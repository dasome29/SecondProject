package BinaryTree;


import LinkedArrayList.LinkedArrayList;

public class Main {

    public static void main(String[] args){
        BinaryTree test = new BinaryTree();
        LinkedArrayList<Integer> lista = new LinkedArrayList<Integer>();
//


        String name = "informeMovimientoParábolico";
        String name2 = "Avion";


        int[] temp = new int[] {5,8,6,8,7,5,6,2,4};
        bubbleSort(temp);



        System.out.println(name2.compareTo(name));
    }

    public static int[] bubbleSort(int[] array){
        for(int i=0; i < array.length -1; i++) {
            for (int current = 0; current < array.length -i-1; current++) {
                if (array[current] > array[current + 1]) {
                    int temp = array[current];
                    array[current] = array[current + 1];
                    array[current + 1] = temp;
                }
            }
        }
        return array;
    }
}
