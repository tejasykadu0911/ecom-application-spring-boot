package com.app.ecom.dto;

import com.app.ecom.model.Order;
import com.app.ecom.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long productId;
    private Product product;;
    private Integer quantity;
    private BigDecimal price;

}
