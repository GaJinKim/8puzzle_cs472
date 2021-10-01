import java.lang.reflect.Array;
import java.util.*;

public class Search {
    private static final long fifteenMinInMs = 900000;
    private static final char[] goalState = new char[] {'1','2','3','4','5','6','7','8','_'};

    private Node root; // initial state
    private int totalNodes;

    /**
     * Constructor
     */
    public Search(Node root) {
        this.root = root;
        totalNodes = 0;
    }

    /**
     * Getters
     */
    public char[] getGoalState() { return goalState; }


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

        Set<String> closed = new HashSet<String>(); // closed list (explored set)
        Queue<Node> fringe = new LinkedList<Node>(); // open list (not yet explored set)
        fringe.add(new Node(problem));

        Node current = new Node(problem.getState());
        while (!atGoalState(current) && System.currentTimeMillis() - startTime < fifteenMinInMs) {
            closed.add(Arrays.toString(current.getState()));
            List<char[]> successors = Successor.getSuccessorStates(current);
            for (char[] state : successors) {
                // if we've already visited this state before
                if (closed.contains(state))
                    continue;
                closed.add(Arrays.toString(state));

                // new child node generated
                Node child = new Node(state);
                totalNodes++;
                System.out.println(totalNodes);

                current.addChild(child);
                child.setParent(current);
                child.setDepth(current.getDepth() + 1);
                child.setAction(Successor.getDirection(child.getState(), current.getState()));
                fringe.add(child);
            }
            current = fringe.poll();
        }
        long execTime = System.currentTimeMillis() - startTime;
        if (execTime > fifteenMinInMs)
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
        while (System.currentTimeMillis() - startTime < fifteenMinInMs) {
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
     * A Star (with heuristics)
     *
     * h1 - misplaced tiles
     * h2 - manhattan distance
     */
    public void aStar(Node problem, String heuristic) {
        long startTime = System.currentTimeMillis();

        HashSet<String> closed = new HashSet<String>();
        PriorityComparator compare = new PriorityComparator();

        PriorityQueue<Node> fringe = new PriorityQueue<Node>(compare);
        Node currentNode = new Node(problem);
        currentNode.setCost(0);
        currentNode.setTotalCost(0);
        fringe.add(currentNode);

        while (!atGoalState(currentNode) && System.currentTimeMillis() - startTime < fifteenMinInMs) {
            closed.add(Arrays.toString(currentNode.getState()));

            List<char[]> successors = Successor.getSuccessorStates(currentNode);
            System.out.println(successors.size());
            for (char[] n : successors) {
                if (closed.contains(Arrays.toString(n))) {
                    continue;
                }
                closed.add(Arrays.toString(n));

                // new child
                Node child = new Node(n);
                totalNodes++;

                currentNode.addChild(child);
                child.setParent(currentNode);
                child.setDepth(currentNode.getDepth() + 1);
                child.setCost(currentNode.getCost());
                child.setAction(Successor.getDirection(child.getState(), currentNode.getState()));

                // f(n) = total cost = g(n) + h(n)
                // g(n) = cost (to get to this point)
                // h(n) = estimated cost
                if (heuristic.equals("h1")) {
                    child.setTotalCost(child.getCost() + heuristicOne(child.getState(), getGoalState()));
                    System.out.println(Arrays.toString(child.getState()) + " " + child.getTotalCost());
                }
                fringe.add(child);
            }
            currentNode = fringe.poll();
        }
        long execTime = System.currentTimeMillis() - startTime;
        if (execTime > fifteenMinInMs)
            printTimeout();
        else
            printSolution(totalNodes, execTime, currentNode);
    }

    /**
     * Heuristics
     *
     * heuristicOne - misplaced tiles
     *
     * heuristicsTwo - manhattan distance
     */

    private int heuristicOne(char[] current, char[] goal) {
        int misplaced = 0;
        for (int i = 0; i < current.length; i++) {
            if (current[i] != goal[i])
                misplaced++;
        }
        return misplaced;
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
