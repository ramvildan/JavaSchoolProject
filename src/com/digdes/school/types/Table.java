package com.digdes.school.types;

import com.digdes.school.types.interfaces.Type;

import java.util.Map;

public class Table {

    public static Map<String, Type> USER = Map.of(
            "id", new LongType(),
            "lastName", new StringType(),
            "cost", new DoubleType(),
            "age", new LongType(),
            "active", new BooleanType());
}
