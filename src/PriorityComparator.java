import java.util.Comparator;

/**
 * Nodes are weighted based on total cost
 *
 * Total cost = cost + estimated (heuristic) cost
 * f(n) = g(n) + h(n)
 *
 */
public class PriorityComparator implements Comparator<Node> {
    @Override
    public int compare(Node node1, Node node2) {
        return Integer.compare(node1.getTotalCost(), node2.getTotalCost());
    }
}
