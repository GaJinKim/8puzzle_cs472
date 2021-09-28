import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] arg) {
        Scanner scan = new Scanner(System.in);
        Boolean again = true;

        while (again) {
            System.out.println("Enter file path (e.g. \"./puzzles/Part2/S2.txt\"):");
            String filePath = scan.next();

            System.out.println("Enter Algorithm (e.g. \"BFS\")");
            String algorithm = scan.next();

            Node board = new Node(new File(filePath));

            System.out.println(board.toString());

            // determine if valid 8 puzzle
            if (!board.isSolvable()) {
                System.out.println("Board is not solvable (odd number of inversions: " + board.numInversions() + ")");
            } else {
                board.moveDown(); // TODO: Testing
                System.out.println(board.toString()); // TODO: Testing
                board.moveRight();
                System.out.println(board.toString());
                board.moveUp();
                System.out.println(board.toString());
                board.moveLeft();
                System.out.println(board.toString());

            }
            System.out.println("\nEnd Program? (y/n)");
            String endProgram = scan.next();
            again = endProgram.equals("y") ? false : true;
        }
    }


}
