package com.pcmk.utils;

import java.util.UUID;

public abstract class UUIdUtils {

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean isUUIDFormat(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
