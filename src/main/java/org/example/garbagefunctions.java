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
}
