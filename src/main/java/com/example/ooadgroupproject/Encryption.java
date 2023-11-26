package com.example.ooadgroupproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public static String getSHA_256Str(String input){
        try{
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            byte[] inputBytes = input.getBytes();
            byte[] hashBytes = md.digest(inputBytes);
            StringBuilder sb=new StringBuilder();
            for(byte b:hashBytes){
                sb.append(String.format("%02x",b));
            }
            String encryptedString = sb.toString();
            return encryptedString;

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
}
