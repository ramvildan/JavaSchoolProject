package com.digdes.school;

import com.digdes.school.types.Table;
import com.digdes.school.types.interfaces.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.digdes.school.utils.Util.getString;

public class JavaSchoolStarter {

    public JavaSchoolStarter() {
    }

    private static final List<Map<String, Object>> db = new ArrayList<>();

    public List<Map<String, Object>> execute(String request) throws Exception {

        if (request == null) {
            throw new Exception("Request is blank");
        }

        if (request.trim().toUpperCase().startsWith("INSERT VALUES")) {
            return executeInsert(request);
        }

        if (request.trim().toUpperCase().startsWith("UPDATE VALUE")) {
            return executeUpdate(request);
        }

        if (request.trim().toUpperCase().startsWith("DELETE")) {
            return executeDelete(request);
        }

        if (request.trim().toUpperCase().contains("SELECT")) {
            return executeSelect(request);
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

        List<String> updateValues = Arrays.stream(request
                        .replace("UPDATE VALUES", "")
                        .trim()
                        .replaceAll(" ", "")
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .toList();

        Map<String, Object> valuesToSet = valuesToUpdate(updateValues.get(0));

        String requestInfoToUpdate = updateValues.get(1);

        List<Condition> whereConditions = getWhereConditions(requestInfoToUpdate);

        for (Condition el : whereConditions) {
            if (!Table.USER.containsKey(el.getKey())) {
                throw new RuntimeException("No such column exists: " + el.getKey());
            }
        }

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

        for (Condition el : whereConditions) {
            if (!Table.USER.containsKey(el.getKey())) {
                throw new RuntimeException("No such column exists: " + el.getKey());
            }
        }

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

    private List<Map<String, Object>> executeSelect(String request) {

        List<String> selectValues = Arrays.stream(request
                        .replace("SELECT", "")
                        .trim()
                        .replaceAll(" ", "")
                        .split("[wW][hH][eE][rR][eE]"))
                .map(String::trim)
                .toList();

        List<Map<String, Object>> selectedObjects = new ArrayList<>();

        if (selectValues.size() > 1) {
            String requestInfoToSelect = selectValues.get(1);

            List<Condition> whereConditions = getWhereConditions(requestInfoToSelect);

            for (Condition el : whereConditions) {
                if (!Table.USER.containsKey(el.getKey())) {
                    throw new RuntimeException("No such column exists: " + el.getKey());
                }
            }

            for (Map<String, Object> stringObjectMap : db) {
                if (isObjectMatches(whereConditions, stringObjectMap)) {
                    selectedObjects.add(stringObjectMap);
                }
            }

        } else {
            selectedObjects.addAll(db);
        }
        return selectedObjects;
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

            if (Table.USER.containsKey(key)) {
                Type<?> type = Table.USER.get(key);
                Object value = type.getValue(rawValue);
                values.put(key, value);
            } else {
                throw new RuntimeException("No such column exists");
            }
        }

        return values;
    }

    private static List<Condition> getWhereConditions(String requestWhereInfo) {

        String[] andConditions = requestWhereInfo.split("[Aa][Nn][Dd]");
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
                    Object value = Table.USER.get(key).getValue(condition.getValue());
                    return condition.getOperator().compare(stringObjectMap.get(key), value);
                })
                .toList();
    }
}
