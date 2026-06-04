package com.app.ecom;

public class Dixian {

    public static void main(String args[]) {

        String str1 = "madam";

        System.out.println(checkPalidromWitharray(str1) ? "String is palidrom" : "String is not palidrom");
        System.out.println(checkIfPalidromeByBuffer(str1) ? "String is palidrom" : "String is not palidrom");

    }

    public static boolean checkIfPalidromeByBuffer(String str){

        StringBuilder stringBuffer = new StringBuilder(str);
        return stringBuffer.reverse().toString().equals(str);
    }

    public static boolean checkPalidromWitharray(String str){

        if(str.isEmpty()){
            return false;
        }

        char[] str1 = str.toCharArray();

        for(int i =0,j=str1.length-1;i<j;i++,j--){
            if(str1[i]!=str1[j]){
                return false;
            }
        }
        return true;
    }
}
