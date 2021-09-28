import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    // actions
//    public static Action LEFT = new DynamicAction("L");

    private char[] state;

    public Board (File f) {
        char[] puzzle = new char[9];

        try {
            Scanner fScan = new Scanner(f);
            for (int i = 0; i < puzzle.length; i++) {
                puzzle[i] = fScan.next().charAt(0);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        state = puzzle;
    }

    public char[] getState() {
        return state;
    }
    public String path = "";

    /**
     * String representation of given puzzle
     */
    public String toString() {
        return "+-------+" + "\n" +
                "| " + state[0] + " " + state[1] + " " + state[2] + " |" + "\n" +
                "| " + state[3] + " " + state[4] + " " + state[5] + " |" + "\n" +
                "| " + state[6] + " " + state[7] + " " + state[8] + " |" + "\n" +
                "+-------+";
    }

    /**
     * Determines if given puzzle is solvable based on number of inversions
     *
     * @return True if solvable; false otherwise
     */
    public Boolean isSolvable() {
        int inversions = 0;

        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                if (state[i] > state[j]) {
                    inversions++;
//                    System.out.println("(" + tiles.get(i) +","+ tiles.get(j) +")");
                }
            }
        }
        return inversions % 2 == 0; // solvable if number of inversions is even
    }
}
