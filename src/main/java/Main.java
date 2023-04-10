import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {

            List<Map<String, Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Fedorov' , 'id'=3, 'age'=40, 'active'=true");
            List<Map<String, Object>> result2 = starter.execute("INSERT VALUES ‘lastName’ = ‘Sidorov’ , ‘id’=2, ‘age’=41, ‘active’=false");
            List<Map<String, Object>> result3 = starter.execute("INSERT VALUES ‘lastName’ = ‘Ivanov’ , ‘id’=1, ‘age’=42, ‘active’=false");
            List<Map<String, Object>> result4 = starter.execute("INSERT VALUES ‘lastName’ = ‘Petrov’ , ‘id’=4, ‘age’=43, ‘active’=false");
            System.out.println("-------------");
            System.out.println("request: UPDATE VALUES WHERE");
            List<Map<String, Object>> result5 = starter.execute("UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3");
            System.out.println("-------------");
            System.out.println("request: UPDATE All VALUES WHERE");
            List<Map<String, Object>> result6 = starter.execute("UPDATE VALUES ‘active’=true where ‘active’=false");
            System.out.println("-------------");
            System.out.println("request: UPDATE All VALUES WHERE + AND");
            List<Map<String, Object>> result7 = starter.execute("UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’=3 and 'age'=40");
            System.out.println("-------------");
            System.out.println("request: DELETE");
            List<Map<String, Object>> result8 = starter.execute("DELETE WHERE ‘id’=3");
            System.out.println("-------------");
            System.out.println("request: SELECT");
            List<Map<String, Object>> result9 = starter.execute("SELECT WHERE ‘age’< 42 and ‘lastName’ ilike ‘%o%‘");
            System.out.println("-------------");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
