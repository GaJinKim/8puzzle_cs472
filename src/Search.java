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

                // new child node generated
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
     * Iterative Deepening Search (IDS)
     */
    public void iterativeDeepening(Node problem) {
        long startTime = System.currentTimeMillis();
        int depth = 0;
        while (System.currentTimeMillis() - startTime < 900000) {
            String result = depthLimitedSearch(problem, depth, startTime);
            if (!result.equals("cutoff")) {
                break;
            }
            depth++;
        }
    }

    public String depthLimitedSearch(Node problem, int limit, long startTime) {
        return recursiveDLS(new Node(problem.getState()), problem, limit, startTime);
    }

    public String recursiveDLS(Node node, Node problem, int limit, long startTime) {
        boolean cutoffOccurred = false;
        if (atGoalState(node)) {
            printSolution(totalNodes,System.currentTimeMillis() - startTime, node);
            return "solution";
        }

        else if (node.getDepth() == limit) {
            return "cutoff";
        }

        else {
            List<char[]> successors = Successor.getSuccessorStates(node);
            for (char[] state : successors) {

                // new child node generated
                Node child = new Node(state);
                totalNodes++;

                node.addChild(child);
                child.setParent(node);
                child.setDepth(node.getDepth() + 1);
                child.setAction(Successor.getDirection(child.getState(), node.getState()));

                String result = recursiveDLS(child, problem, limit, startTime);
                if (result.equals("cutoff")) {
                    cutoffOccurred = true;
                } else if (!result.equals("failure")) {
                    return result;
                }
            }
            if (cutoffOccurred) {
                return "cutoff";
            } else {
                return "failure";
            }
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
