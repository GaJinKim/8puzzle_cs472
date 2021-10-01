import java.io.File;
import java.util.Scanner;

public class Main {
    public static long startTime = 0;

    public static void main (String[] arg) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter file path (e.g. \"./puzzles/Part2/S2.txt\"):");
        Node initialState = new Node(new File(scan.next())); // TODO TESTING

        // check if state is solvable
        if (!initialState.isSolvable()) {
            System.out.println("The inputted puzzle is not solvable (odd number of inversions: " + initialState.numInversions() + ")");
            System.out.println(initialState.toString());
        } else {
            // check if already at goal state
            Search search = new Search();
            if (search.atGoalState(initialState)) {
                System.out.print("\nBoard is already at goal state");
            } else {
                System.out.println("Enter Algorithm (e.g. \"BFS\", \"IDS\", \"h1\", \"h2\", \"h3\")");
                String algorithm = scan.next();
                startTime = System.currentTimeMillis();

                switch (algorithm) {
                    case "BFS":
                        search.breadthFirstSearch(initialState);
                        break;
                    case "IDS":
                        search.iterativeDeepening(initialState);
                        break;
                    case "h1":
                        search.aStarSearch(initialState, "h1");
                        break;
                    case "h2":
                        search.aStarSearch(initialState, "h2");
                        break;
                    case "h3":
                        search.aStarSearch(initialState, "h3");
                        break;
                }
            }
        }
    }

}
