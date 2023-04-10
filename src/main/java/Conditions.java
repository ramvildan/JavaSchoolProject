import types.DoubleType;
import types.LongType;

import java.util.List;
import java.util.Locale;

public enum Conditions {

    EQUALS("=", List.of(Boolean.class, String.class, Long.class, Double.class)),
    NOT_EQUALS("!=", List.of(Boolean.class, String.class, Long.class, Double.class)),
    LIKE("like", List.of(String.class)),
    LIKE_INCENTIVE("ilike", List.of(String.class)),
    GTE(">=", List.of(Long.class, Double.class)),
    GT(">", List.of(Long.class, Double.class)),
    LTE("<=", List.of(Long.class, Double.class)),
    LT("<", List.of(Long.class, Double.class));

    private final String value;

    private final List<Class<?>> supportedTypes;

    Conditions(String value, List<Class<?>> supportedTypes) {
        this.value = value;
        this.supportedTypes = supportedTypes;
    }

    public String getValue() {
        return value;
    }

    public List<Class<?>> getSupportedTypes() {
        return supportedTypes;
    }

    public boolean compare(Object a, Object b) {
        boolean typeOfClassesIsSupported = this.supportedTypes.contains(a.getClass()) && this.supportedTypes.contains(b.getClass());

        if (typeOfClassesIsSupported) {
            String stringA = String.valueOf(a);
            String stringB = String.valueOf(b);

            boolean isObjectLong = a instanceof LongType && b instanceof LongType;
            boolean isObjectDouble = a instanceof DoubleType && b instanceof DoubleType;

            switch (this.value) {
                case "=" -> {
                    return a.equals(b);
                }
                case "!=" -> {
                    return !a.equals(b);
                }
                case "like" -> {
                    boolean b1 = stringB.startsWith("%");
                    boolean b2 = stringB.endsWith("%");

                    if (b1 && b2) {
                        return stringA.contains(stringB.replaceAll("%", ""));
                    }

                    if (b1) {
                        return stringA.startsWith(stringB.replaceAll("%", ""));
                    }

                    if (b2) {
                        return stringA.endsWith(stringB.replaceAll("%", ""));
                    }
                }
                case "ilike" -> {
                    boolean b1 = stringB.startsWith("%");
                    boolean b2 = stringB.endsWith("%");

                    if (b1 && b2) {
                        return stringA.contains(stringB.toLowerCase().replaceAll("%", ""));
                    }

                    if (b1) {
                        return stringA.startsWith(stringB.toLowerCase().replaceAll("%", ""));
                    }

                    if (b2) {
                        return stringA.endsWith(stringB.toLowerCase().replaceAll("%", ""));
                    }
                }
                case ">=" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectLong) {
                        return longA >= longB;
                    }
                    if (isObjectDouble) {
                        return doubleA >= doubleB;
                    }

                    return false;
                }
                case "<=" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectLong) {
                        return longA <= longB;
                    }
                    if (isObjectDouble) {
                        return doubleA <= doubleB;
                    }

                    return false;
                }
                case "<" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectLong) {
                        return longA < longB;
                    }
                    if (isObjectDouble) {
                        return doubleA < doubleB;
                    }

                    return false;
                }
                case ">" -> {
                    long longA = Long.parseLong(stringA);
                    long longB = Long.parseLong(stringB);

                    double doubleA = Double.parseDouble(stringA);
                    double doubleB = Double.parseDouble(stringB);

                    if (isObjectLong) {
                        return longA > longB;
                    }
                    if (isObjectDouble) {
                        return doubleA > doubleB;
                    }

                    return false;
                }
            }
        }

        return false;
    }
}
