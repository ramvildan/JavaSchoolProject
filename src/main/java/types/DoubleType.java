package types;

import types.interfaces.Type;

public class DoubleType implements Type<Double> {

    public DoubleType() {
    }

    @Override
    public Double getValue(String rawValue) {
        try {
            String trim = rawValue.trim().replaceAll(" ","");
            return Double.parseDouble(trim);
        } catch (Exception ex) {
            System.out.println("Value is not Double");
            throw new RuntimeException("Value is not Double");
        }
    }
}
