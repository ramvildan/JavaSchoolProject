package types;

import types.interfaces.Type;

public class StringType implements Type<String> {

    public StringType() {
    }

    @Override
    public String getValue(String rawValue) {
        if (rawValue.indexOf("'") >= 0 && rawValue.lastIndexOf("'") > 0) {
            return rawValue.replaceAll("'", "");
        } else {
            System.out.println("Value is not String");
            throw new RuntimeException("Value is not String");
        }
    }
}
