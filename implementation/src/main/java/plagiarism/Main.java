package plagiarism;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String args[] ) throws Exception {
        // TODO change it to local project path not user specific
        Scanner scan = new Scanner(new File("/Users/pankaj.birat/Flipkart/projects/practice/implementation/src/main/resources/plagiarism_input.txt"));
        String first_line[] = scan.nextLine().split(" ");

        int num_student = Integer.parseInt(first_line[0]);
        Float thresh = Float.parseFloat(first_line[1]);

        Map<String, Map<String,Integer>> student_ass = new HashMap<>();
        List<String> rolls = new ArrayList<>();
        Map<String, List<String>> stu_plag = new HashMap<>();

        for(int iii = 0; iii < num_student; iii++) {
            String line = scan.nextLine();
            String line_arr[] = line.split(" ");
            String roll = line_arr[0];
            rolls.add(roll);
            int num_line = Integer.parseInt(line_arr[1]);
            for(int ii = 0; ii < num_line; ii++) {
                String ass_line[] = scan.nextLine().split(" ");
                for(int i = 0; i < ass_line.length; i++) {
                    ass_line[i] = ass_line[i].toLowerCase();
                    if(student_ass.containsKey(roll)) {
                        Map<String, Integer> stu_assMap = student_ass.get(roll);
                        stu_assMap.put(ass_line[i], stu_assMap.getOrDefault(ass_line[i], new Integer(0))+1);
                    }else{
                        Map<String, Integer> stu_assMap = new HashMap<>();
                        stu_assMap.put(ass_line[i], stu_assMap.getOrDefault(ass_line[i], new Integer(0))+1);
                        student_ass.put(roll, stu_assMap);
                    }
                }
            }
        }

        Collections.sort(rolls);
        for(int i = 0; i < rolls.size(); i++) {
            for(int j = i+1; j < rolls.size(); j++) {
                String stu1 = rolls.get(i);
                String stu2 = rolls.get(j);

                if(isPlague(student_ass.get(stu1), student_ass.get(stu2), thresh)) {
                    List<String> sttt = stu_plag.getOrDefault(stu1, new ArrayList<>());
                    sttt.add(stu2);
                    stu_plag.put(stu1, sttt);
                    List<String> sttt1 = stu_plag.getOrDefault(stu2, new ArrayList<>());
                    sttt1.add(stu1);

                    stu_plag.put(stu2, sttt1);
                }
            }
        }
        Set<String> visited = new HashSet<>();
        for(String rol: rolls){
            Set<String> st = new HashSet<>();
            st.add(rol);
            printPlag(stu_plag, rol, st, visited);
            if(st.size() > 1) {
                for (String str : st) {
                    System.out.print(str + " ");
                }
                System.out.println();
            }

        }

    }

    public static void printPlag(Map<String, List<String>> plag, String curr_stu, Set<String> bros, Set<String> visited) {
        if(visited.contains(curr_stu)) return;
        visited.add(curr_stu);
        if(plag.containsKey(curr_stu)) {
            for(String p : plag.get(curr_stu)) {
                if(visited.contains(curr_stu)) {
                    bros.add(p);
                    printPlag(plag, p, bros, visited);
                }
            }
        }
    }


    static boolean isPlague(Map<String, Integer> student1,Map<String, Integer> student2, Float threshHold) {

        Set<String> uniqueWord = new HashSet<>();
        uniqueWord.addAll(student1.keySet());
        uniqueWord.addAll(student2.keySet());

        Integer uniqueCount = uniqueWord.size();
        int wordMatches = 0;
        for (Map.Entry<String, Integer> entry: student1.entrySet()) {
            Integer count1 = entry.getValue();
            String word = entry.getKey();

            if (student2.containsKey(word)) {
                if (checkThreshold(student2.get(word), count1, threshHold)) {
                    wordMatches++;
                }
            }
        }
        return checkThreshold(wordMatches, uniqueCount, threshHold);
    }

    private static boolean checkThreshold(Integer count1, Integer count2, Float threshHold) {
        Integer temp;
        if (count1 < count2) {
            temp = count2;
            count2 = count1;
            count1 = temp;
        }
        return (count2*100.0F)/(count1) >= threshHold;
    }
}
