package types;

import types.interfaces.Type;

public class BooleanType implements Type<Boolean> {

    public BooleanType() {
    }

    @Override
    public Boolean getValue(String rawValue) {
        if (rawValue.startsWith("true") || rawValue.startsWith("false")) {
            return Boolean.valueOf(rawValue);
        } else {
            System.out.println("Value is not Boolean");
            throw new RuntimeException("Value is not Boolean");
        }
    }
}
