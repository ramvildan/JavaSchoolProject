package com.digdes.school.utils;

public class Util {
    public static String getString (String rawValue) {
        return rawValue.trim().replaceAll("^['’‘]", "").replaceAll("['’‘]$", "");
    }
}