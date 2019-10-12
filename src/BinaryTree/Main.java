package BinaryTree;

import FileReader.RadixSort;
import GUI.SearchBar;
import LinkedArrayList.LinkedArrayList;
import FileReader.RadixSort;

import java.io.FileReader;
import java.text.Collator;
import java.util.Collections;

public class Main {

    public static void main(String[] args){
        LinkedArrayList<Integer> list = new LinkedArrayList<Integer>();
//        list.addLast(1);
//        list.addLast(6);
//        list.addLast(8);
//        list.addLast(7);
//        list.addLast(5);
//
//
//        list.replace(2, 254);
//
//        String name = "Prueba2";
//        String name2 = "zaragoza";
//
//
//        int c = 10 > 20 ? 30 : 40;
//        System.out.println(c);
//
//
//        System.out.println(name.compareTo(name2));
        list.addLast(10);
        list.addLast(74);
        list.addLast(29);
        list.addLast(68);
        list.addLast(57);
        list.addLast(21);
        list.addLast(16);
        list.addLast(165);
        list.addLast(124);
        list.addLast(135);

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getElement(i));
        }
        System.out.println("-----------");

        list = RadixSort.radixSort(list);

        for (int i = 0; i < list.getSize(); i++) {
            System.out.println(list.getElement(i));
        }

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
