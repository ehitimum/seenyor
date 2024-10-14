package com.yunyan.project.iot.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Sha1Util {

    public static String generateSha1(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(messageDigest).toUpperCase();
        // StringBuilder sb = new StringBuilder();
        // for (byte b : hashBytes) {
        //     sb.append(String.format("%02x", b));
        // }
        // return sb.toString();
    }
}
