public class Main {
    public static void main(String[] args) {

        BST tree = new BST(15);

        tree.addNodeAsALeaf(20);
        tree.addNodeAsALeaf(24);
        tree.addNodeAsALeaf(18);

        tree.addNodeAsALeaf(10);
        tree.addNodeAsALeaf(13);
        tree.addNodeAsALeaf(14);
        tree.addNodeAsALeaf(11);
        tree.addNodeAsALeaf(7);
        tree.addNodeAsALeaf(8);
        tree.addNodeAsALeaf(5);


        tree.deleteTheNode(15);
        tree.printTree(tree.root);
        System.out.println();
        System.out.println( "the tree after adding 9 \n");
        tree.insertAsRoot(9);
        tree.printTree(tree.root);

        System.out.println("The smallest number that is bigger than 14 in this BST is"+
                tree.findTheSmallestBiggerNumber(14).data);

        System.out.println("The result of searching for the value 7 :"
                +tree.recursiveSearch(7));//same result with the normal search method

        tree.deleteTheNode(15);
        tree.deleteTheNode(8);
        tree.deleteTheNode(7);
        tree.deleteTheNode(100);//trying to delete a node that does not exist
        tree.printTree(tree.root);
        //the extra functions
        tree.theMaxAndMinimumHeightBasedOnTheNodeNumber();
        tree.theTotalNumberOfTheNodes();
        tree.printTheNumberOfTheLeafNode();
        tree.theNumberOfNonLeafNodes();

    }
}
