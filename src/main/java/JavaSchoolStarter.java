import types.interfaces.Type;
import types.Tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.getString;

public class JavaSchoolStarter {

    public JavaSchoolStarter() {}

    private static final List<Map<String, Object>> db = new ArrayList<>();

    public List<Map<String, Object>> execute(String request) throws Exception {

        if (request == null) {
            throw new Exception("Request is blank");
        }

        if (request.trim().toUpperCase().startsWith("INSERT VALUES")) {
            List<Map<String, Object>> insertedValues = executeInsert(request);

            insertedValues.forEach(System.out::println);
        }

        if(request.trim().toUpperCase().startsWith("UPDATE VALUE")) {
            List<Map<String, Object>> updatedValues = executeUpdate(request);

            updatedValues.forEach(System.out::println);
        }

        if(request.startsWith("DELETE")) {


        }

        if(request.contains("SELECT VALUE")) {


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

        String valueToSet = updateValues.get(0);
        String requestInfo = updateValues.get(1);

        Map<String, Object> values = getValues(Arrays
                .stream(valueToSet
                        .split(","))
                .map(String::trim)
                .toList());

        String[] split = requestInfo.split("=");

        if (split.length != 0) {
            String requestInfoKey = getString(split[0]);
            Object requestInfoValue = Tables.USER.get(requestInfoKey).getValue(split[1]);

            List<Map<String, Object>> updatedObjects = new ArrayList<>();

            db.forEach(stringObjectMap -> {
                if (stringObjectMap.get(requestInfoKey).equals(requestInfoValue)) {
                    stringObjectMap.putAll(values);

                    updatedObjects.add(stringObjectMap);
                }
            });

            return updatedObjects;
        } else {
            List<Map<String, Object>> updatedObjects = new ArrayList<>();

            db.forEach(stringObjectMap -> {
                updatedObjects.add(stringObjectMap);
            });

            return updatedObjects;
        }
    }

    //"DELETE WHERE ‘id’=3"
    private void executeDelete(String request) {

        if (request.trim().toUpperCase().contains("WHERE") && !request.trim().toUpperCase().contains("AND")) {

            List<String> deleteValues = Arrays.stream(request
                            .replace("DELETE", "")
                            .trim()
                            .replaceAll(" ", "")
                            .split("[wW][hH][eE][rR][eE]"))
                    .map(String::trim)
                    .toList();


        }
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
}
