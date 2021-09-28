import java.io.File;
import java.io.FileReader;
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

//        File file = new File("./puzzles/Part2/S2.txt");
            File file = new File(filePath);

            char[][] puzzle = generatePuzzle(file);
            printPuzzle(puzzle);

            System.out.println("\nEnd Program? (y/n)");
            String endProgram = scan.next();
            again = endProgram.equals("y") ? false : true;
        }
    }

     // 2d array representation of 8 puzzle
    private static char[][] generatePuzzle(File f) {
        char[][] puzzle = new char[3][3];
        int row = 0;
        int col = 0;

        try {
            Scanner fScan = new Scanner(f);
            while (fScan.hasNext()) {
                puzzle[row][col] = fScan.next().charAt(0);
                col++;
                if (col == 3) {
                    row++;
                    col = 0;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return puzzle;
    }

    // print current 8 puzzle
    private static void printPuzzle(char[][] puzzle) {
        System.out.println("8 Puzzle:");
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
}
