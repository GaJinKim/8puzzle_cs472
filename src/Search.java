import java.util.*;

public class Search {
    private static final long fifteenMinInMilliseconds = 900000;

    private Node root; // initial state
    private char[] goalState;
    private int totalNodes;

    /**
     * Constructor
     */
    public Search(Node root) {
        this.root = root;
        this.goalState = new char[] {'1','2','3','4','5','6','7','8','_'};
        totalNodes = 0;
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
        return Arrays.equals(node.getState(), getGoalState());
    }

    /**
     * Breadth First Search (BFS)
     *
     * <Total nodes generated, Total time taken, Valid sequence of actions>>
     */
    public void breadthFirstSearch() {
        Set<char[]> closed = new HashSet<char[]>(); // closed list (explored set)
        Queue<Node> fringe = new LinkedList<Node>(); // open list (not yet explored set)
        fringe.add(root);

        boolean foundSolution = false;
        long startTime = System.currentTimeMillis();

        outerloop:
        while (!fringe.isEmpty() && System.currentTimeMillis() - startTime < fifteenMinInMilliseconds) {
            Node current = fringe.remove(); // pop() equivalent
            closed.add(current.getState());

            List<Node> successors = Successor.getSuccessors(current);
            totalNodes += successors.size();

            for (Node child : successors) {
                if (!(closed.contains(child.getState()) || fringe.contains(child))) {
                    if (atGoalState(child)) {
                        printSolution(totalNodes, System.currentTimeMillis() - startTime, child);
                        foundSolution = true;
                        break outerloop;
                    }
                    fringe.add(child);
                }
            }
        }
        if (!foundSolution) {
            printTimeout();
        }
    }



    /**
     * Helper Functions
     */
    private void printSolution(int totalNodes, long ms, Node childAtGoal) {
        printTotalNodesGenerated(totalNodes);
        printFormattedRunTime(ms);
        printPathAndPathLength(childAtGoal);
    }
    private void printTotalNodesGenerated(int totalNodes) {
        System.out.println("Total nodes generated: " + totalNodes);
    }
    private void printFormattedRunTime(long ms) {
        long seconds = ms / 1000;
        long remainingMilliseconds = ms % 1000;
        System.out.println("Total time taken: " + seconds + "sec " + remainingMilliseconds + "ms");
    }
    private void printPathAndPathLength(Node childAtGoal) {
        Stack<Action> solution = new Stack<>();
        while (!childAtGoal.equals(root)) {
            solution.add(childAtGoal.getAction());
            childAtGoal = childAtGoal.getParent();
        }

        System.out.println("Path length: " + solution.size());
        System.out.print("Path: ");

        while (!solution.isEmpty())
            System.out.print(solution.pop());
    }

    private void printTimeout() {
        System.out.println("Path: timed out.");
        System.out.println("Path length: timed out.");
        System.out.println("Total nodes generated: <<??>>");
        System.out.println("Total time taken: > 15 min");
    }
}
