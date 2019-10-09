package BinaryTree;

import LinkedArrayList.LinkedArrayList;

public class Main {

    public static void main(String[] args){
        LinkedArrayList<Integer> list = new LinkedArrayList<Integer>();
        list.addLast(1);
        list.addLast(6);
        list.addLast(8);
        list.addLast(7);
        list.addLast(5);

        list.insert(0, 4);

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getElement(i));
        }
    }
}
