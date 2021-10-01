import java.util.ArrayList;
import java.util.List;

public class Successor {

    /**
     * Retrieves a list of valid actions given a node
     */
    public static List<Action> getActions(Node node) {
        List<Action> actions = new ArrayList<Action>();
        int gapPosition = node.getGapPosition();

        /**
         * Board Positions
         *
         * 0 1 2
         * 3 4 5
         * 6 7 8
         */
        switch(gapPosition) {
            case 0:
                actions.add(Action.L);
                actions.add(Action.U);
                break;
            case 1:
                actions.add(Action.R);
                actions.add(Action.L);
                actions.add(Action.U);
                break;
            case 2:
                actions.add(Action.R);
                actions.add(Action.U);
                break;
            case 3:
                actions.add(Action.D);
                actions.add(Action.U);
                actions.add(Action.L);
                break;
            case 4:
                actions.add(Action.D);
                actions.add(Action.U);
                actions.add(Action.L);
                actions.add(Action.R);
                break;
            case 5:
                actions.add(Action.D);
                actions.add(Action.U);
                actions.add(Action.R);
                break;
            case 6:
                actions.add(Action.D);
                actions.add(Action.L);
                break;
            case 7:
                actions.add(Action.L);
                actions.add(Action.R);
                actions.add(Action.D);
                break;
            case 8:
                actions.add(Action.R);
                actions.add(Action.D);
                break;
        }
        return actions;
    }

    public static List<char[]> getSuccessorStates(Node node) {
        List<char[]> successorStates = new ArrayList<char[]>();
        int gapPosition = node.getGapPosition();

        Node leftNode = new Node(node);
        leftNode.moveLeft();

        Node rightNode = new Node(node);
        rightNode.moveRight();

        Node upNode = new Node(node);
        upNode.moveUp();

        Node downNode = new Node(node);
        downNode.moveDown();

        switch(gapPosition) {
            case 0:
                successorStates.add(leftNode.getState());
                successorStates.add(upNode.getState());
                break;
            case 1:
                successorStates.add(rightNode.getState());
                successorStates.add(leftNode.getState());
                successorStates.add(upNode.getState());
                break;
            case 2:
                successorStates.add(rightNode.getState());
                successorStates.add(upNode.getState());
                break;
            case 3:
                successorStates.add(downNode.getState());
                successorStates.add(upNode.getState());
                successorStates.add(leftNode.getState());
                break;
            case 4:
                successorStates.add(downNode.getState());
                successorStates.add(upNode.getState());
                successorStates.add(leftNode.getState());
                successorStates.add(rightNode.getState());
                break;
            case 5:
                successorStates.add(downNode.getState());
                successorStates.add(upNode.getState());
                successorStates.add(rightNode.getState());
                break;
            case 6:
                successorStates.add(downNode.getState());
                successorStates.add(leftNode.getState());
                break;
            case 7:
                successorStates.add(leftNode.getState());
                successorStates.add(rightNode.getState());
                successorStates.add(downNode.getState());
                break;
            case 8:
                successorStates.add(rightNode.getState());
                successorStates.add(downNode.getState());
                break;
        }
        return successorStates;
    }

    /**
     * Retrieves a list of successors given a node and its allowed actions
     *
     * @param node node to which we want to find viable successors
     * @return list of reachable nodes
     */
    public static List<Node> getSuccessors(Node node) {
        List<Node> successors = new ArrayList<Node>();
        int gapPosition = node.getGapPosition();

        Node leftNode = new Node(node);
        leftNode.moveLeft();
        leftNode.setParent(node); // needs a reference to its parent
        leftNode.setDepth(node.getDepth() + 1); // increment depth

        Node rightNode = new Node(node);
        rightNode.moveRight();
        rightNode.setParent(node);
        rightNode.setDepth(node.getDepth() + 1); // increment depth

        Node upNode = new Node(node);
        upNode.moveUp();
        upNode.setParent(node);
        upNode.setDepth(node.getDepth() + 1); // increment depth

        Node downNode = new Node(node);
        downNode.moveDown();
        downNode.setParent(node);
        downNode.setDepth(node.getDepth() + 1); // increment depth

        /**
         * Board Positions
         *
         * 0 1 2
         * 3 4 5
         * 6 7 8
         */
        switch(gapPosition) {
            case 0:
                successors.add(leftNode);
                successors.add(upNode);
                break;
            case 1:
                successors.add(rightNode);
                successors.add(leftNode);
                successors.add(upNode);
                break;
            case 2:
                successors.add(rightNode);
                successors.add(upNode);
                break;
            case 3:
                successors.add(downNode);
                successors.add(upNode);
                successors.add(leftNode);
                break;
            case 4:
                successors.add(downNode);
                successors.add(upNode);
                successors.add(leftNode);
                successors.add(rightNode);
                break;
            case 5:
                successors.add(downNode);
                successors.add(upNode);
                successors.add(rightNode);
                break;
            case 6:
                successors.add(downNode);
                successors.add(leftNode);
                break;
            case 7:
                successors.add(leftNode);
                successors.add(rightNode);
                successors.add(downNode);
                break;
            case 8:
                successors.add(rightNode);
                successors.add(downNode);
                break;
        }
        return successors;
    }

    /**
     * Based on the position of the relative position of the gaps, we can determine the direction
     */
    public static Action getDirection(char[] child, char[] parent) {
        if (getGapPosition(child) - getGapPosition(parent) == 3) {
            return Action.U;
        } else if (getGapPosition(child) - getGapPosition(parent) == -3) {
            return Action.D;
        } else if (getGapPosition(child) - getGapPosition(parent) == 1) {
            return Action.L;
        } else {
            return Action.R;
        }
    }

    public static int getGapPosition(char[] state) {
        return getPositionOf(state,'_');
    }

    public static int getPositionOf(char[] state, char val) {
        for (int i = 0; i < 9; i++)
            if (state[i] == val)
                return i;
        return -1;
    }

}
