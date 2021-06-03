package Tests;

import java.util.Scanner;
import java.util.regex.*;
public class TestRegexpass {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        String regex="^[a-zA-Z0-9]{8,}";
        while (true) {
            String str = s.nextLine();
            System.out.println(Pattern.matches(regex, str));
        }

    }
}
