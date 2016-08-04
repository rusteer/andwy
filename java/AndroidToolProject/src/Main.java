import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] params) throws Exception {
       List<Integer> list=new ArrayList<Integer>();
       list.add(5);
       list.add(8);
       list.add(1);
       System.out.println(list);
       Collections.sort(list, new Comparator<Integer>(){
        public int compare(Integer o1, Integer o2) {
           return o1.compareTo(o2);
        }});
       System.out.println(list);
    }
    private static boolean matchPrice(int bizPrice, int targetPrice, float fuzzyDegree) {
        return bizPrice <= targetPrice && bizPrice >= targetPrice * fuzzyDegree;
    }
}
