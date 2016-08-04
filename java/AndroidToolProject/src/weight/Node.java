package weight;
import java.util.Comparator;

class Node implements Comparator<Node> {
    int weight = 0;
    String name = "";
    public Node() {}
    public Node(int wt, String name) {
        this.weight = wt;
        this.name = name;
    }
    public String toString() {
        StringBuilder sbBuilder = new StringBuilder();
        sbBuilder.append(" weight=").append(weight);
        sbBuilder.append(" name").append(name);
        return sbBuilder.toString();
    }
    public int compare(Node n1, Node n2) {
        if (n1.weight > n2.weight) return 1;
        else return 0;
    }
}
