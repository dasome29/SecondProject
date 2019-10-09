package BinaryTree;

import GUI.SearchBar;
import LinkedArrayList.LinkedArrayList;

import java.text.Collator;
import java.util.Collections;

public class Main {

    public static void main(String[] args){
        LinkedArrayList<Integer> list = new LinkedArrayList<Integer>();
        list.addLast(1);
        list.addLast(6);
        list.addLast(8);
        list.addLast(7);
        list.addLast(5);


        list.replace(2, 254);

        String name = "Prueba2";
        String name2 = "zaragoza";

        int[] e = new int[] { 2,4,7,6,8};
        insertionSort(e);
        for(int i : e){
            System.out.println(i);
        }


        System.out.println(name.compareTo(name2));
    }

    public static void insertionSort(int[] A) {
        int in = 0;
        int out = 0;
        for (out = 1; out < A.length; out++) {
            int temp = A[out];
            in = out;
            while (in > 0 && A[in - 1] >= temp) {
                A[in] = A[in - 1];
                --in;
            }
            A[in] = temp;
        }
    }
}
