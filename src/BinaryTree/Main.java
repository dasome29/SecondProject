package BinaryTree;

import LinkedArrayList.LinkedArrayList;

public class Main {

    public static void main(String[] args){
        BinaryTree test = new BinaryTree();
//        LinkedArrayList<Integer> list = new LinkedArrayList<Integer>();
//        list.addLast(1);
//        list.addLast(6);
//        list.addLast(8);
//        list.addLast(7);
//        list.addLast(5);
//
//        list.insert(0, 4);

//        for (int i = 0; i < list.getSize(); i++) {
//            System.out.println(list.getElement(i));
//        }
        test.insert("Pene");
        test.insert("Gordo");
        test.insert("Banano");
        test.insert("Poronga");
        test.insert("Perra");
        test.insert("Puta");
        test.insert("Picha");
        test.insert("Panocha");
        test.insert("Pinga");
        test.insert("Polla");

        test.remove("Pinga");
        System.out.println(test.get("Polla"));
    }
}
