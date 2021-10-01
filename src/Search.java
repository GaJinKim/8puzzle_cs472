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
    public void breadthFirstSearch(Node problem) {
        long startTime = System.currentTimeMillis();

        Set<char[]> closed = new HashSet<char[]>(); // closed list (explored set)
        Queue<Node> fringe = new LinkedList<Node>(); // open list (not yet explored set)
        fringe.add(problem);

        Node current = new Node(root.getState());

        while (!atGoalState(current) && System.currentTimeMillis() - startTime < 900000) {
            closed.add(current.getState());
            List<char[]> successors = Successor.getSuccessorStates(current);
            for (char[] state : successors) {
                // if we've already visited this state before
                if (closed.contains(state))
                    continue;
                closed.add(state);
                System.out.println(closed.size());

                Node child = new Node(state);
                totalNodes++;

                current.addChild(child);
                child.setParent(current);
                child.setDepth(current.getDepth() + 1);
                child.setAction(Successor.getDirection(child.getState(), current.getState()));
                fringe.add(child);
            }
            current = fringe.poll();
        }
        long execTime = System.currentTimeMillis() - startTime;
        if (execTime > 900000)
            printTimeout();
        else
            printSolution(totalNodes, execTime, current);
    }

    /**
     * Depth Limited Algorithm
     */
    public void depthLimitedSearch(int limit) {
    }

    public Node recursiveDepthLimitedSearch(Node node, Node problem, int limit) {
        boolean cutoffOccurred = false;
        if (atGoalState(problem)) {
            System.out.println("solution");
            return node;
        } else if (node.getDepth() == limit) {
            System.out.println("cutoff");
            return null;
        } else {

            List<Node> successors = Successor.getSuccessors(problem); // automatically generates children
            for (Node s : successors) {

            }
        }

        return null;
    }

    /**
     * Iterative Deepening Search (IDS)
     */
    public void iterativeDeepening(int depthLimit) {

    }


    /**
     * Helper Functions
     */
    private void printSolution(int totalNodes, long ms, Node childAtGoal) {
        printTotalNodesGenerated(totalNodes);
        printFormattedRunTime(ms);
        printPathAndPathLength(childAtGoal);
    }
    private static void printFormattedRunTime(long ms) {
        long seconds = ms / 1000;
        long remainingMilliseconds = ms % 1000;
        System.out.println("Total time taken: " + seconds + "sec " + remainingMilliseconds + "ms");
    }
    private void printTotalNodesGenerated(int totalNodes) {
        System.out.println("Total nodes generated: " + totalNodes);
    }
    private void printPathAndPathLength(Node goal) {
        Stack<Action> solution = new Stack<>();
        while (goal.getParent() != null) {
            solution.add(goal.getAction());
            goal = goal.getParent();
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
