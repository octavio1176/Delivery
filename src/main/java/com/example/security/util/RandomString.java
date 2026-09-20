package com.example.security.util;
import java.security.SecureRandom;

public class RandomString {
    private static final String CHARACTERES =  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String generateRandomString(int length){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder  sb = new StringBuilder();
        for (int i=0; i<length; i++){
            int  index=secureRandom.nextInt(CHARACTERES.length());
            sb.append(CHARACTERES.charAt(index));
        }


        return sb.toString();
    }

    public static String generateNumericCode() {
        SecureRandom secureRandom = new SecureRandom();
        int code = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(code);
    }


}
