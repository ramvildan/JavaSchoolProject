import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {

            List<Map<String, Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
            List<Map<String, Object>> result2 = starter.execute("INSERT VALUES ‘lastName’ = ‘Сидоров’ , ‘id’=2, ‘age’=41, ‘active’=false");
            List<Map<String, Object>> result3 = starter.execute("INSERT VALUES ‘lastName’ = ‘Иванов’ , ‘id’=3, ‘age’=42, ‘active’=false");
            List<Map<String, Object>> result4 = starter.execute("INSERT VALUES ‘lastName’ = ‘Петров’ , ‘id’=4, ‘age’=43, ‘active’=false");
            List<Map<String, Object>> result5 = starter.execute("UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3");
//            List<Map<String, Object>> result6 = starter.execute("SELECT WHERE ‘age’>=30 and ‘lastName’ ilike ‘%п%");
//            List<Map<String, Object>> result7 = starter.execute("DELETE WHERE ‘id’=3");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
