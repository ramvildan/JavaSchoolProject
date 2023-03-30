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

        if (request.startsWith("INSERT VALUES")) {

            List<Map<String, Object>> insertedValues = executeInsert(request);
        }

        if(request.startsWith("UPDATE VALUE")) {

            String[] updateValues = request
                    .replace("UPDATE VALUES", "")
                    .trim()
                    .replaceAll(" ", "")
                    .split("[wW][hH][eE][rR][eE]");

        }

        if(request.contains("DELETE VALUE")) {


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

        Map<String, Object> object = new HashMap<>();

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
                object.put(key, value);
            }
        }

        db.add(object);

        return List.of(object);
    }
}
