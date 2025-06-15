import java.util.ArrayList;

public class BST {
    private int numberOfNodes=0;
    private int numberOfLeafNodes=0;
    class Node{
        int data;
        Node left;
        Node right;
        public String toString(){
            return ("Node Value : "+ data);
        }
    }
    Node root=new Node();

    //the constructor
    BST(int rootData){
        root.data=rootData;
        numberOfNodes++;
    }//here

    //the search methods
    Node searchNode(int data){
        Node node = root;
        while(node!=null&&node.data!=data){
            if (node.data>data){
                node = node.left;
            }
            else{
                node=node.right;
            }
        }
        return node;// node can be either null of the found node (on the both hand we are good)

        //this is the non-recursive method to search for a node
    }
    public Node recursiveSearch(int data){
        return recursiveSearch(root,data);
    }
    private Node recursiveSearch(Node startNode,int data){

        // the conditions the stop the recursion
        if (startNode == null){return null;}

        if (startNode.data == data){return startNode;}

        // the way of recursion
        if (startNode.data>data) {
            startNode = recursiveSearch(startNode.left,data);
            return startNode;
        }
        else {
            return recursiveSearch(startNode.right,data);
        }
    }

    //the add methods
    void addNodeAsALeaf(int data){
        Node leafNod= new Node();
        leafNod.data=data;
        //in this method we walk through the tree the same way when searching ,and we state our node when we see a "null"
        Node node = root;

        while (node!=null){
            if (node.data >= data){//look at the equality condition
                if (node.left==null){
                    node.left=leafNod;
                    numberOfNodes++;
                    break;
                }
                node=node.left;
            }
            else if (node.data<data){
                if (node.right==null) {
                    node.right=leafNod;
                    numberOfNodes++;
                    break;
                }
                node=node.right;
            }
            else {
                /*to handel the equal condition there are two ways to do it
                *
                * solution number one(that I'm going to use):
                * allow the equality when add the node on the left or on the right(in my code I'm going to allow it on
                * the left)
                *
                * solution number two:
                * add a counter the Node class and add it by one whenever this node is added twice or more
                * */
            }
        }


    }
    public void insertAsRoot(int data) {
        numberOfNodes++;
        root = insertAtRoot(root, data);
    }

    // Recursive method to insert and rotate to root
    private Node insertAtRoot(Node node, int data) {

        if (node == null){
            Node node1 = new Node();
            node1.data=data;
            return node1;
        }

        if (data < node.data) {
            node.left = insertAtRoot(node.left, data);
            node = rotateRight(node);
        } else {
            node.right = insertAtRoot(node.right, data);
            node = rotateLeft(node);
        }
        return node;
    }
    // Right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }
    // Left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }

    //the deletion method
    void deleteTheNode(int data){

        numberOfNodes--;
        //delete the root
        if (data==root.data){
            if (root.right==null){
                root=root.left;
                return;
            }


            if (root.right.left==null){
                root.data=root.right.data;
                root.right=null;
                return;
            }

            Node smallestBiggerNumber = root.right;
            Node fatherSmallestOfTheSmallestBN=root;
            while (smallestBiggerNumber.left!=null){
                fatherSmallestOfTheSmallestBN=smallestBiggerNumber;
                smallestBiggerNumber=smallestBiggerNumber.left;
            }
            root.data=smallestBiggerNumber.data;
            fatherSmallestOfTheSmallestBN.left=null;
            return;
        }

        ArrayList infoAboutTheNodeWeWantToDelete = searchNodeForDeletion(data);

        Node father = (BST.Node)infoAboutTheNodeWeWantToDelete.get(0);
        Node son = (BST.Node)infoAboutTheNodeWeWantToDelete.get(1);
        Node theSmallestBiggerNumber = findTheSmallestBiggerNumber(data);

        if (son==null){
            numberOfNodes++;
            System.out.println("This node( "+ data +" )does not exist! ");
            return;
        }


        // the node is a leaf
        if (son.left == null && son.right==null){
            if (father.left==son){
                father.left=null;
            }
            else father.right=null;
        }

        //the node has only one son
        else if ((son.right==null&&son.left!=null)||(son.right!=null&&son.left==null)){
            if (son.right==null&&son.left!=null){
                if (father.left==son){
                    father.left=son.left;
                }
                else father.right=son.left;
            }
            else {
                if (father.left==son){
                    father.left=son.right;
                }
                else father.right=son.right;
            }
        }


        //the node has two sons
        else {
            if (father.left==son){
                Node fatherOfTheSmallestBiggerNumber = (BST.Node)searchNodeForDeletion(theSmallestBiggerNumber.data).get(0);
                if (fatherOfTheSmallestBiggerNumber.left==theSmallestBiggerNumber){
                    fatherOfTheSmallestBiggerNumber.left=null;
                }
                else fatherOfTheSmallestBiggerNumber.right=null;

                father.left=theSmallestBiggerNumber;
                theSmallestBiggerNumber.left=son.left;
                theSmallestBiggerNumber.right=son.right;
            }
            else {
                Node fatherOfTheSmallestBiggerNumber = (BST.Node)searchNodeForDeletion(theSmallestBiggerNumber.data).get(0);
                if (fatherOfTheSmallestBiggerNumber.left==theSmallestBiggerNumber){
                    fatherOfTheSmallestBiggerNumber.left=null;
                }
                else fatherOfTheSmallestBiggerNumber.right=null;


                father.right=theSmallestBiggerNumber;

                theSmallestBiggerNumber.left=son.left;
                theSmallestBiggerNumber.right=son.right;

            }
        }
    }
    Node findTheSmallestBiggerNumber(int data){


        ArrayList infoAboutTheSearchedNode= searchNodeForDeletion(data);
        //info..[0] is the father
        //info..[1] is the son
        //info..[2] is the number representing the bigger number than the node but in the upward direction
        Node foundNode = (BST.Node)infoAboutTheSearchedNode.get(1);
        Node upperNumberBiggerThanTheData = (BST.Node)infoAboutTheSearchedNode.get(2);

        if (foundNode== null){
            // the node that the user provided does not exist
            return null;

        }
        else {
            if (/*node.left==null &&*/ foundNode.right==null){
                // if the father is bigger return the father
                // if the father is smaller return the node
                if (upperNumberBiggerThanTheData == null){
                    return foundNode;
                }
                else {return upperNumberBiggerThanTheData;}
            }
            else {
                foundNode = foundNode.right;
                while (foundNode.left!=null){
                    foundNode=foundNode.left;
                }
                return foundNode;
            }
        }
    }
    ArrayList searchNodeForDeletion(int data){
        //this function does not handel the deletion of the root
        // make sure that you handel it in the deletion function

        ArrayList prevCurrentInt = new ArrayList<>();
        Node biggerThanTheDataAllTheWayUp = null;
        Node previous = root;
        Node current;
        if (root.data>data){
            biggerThanTheDataAllTheWayUp=root;
            current=root.left;}
        else {current=root.right;}
        while(current!=null&&current.data!=data){
            if (current.data>data){
                biggerThanTheDataAllTheWayUp=current;
                previous=current;
                current = current.left;
            }
            else{
                previous = current;
                current=current.right;
            }
        }
        prevCurrentInt.add(previous);
        prevCurrentInt.add(current);
        prevCurrentInt.add(biggerThanTheDataAllTheWayUp);
        return prevCurrentInt ;
    }


    //extra things related to the BST in general
    void theMaxAndMinimumHeightBasedOnTheNodeNumber(){
        System.out.println("The max height possible to present a normal tree is: "+ (numberOfNodes-1));
        System.out.println("The minimum height possible to present a normal tree is: " + Math.floor(Math.log(numberOfNodes)/Math.log(2)));//log2(number of the nodes)
    }
    void theTotalNumberOfTheNodes(){
        System.out.println("The total number of the node: "+ numberOfNodes);
    }

    public void theNumberOfTheLeafs(){
        numberOfLeafNodes=0;
        theNumberOfTheLeafs(root);
    }
    private void theNumberOfTheLeafs(Node node){
        if (node==null){
            return ;
        }

        if (node.left==null&&node.right==null){
            numberOfLeafNodes++;
            return;
        }
        theNumberOfTheLeafs(node.left);
        theNumberOfTheLeafs(node.right);
    }
    public void theNumberOfNonLeafNodes(){
        theNumberOfTheLeafs();
        System.out.println("The number of the non-leaf nodes: "+ (numberOfNodes-numberOfLeafNodes));
    }
    public void printTheNumberOfTheLeafNode(){
        theNumberOfTheLeafs();
        System.out.println("The number of leaf: "+numberOfLeafNodes);
    }
    void printTree(Node root) {
        if (root==null){
            return;
        }
        if (root.left==null && root.right==null){
            return;
        }
        System.out.print(root.data + " --> ");
        if (root.left==null){
            System.out.print(" [No Left Son] ");
        }
        else {
            System.out.print(" [Left Son] "+root.left.data);
        }

        if (root.right==null){
            System.out.println(" [No Right Son] ");
        }
        else {
            System.out.println(" [Right Son] "+ root.right.data);
        }
        printTree(root.right);
        printTree(root.left);
    }

}
