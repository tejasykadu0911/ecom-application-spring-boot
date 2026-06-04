package com.app.ecom;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PracticeJava {

    public static void main(String[] args) {
        long number = 123456789L;

//        System.out.println(reverseNumber(number).orElseThrow(() -> new IllegalArgumentException("Number cannot be zero")));
//        System.out.println(reverseNumber(number));
//        System.out.println(reverseStringByArray("tejask"));

        // Example: find two elements that sum to target
        int[] arr = {2, 7, 11, 15};
        int target = 9;
        findIndicesOfTwoSum(arr, target);
    }

    public static Optional<Long> reverseNumber(long number) {

        if(number==0) return Optional.empty();
        long temp = number;
        long reverseNumber = 0;

        while (temp % 10 > 0) {
            reverseNumber = (reverseNumber * 10) + temp % 10;
            temp/= 10;
        }
        return Optional.of(reverseNumber);
    }

    public static String reverseStringByArray(String str){

        char [] strArray = str.toCharArray();

        for(int i =0,j=strArray.length-1;i<j;i++,j--){
            char temp = strArray[i];
            strArray[i] = strArray[j];
            strArray[j] = temp;
        }

        return new String(strArray);
    }

    public static void findIndicesOfTwoSum(int[] arr, int target){

//        int[]arr = new int[]{2,7,11,15};
//        int target = 9;
        // Using HashMap for O(n) time complexity
        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < arr.length; i++){
            int complement = target - arr[i];

            // Check if complement exists in map
            if(map.containsKey(complement)){
                System.out.println("Indices of two sum are " + map.get(complement) + " and " + i);
                System.out.println("Elements are " + complement + " and " + arr[i]);
                return;
            }

            // Store current element with its index
            map.put(arr[i], i);
        }

        System.out.println("No two elements found that sum to " + target);
    }

//    public static void findIndicesOfTwoSum(){
//
//        int[]arr = new int[]{2,7,11,15};
//        int target = 9;
//        int[] targetIndices = new int[2];;
//
//        for(int i =0;i< arr.length;i++){
//            for(int j=0;j<arr.length && j!=i;j++){
//                if(arr[i] + arr[j] == target){
//                    targetIndices[0] = i;
//                    targetIndices[1] = j;
//                }
//            }
//        }
//
//        System.out.println("Indices of two sum are " + targetIndices[0] + " and " + targetIndices[1]);
//    }
}
