package BinaryTree;

public class Main {

    public static void main(String[] args){

        BinaryTree binaryTree = new BinaryTree();
        binaryTree.insert("Sebastian");
        binaryTree.insert("Mora");
        binaryTree.insert("Godinez");

        Node node = binaryTree.get("Godine");
        System.out.println(node.element);
    }
}
