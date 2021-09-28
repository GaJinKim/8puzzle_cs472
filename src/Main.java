import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final char[] goalState = {'1','2','3','4','5','6','7','8','_'};

    public static void main (String[] arg) {
        Scanner scan = new Scanner(System.in);
        Boolean again = true;

        while (again) {
            System.out.println("Enter file path (e.g. \"./puzzles/Part2/S2.txt\"):");
            String filePath = scan.next();

            System.out.println("Enter Algorithm (e.g. \"BFS\")");
            String algorithm = scan.next();

            // TODO: comment out the test directory
        File file = new File("./puzzles/Part2/S2.txt");
//            File file = new File(filePath);

            Board board = new Board(file);

//            System.out.println(board.toString()); // TODO: Remove

            // determine if valid 8 puzzle
            if (!board.isSolvable()) {
                System.out.println("Board is not solvable (odd number of inversions: " + board.numInversions() + ")");
            } else {
                board.moveGapUp(); // TODO: Remove
//                System.out.println(board.toString());

            }
            System.out.println("\nEnd Program? (y/n)");
            String endProgram = scan.next();
            again = endProgram.equals("y") ? false : true;
        }
    }

}
