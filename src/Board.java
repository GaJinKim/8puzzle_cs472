import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    // actions
//    public static Action LEFT = new DynamicAction("L");

    private char[][] state;

    public Board() {}

    public Board (File f) {
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

        state = puzzle;
    }

    /**
     * Prints out the given puzzle
     */
    public void printBoard() {
        System.out.println("+-------+");
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (j == 0)
                    System.out.print("| ");
                System.out.print(state[i][j] + " ");
                if (j == 2)
                    System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("+-------+");
    }

    /**
     * Determines if given puzzle is solvable based on number of inversions
     *
     * @return True if solvable; false otherwise
     */
    public Boolean isSolvable() {
        int inversions = 0;
        ArrayList<Integer> tiles = new ArrayList<Integer>();

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] != '_') {
                    tiles.add(Integer.parseInt(Character.toString(state[i][j])));
                }
            }
        }

        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                if (tiles.get(i) > tiles.get(j)) {
                    inversions++;
//                    System.out.println("(" + tiles.get(i) +","+ tiles.get(j) +")");
                }
            }
        }
        return inversions % 2 == 0; // solvable if number of inversions is even
    }
}
