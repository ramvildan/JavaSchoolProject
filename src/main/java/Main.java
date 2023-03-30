import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {

            List<Map<String, Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");

            List<Map<String,Object>> result2 = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3");

            List<Map<String,Object>> result3 = starter.execute("SELECT");

            System.out.println(result1);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
