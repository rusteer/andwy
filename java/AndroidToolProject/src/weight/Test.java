package weight;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        List<Node> arrNodes = new ArrayList<Node>();
        Node n = new Node(10, "测试1");
        arrNodes.add(n);
        n = new Node(20, "测试2");
        arrNodes.add(n);
        n = new Node(30, "测试3");
        arrNodes.add(n);
        n = new Node(40, "测试4");
        arrNodes.add(n);
        //Collections.sort(arrNodes, new Node());
        int sum = getSum(arrNodes);
        for (int k = 0; k < 20; k++) {
            Map<String, Integer> showMap = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < 100000; i++) {
                int random = getRandom(sum);
                Node kw = getKW(arrNodes, random);
                if (showMap.containsKey(kw.name)) {
                    showMap.put(kw.name, showMap.get(kw.name) + 1);
                } else {
                    showMap.put(kw.name, 1);
                }
                //System.out.println(i + " " +random + " " + getKW(arrNodes, random));
            }
            System.out.print(k + " ");
            StringBuilder result = new StringBuilder("{");
            for (Node node : arrNodes) {
                if (showMap.containsKey(node.name)) {
                    result.append(showMap.get(node.name)).append(",");
                }
            }
            result.append("}");
            System.out.println(result);
        }
    }
    public static Node getKW(List<Node> nodes, int rd) {
        Node ret = null;
        int curWt = 0;
        for (Node n : nodes) {
            curWt += n.weight;
            if (curWt >= rd) {
                ret = n;
                break;
            }
        }
        return ret;
    }
    public static int getSum(List<Node> nodes) {
        int sum = 0;
        for (Node n : nodes)
            sum += n.weight;
        return sum;
    }
    public static int getRandom(int seed) {
        return (int) Math.round(Math.random() * seed);
    }
}
