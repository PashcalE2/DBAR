package com.main;


import java.security.*;
import java.util.Base64;

public class KeyGeneratorUtil {

    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static void printKeyPair() throws NoSuchAlgorithmException {
        KeyPair keyPair = getKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        System.out.println("Public Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        printKeyPair();
    }
}