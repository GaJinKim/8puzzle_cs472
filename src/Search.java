import java.util.*;

public class Search {
    private Node root;
    private char[] goalState;

    /**
     * Constructor
     */
    public Search(Node root) {
        this.root = root;
        this.goalState = new char[] {'1','2','3','4','5','6','7','8','_'};
    }

    /**
     * Getters
     */
    public Node getRoot() {
        return root;
    }
    public char[] getGoalState() { return goalState; }

    /**
     * Setters
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     *  Goal test
     *
     *  Used to check whether goal configuration is reached
     */
    public boolean atGoalState(Node node) {
        return Arrays.equals(node.getState(), goalState);
    }

    /**
     * Breadth First Search (BFS)
     *
     * prints out solution or failure
     */
    public void breadthFirstSearch() {
//        Queue<Node> frontier = new LinkedList<Node>(); // FIFO queue initially containing one path, for the problem's initial state
//        Set<Node> reached = new HashSet<Node>(); // set of states; initially empty
//        String solution = "failure";
//
//        while (!frontier.isEmpty()) {
//            Node parent = frontier.peek();
//
//        }

        if (atGoalState(root)) {
            System.out.println("woohoo we're at the goal state!");
        } else {
            System.out.println("hey not at goal state!");
        }


    }
}
