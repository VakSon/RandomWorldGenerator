package org.example;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class garbagefunctions {
    public static long Seed2Long(String seed){
        try {
            // Create a MessageDigest object with the SHA-256 algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Update the MessageDigest with the seed bytes
            byte[] bytes = seed.getBytes(StandardCharsets.UTF_8);
            md.update(bytes);

            // Get the digest bytes and convert them to a long
            byte[] digest = md.digest();
            long result = 0;
            for (int i = 0; i < 8; i++) {
                result <<= 8;
                result |= (long) (digest[i] & 0xFF);
            }
            return result;

        } catch (NoSuchAlgorithmException e) {
            // Handle the case where the SHA-256 algorithm is not available
            throw new RuntimeException(e);
        }
    }
    public static float[][] TempMap(int vyska, int sirka, int equator){
        float[][] tempmap = new float[sirka][vyska];
        for (int j = 0; j < vyska; j++){
            float h = (float) j/equator;
            double cosine = (1.0 - Math.cos( h * Math.PI)) / 2.0;
            float f = (float) (25 * (1 - cosine) + cosine * -5);
            for (int i = 0; i < sirka; i++){
                tempmap[i][j] = (float)f;
            }
        }
        return tempmap;
    }
}
