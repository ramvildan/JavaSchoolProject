import types.interfaces.Type;
import types.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static utils.Utils.getString;

public class JavaSchoolStarter {

    public JavaSchoolStarter() {
    }

    private static final List<Map<String, Object>> db = new ArrayList<>();

    public List<Map<String, Object>> execute(String request) throws Exception {

        if (request == null) {
            throw new Exception("Request is blank");
        }

        if (request.trim().toUpperCase().startsWith("INSERT VALUES")) {
            List<Map<String, Object>> insertedValues = executeInsert(request);

            insertedValues.forEach(System.out::println);
        }

        if (request.trim().toUpperCase().startsWith("UPDATE VALUE")) {
            List<Map<String, Object>> updatedValues = executeUpdate(request);

            updatedValues.forEach(System.out::println);
        }

        if (request.startsWith("DELETE")) {
            List<Map<String, Object>> deletedValues = executeDelete(request);

            db.forEach(System.out::println);

        }

        if (request.contains("SELECT VALUE")) {


        }

        return new ArrayList<>();
    }

    private List<Map<String, Object>> executeInsert(String request) {
        List<String> insertValues = Arrays
                .stream(request.replace("INSERT VALUES", "")
                        .trim()
                        .split(","))
                .map(String::trim)
                .toList();

        Map<String, Object> object = getValues(insertValues);

        db.add(object);

        return List.of(object);
    }

    private List<Map<String, Object>> executeUpdate(String request) {

        List<String> updateValuesWithWhere = Arrays.stream(request
                        .replace("UPDATE VALUES", "")
                        .trim()
                        .replaceAll(" ", "")
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .toList();

        Map<String, Object> valuesToSet = valuesToUpdate(updateValuesWithWhere.get(0));

        String requestInfoToUpdate = updateValuesWithWhere.get(1);

        List<Condition> whereConditions = getWhereConditions(requestInfoToUpdate);

        List<Map<String, Object>> updatedObjects = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : db) {
            if (isObjectMatches(whereConditions, stringObjectMap)) {
                stringObjectMap.putAll(valuesToSet);
                updatedObjects.add(stringObjectMap);
            }
        }

        return updatedObjects;
    }

    private List<Map<String, Object>> executeDelete(String request) {

        List<String> deleteValues = Arrays.stream(request
                        .replace("DELETE", "")
                        .trim()
                        .replaceAll(" ", "")
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .toList();

        String requestInfoToDelete = deleteValues.get(1);

        List<Condition> whereConditions = getWhereConditions(requestInfoToDelete);

        List<Map<String, Object>> deletedObjects = new ArrayList<>();

        Iterator<Map<String, Object>> iterator = db.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> stringObjectMap = iterator.next();

            if (isObjectMatches(whereConditions, stringObjectMap)) {
                iterator.remove();
                deletedObjects.add(stringObjectMap);
            }
        }

        return deletedObjects;
    }

    private static Map<String, Object> valuesToUpdate(String valuesToSet) {

        String[] split = valuesToSet
                .split(",");

        return getValues(Arrays
                .stream(split)
                .map(String::trim)
                .toList());
    }

    private static Map<String, Object> getValues(List<String> insertValues) {
        Map<String, Object> values = new HashMap<>();

        for (String insertValue : insertValues) {
            List<String> keyValues = Arrays
                    .stream(insertValue.split("="))
                    .map(String::trim)
                    .toList();

            String rawKey = keyValues.get(0);
            String rawValue = keyValues.get(1);

            String key = getString(rawKey);

            if (Tables.USER.containsKey(key)) {
                Type<?> type = Tables.USER.get(key);
                Object value = type.getValue(rawValue);
                values.put(key, value);
            }
        }

        return values;
    }

    private static List<Condition> getWhereConditions(String requestWhereInfo) {

        String[] andConditions = requestWhereInfo.split("[Aa],[Nn],[Dd]");
        List<Condition> conditionList = new ArrayList<>();

        for (String someCondition : andConditions) {
            Condition condition = Condition.getCondition(someCondition);
            conditionList.add(condition);
        }

        return conditionList;
    }

    private static boolean isObjectMatches(List<Condition> whereConditions, Map<String, Object> stringObjectMap) {
        List<Condition> successConditions = getSuccessCondition(whereConditions, stringObjectMap);

        return whereConditions.size() == successConditions.size();
    }

    private static List<Condition> getSuccessCondition(List<Condition> whereConditions, Map<String, Object> stringObjectMap) {
        return whereConditions
                .stream()
                .filter(condition -> {
                    String key = condition.getKey();
                    Object value = Tables.USER.get(key).getValue(condition.getValue());
                    return condition.operator.compare(stringObjectMap.get(key), value);
                })
                .toList();
    }
}
