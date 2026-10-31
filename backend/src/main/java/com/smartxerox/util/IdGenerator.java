package com.smartxerox.util;

import java.security.SecureRandom;

public class IdGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHA = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    public static String qrToken() {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) sb.append(ALPHA.charAt(RANDOM.nextInt(ALPHA.length())));
        return sb.toString();
    }

    public static String shopCode() {
        StringBuilder sb = new StringBuilder(9);
        sb.append("SX-");
        for (int i = 0; i < 6; i++) sb.append(ALPHA.charAt(RANDOM.nextInt(ALPHA.length())));
        return sb.toString();
    }

    public static String sessionUid() {
        StringBuilder sb = new StringBuilder(9);
        sb.append("CX-");
        for (int i = 0; i < 6; i++) sb.append(ALPHA.charAt(RANDOM.nextInt(ALPHA.length())));
        return sb.toString();
    }

    public static String orderNumber() {
        StringBuilder sb = new StringBuilder(12);
        sb.append("SX-");
        for (int i = 0; i < 8; i++) sb.append(ALPHA.charAt(RANDOM.nextInt(ALPHA.length())));
        return sb.toString();
    }
}
