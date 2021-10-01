import java.util.Comparator;

public class PriorityComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.getTotalCost(), node2.getTotalCost());
    }
}
