package com.digdes.school.types;


import com.digdes.school.types.interfaces.Type;

import static com.digdes.school.utils.Util.getString;

public class StringType implements Type<String> {

    public StringType() {
    }

    @Override
    public String getValue(String rawValue) {
        if (rawValue.contains("'") || rawValue.contains("’") || rawValue.contains("‘")) {
            return getString(rawValue);
        } else {
            System.out.println("Value is not String");
            throw new RuntimeException("Value is not String");
        }
    }
}
