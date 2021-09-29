import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] arg) {
        Scanner scan = new Scanner(System.in);
        boolean again = true;

        while (again) {
            System.out.println("Enter file path (e.g. \"./puzzles/Part2/S2.txt\"):");
            String filePath = scan.next();

            Node initialState = new Node(new File(filePath));

            // determine if valid 8 puzzle
            if (!initialState.isSolvable()) {
                System.out.println("Board is not solvable (odd number of inversions: " + initialState.numInversions() + ")");
            } else {
                System.out.println("Enter Algorithm (e.g. \"BFS\", \"IDS\", \"h1\", \"h2\", \"h3\")");
                String algorithm = scan.next();

                Search search = new Search(initialState);

                switch(algorithm) {
                    case "BFS":
                        search.breadthFirstSearch();
                }

            }
            System.out.println("\nEnd Program? (y/n)");
            String endProgram = scan.next();
            again = endProgram.equals("y") ? false : true;
        }
    }


}
