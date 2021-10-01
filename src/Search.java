import java.util.*;

public class Search {
    private static final long fifteenMinInMs = 900000;
    private static final char[] goalState = new char[] {'1','2','3','4','5','6','7','8','_'};

    private int totalNodes;

    /**
     * Constructor
     */
    public Search() {
        // initial state
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

        HashSet<String> closed = new HashSet<String>(); // closed list (explored set)
        Queue<Node> fringe = new LinkedList<Node>(); // open list (not yet explored set)
        fringe.add(new Node(problem));

        Node current = new Node(problem.getState());
        while (!atGoalState(current) && System.currentTimeMillis() - startTime < fifteenMinInMs) {
            closed.add(Arrays.toString(current.getState()));
            List<char[]> successors = Successor.getSuccessorStates(current);
            for (char[] state : successors) {
                // if we've already visited this state before
                if (closed.contains(Arrays.toString(state)))
                    continue;
                closed.add(Arrays.toString(state));

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
        } else if (node.getDepth() == limit) {
            return "cutoff";
        } else {
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
            if (cutoffOccurred)
                return "cutoff";
            else
                return "failure";
        }
    }

    /**
     * A Star (with heuristics)
     *
     * h1 - misplaced tiles
     * h2 - manhattan distance
     * h3 -
     */
    public void aStarSearch(Node problem, String heuristic) {
        long startTime = System.currentTimeMillis();

        HashSet<String> closed = new HashSet<String>();

        PriorityQueue<Node> fringe = new PriorityQueue<Node>(new PriorityComparator());
        Node current = new Node(problem);
        while (!atGoalState(current) && System.currentTimeMillis() - startTime < fifteenMinInMs) {
            closed.add(Arrays.toString(current.getState()));
            List<char[]> successors = Successor.getSuccessorStates(current);
            for (char[] state : successors) {
                if (closed.contains(Arrays.toString(state)))
                    continue;
                closed.add(Arrays.toString(state));

                // new child
                Node child = new Node(state);
                totalNodes++;

                current.addChild(child);
                child.setParent(current);
                child.setDepth(current.getDepth() + 1);
                child.setCost(current.getTotalCost());
                child.setAction(Successor.getDirection(child.getState(), current.getState()));

                switch (heuristic) {
                    case "h1":
                        child.setTotalCost(child.getCost() + heuristicOne(child.getState(), getGoalState()));
                        break;
                    case "h2":
                        child.setTotalCost(child.getCost() + heuristicTwo(child.getState(), getGoalState()));
                        break;
                    case "h3":
                        child.setTotalCost(child.getCost() + heuristicThree(child.getState(), getGoalState()));
                        break;
                }
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
     * Heuristics
     *
     * heuristicOne - misplaced tiles
     *
     * heuristicsTwo - manhattan distance
     *
     * heuristicThree - weighted misplaced tiles
     */
    private int heuristicOne(char[] current, char[] goal) {
        int misplaced = 0;
        for (int i = 0; i < current.length; i++) {
            if (current[i] != goal[i])
                misplaced++;
        }
        return misplaced;
    }

    private int heuristicTwo(char[] current, char[] goal) {
        int distance = 0;
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                if (current[i] == goal[j])
                    distance += ((Math.abs(i % 3 - j % 3)) + Math.abs(i / 3 + j / 3));
            }
        }
        return distance;
    }

    /**
     * Custom heuristic: weighted misplaced tiles
     *
     * This heuristic sums the differences between current and goal tiles.
     * The idea is the further away a square is from its goal, the greater the penalty.
     *
     * (gap is 9)
     *
     * For example:
     * 8 1 2
     * 3 4 5
     * 6 7 _
     * would have a penalty of |8-1| + |2-1| + |3-2| + |4-3| + |5-4| + |6-5) + |7-6| + |8-7| + |9-9|
     *                        = 7 + 1 + 1 + 1 + 1 + 1 + 1 + 1 = 14
     *
     * 1 2 3
     * 4 5 6
     * 7 _ 8
     * would have a penalty of |1-1| + |2-2| + |3-3| + |4-4| + ... + |9-8| + |8-9| = 2
     */
    private int heuristicThree(char[] current, char[] goal) {
        int val = 0;

        int[] c = new int[9];
        int[] g = new int[9];

        // convert gap to 9
        for (int i = 0; i < current.length; i++) {
            if (current[i] == '_')
                c[i] = 9;
            else
                c[i] = Character.getNumericValue(current[i]);

            if (goal[i] == '_')
                g[i] = 9;
            else
                g[i] = Character.getNumericValue(goal[i]);
        }

        // smaller is better
        for (int i = 0; i < current.length; i++) {
            val += Math.abs(c[i] - g[i]);
        }
        return val;
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
