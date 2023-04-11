package com.digdes.school.types;


import com.digdes.school.types.interfaces.Type;

public class LongType implements Type<Long> {

    public LongType() {
    }

    @Override
    public Long getValue(String rawValue) {
        try {
            String trim = rawValue.trim().replaceAll(" ","");
            return Long.parseLong(trim);
        } catch (Exception ex) {
            System.out.println("Value is not Long");
            throw new RuntimeException("Value is not Long");
        }
    }
}
