package types;

import types.interfaces.Type;

import static utils.Utils.getString;

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
