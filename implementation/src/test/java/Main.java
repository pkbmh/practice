import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        int arr[]  = new int[5];
        String str = "I am the main";
        String strArr[] = str.split(" ");
        List<String> strList = Arrays.asList(strArr);
        Set<String> stringsOrdered = new TreeSet<>(strList);
        Set<String> stringsUnordered = new HashSet<>(strList);

        for(String s : strArr) {
            System.out.println(s);
        }
    }
}
