import java.util.Objects;

import static utils.Utils.getString;

public class Condition {

    Conditions operator;
    String key;
    String value;


    public Condition(Conditions operator, String key, String value) {
        this.operator = operator;
        this.key = key;
        this.value = value;
    }

    public static Condition getCondition(String condition) {
        Condition conditionObject = null;

        if (condition.contains("=")) {
            if (condition.contains("!")) {
                String[] split = condition.split("!=");
                conditionObject = new Condition(Conditions.NOT_EQUALS, getString(split[0]), split[1]);
            } else {
                String[] split = condition.split("=");
                conditionObject = new Condition(Conditions.EQUALS, getString(split[0]), split[1]);
            }
        }

        if (condition.contains("like")) {
            String[] split = condition.split("like");
            conditionObject = new Condition(Conditions.LIKE, getString(split[0]), split[1]);
        }

        if (condition.contains("ilike")) {
            String[] split = condition.split("ilike");
            conditionObject = new Condition(Conditions.LIKE_INCENTIVE, getString(split[0]), split[1]);
        }

        if (condition.contains(">")) {
            if (condition.contains("=")) {
                String[] split = condition.split(">=");
                conditionObject = new Condition(Conditions.GTE, getString(split[0]), split[1]);
            } else {
                String[] split = condition.split(">");
                conditionObject = new Condition(Conditions.GT, getString(split[0]), split[1]);
            }
        }

        if (condition.contains("<")) {
            if (condition.contains("=")) {
                String[] split = condition.split("<=");
                conditionObject = new Condition(Conditions.LTE, getString(split[0]), split[1]);
            } else {
                String[] split = condition.split("<");
                conditionObject = new Condition(Conditions.LT, getString(split[0]), split[1]);
            }
        }

        if (Objects.isNull(conditionObject)) {
            throw  new RuntimeException("Bad condition...");
        }

        return conditionObject;
    }

    public Conditions getOperator() {
        return operator;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}