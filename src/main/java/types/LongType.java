package types;

import types.interfaces.Type;

public class LongType implements Type<Long> {

    public LongType() {
    }

    @Override
    public Long getValue(String rawValue) {
        try {
            return Long.parseLong(rawValue);
        } catch (Exception ex) {
            System.out.println("Value is not Long");
            throw new RuntimeException("Value is not Long");
        }
    }
}
