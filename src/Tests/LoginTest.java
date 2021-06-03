package Tests;

import java.util.HashMap;
import java.util.Map;

public class LoginTest {
    public static Map<String, String> users = new HashMap<>();
    static {
        users.put("ali","alavi");
        users.put("taghi","123");
        users.put("naghi","naghavi");
     }
    public static boolean exist(String username,String password){
        if(users.containsKey(username)){
            return users.get(username).equals(password);
        }
     return false;
    }
}
