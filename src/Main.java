import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] arg) {
        Scanner scan = new Scanner(System.in);
        boolean again = true;

        while (again) {
            System.out.println("Enter file path (e.g. \"./puzzles/Part2/S2.txt\"):");
            Node initialState = new Node(new File(scan.next()));

            // before attempting to solve
            if (!initialState.isSolvable()) {
                System.out.println("Board is not solvable (odd number of inversions: " + initialState.numInversions() + ")");
            }
            else {
                // before attempting to solve
                Search search = new Search(initialState);
                if (search.atGoalState(initialState)) {
                    System.out.println("Board is already at goal state");
                }

                // attempt to solve
                else {
                    System.out.println("Enter Algorithm (e.g. \"BFS\", \"IDS\", \"h1\", \"h2\", \"h3\")");
                    String algorithm = scan.next();

                    switch (algorithm) {
                        case "BFS":
                            search.breadthFirstSearch();
                        case "IDS":
                            search.iterativeDeepeningSearch();

                        default:
                            System.out.println("Error: Please re-enter algorithm");
                    }
                }
            }
            System.out.println("\nEnd Program? (y/n)");
            String endProgram = scan.next();
            again = endProgram.equals("y") ? false : true;
        }
    }
}
