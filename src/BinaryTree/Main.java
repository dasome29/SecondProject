package BinaryTree;

import GUI.SearchBar;
import LinkedArrayList.LinkedArrayList;
import FileReader.RadixSort;

import java.text.Collator;
import java.util.Collections;

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
=========

        list.replace(2, 254);

        String name = "Prueba2";
        String name2 = "zaragoza";


        int c = 10 > 20 ? 30 : 40;
        System.out.println(c);


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
>>>>>>>>> Temporary merge branch 2
    }
}
