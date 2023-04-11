package com.digdes.school.types;

import com.digdes.school.types.interfaces.Type;

public class BooleanType implements Type<Boolean> {

    public BooleanType() {
    }

    @Override
    public Boolean getValue(String rawValue) {
        if (rawValue.startsWith("true") || rawValue.startsWith("false")) {
            String trim = rawValue.trim().replaceAll(" ", "");

            return Boolean.valueOf(trim);
        } else {
            System.out.println("Value is not Boolean");
            throw new RuntimeException("Value is not Boolean");
        }
    }
}
