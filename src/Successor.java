import java.util.ArrayList;
import java.util.List;

public class Successor {

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

}
