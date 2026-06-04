package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartItemResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request){

        //claude has suggested this is not prod standard code, will revisit
        // throw new InsufficientStockException(product.getId());
        //Look for product
        Optional<Product> productOpt = productRepository.findById(request.getProductId());

        if(productOpt.isEmpty()) return false;


        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity()) return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())   return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if(existingCartItem!=null){
            //update cart
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else {
            //Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()) return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty())   return false;

        cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
        return true;

//        claude suggested
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ProductNotFoundException(productId));
//
//        User user = userRepository.findById(Long.valueOf(userId))
//                .orElseThrow(() -> new UserNotFoundException(userId));

    }

    public List<CartItemResponse> getCart(String userId) {

        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .map(cartItems -> cartItems.stream().map(this::mapToCartItemResponse).toList())
                .orElseGet(List::of);
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItems) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setPrice(cartItems.getPrice());
        cartItemResponse.setQuantity(cartItems.getQuantity());
        cartItemResponse.setProductId(cartItems.getProduct().getId());
        cartItemResponse.setProduct(cartItems.getProduct());
        return cartItemResponse;

    }

    public void clearCart(String userId) {

        userRepository.findById(Long.valueOf(userId))
                .ifPresent(cartItemRepository::deleteByUser);

//        userRepository.findById(Long.valueOf(userId))           Longhand representation
//                .ifPresent(user -> cartItemRepository.deleteByUser(user));

    }
}
