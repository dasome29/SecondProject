package FileReader;

import LinkedArrayList.LinkedArrayList;

public class RadixSort{


    public static LinkedArrayList<Integer> radixSort(LinkedArrayList<Integer> list){
        int length = String.valueOf(getMax(list)).length();
        for (int i = 1; i <= length; i++) {
            list = sort(positions(createList(10), (int) Math.pow(10, i), list), (int) Math.pow(10, i), list);
        }
        return list;
    }
    private static LinkedArrayList<Integer> createList(int num){
        LinkedArrayList<Integer> linkedArrayList = new LinkedArrayList<Integer>();
        for (int i = 0; i < num; i++) {
            linkedArrayList.addLast(0);
        }
        return linkedArrayList;
    }
    private static LinkedArrayList<Integer> sumList(LinkedArrayList<Integer> nums){
        int count = 0;
        for (int i = 0; i < nums.getSize(); i++) {
            count +=nums.getElement(i);
            nums.replace(i, count);
        }
        return nums;
    }
    private static int getMax(LinkedArrayList<Integer> list){
        int max = 0;
        for (int i = 0; i < list.getSize(); i++) {
            if(list.getElement(i)>max){
                max=list.getElement(i);
            }
        }
        return max;
    }
    private static LinkedArrayList<Integer> positions(LinkedArrayList<Integer> nums, int module, LinkedArrayList<Integer> list){
        for (int i = 0; i < list.getSize(); i++) {
            int x = (int) (list.getElement(i)%module/Math.pow(10, (Math.log10(module)-1)));
            int y = nums.getElement((int) (((list.getElement(i)%module)/Math.pow(10, (Math.log10(module)-1)))))+1;
            nums.replace(x, y);
        }
        return nums;
    }
    private static LinkedArrayList<Integer> sort(LinkedArrayList<Integer> nums, int module, LinkedArrayList<Integer> list){
        LinkedArrayList<Integer> temp = createList(list.getSize());
        LinkedArrayList<Integer> newNums = sumList(nums);
        for (int i = list.getSize()-1; i >= 0; i--) {
            int num = (int) (list.getElement(i)%module/Math.pow(10, (Math.log10(module)-1)));
            newNums.replace(num,newNums.getElement(num)-1 );
            temp.replace(newNums.getElement(num),list.getElement(i));
        }
        list = temp;
        return list;
    }
}
