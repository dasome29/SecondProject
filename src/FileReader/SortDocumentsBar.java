package FileReader;

import GUI.Results;
import GUI.SearchBar;
import LinkedArrayList.LinkedArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;


/**
 * Clase encargada de controlar el ordenamiento de los resultados
 */
public class SortDocumentsBar {
    private Pane root;
    private Pane sortPane;
    private ToggleGroup toggleGroup;
    private RadioButton sortByName;
    private RadioButton sortBySize;
    private RadioButton sortByDate;


    /**
     * Método encargado de establecer la barra de ordenamiento en la parte gráfica de la aplicación
     * @param root panel principal
     */
    public SortDocumentsBar(Pane root){
        this.root = root;

        sortPane = new Pane();
        sortPane.setLayoutX(352);
        sortPane.setLayoutY(100);
        sortPane.setPrefSize(550,35);
        root.getChildren().add(sortPane);

        Label label = new Label("Sort by: ");
        label.setLayoutX(10);
        label.setLayoutY(10);
        sortPane.getChildren().add(label);

        toggleGroup = new ToggleGroup();
        setEventToggleGroup();

        sortByName = new RadioButton();
        sortByName.setToggleGroup(toggleGroup);
        sortByName.setLayoutX(80);
        sortByName.setLayoutY(10);
        sortByName.setText("Name ");

        sortBySize = new RadioButton();
        sortBySize.setToggleGroup(toggleGroup);
        sortBySize.setLayoutX(200);
        sortBySize.setLayoutY(10);
        sortBySize.setText("Size ");

        sortByDate = new RadioButton();
        sortByDate.setToggleGroup(toggleGroup);
        sortByDate.setLayoutX(320);
        sortByDate.setLayoutY(10);
        sortByDate.setText("Date ");


        sortPane.getChildren().addAll(sortByName, sortByDate, sortBySize);

    }

    /**
     * Este método es el encargado de verificar cual de los tres RadioButtons se encuentran seleccionados para llamr al
     * método correspondiente
     */

    private void setEventToggleGroup(){
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                try {
                    if (sortByName.isSelected()) {
                        System.out.println("Ordenar por nombre " + SearchBar.listOfWords.getSize());
                        SortByName(SearchBar.resultsList);
                        System.out.println("Ordenar por nombre " + SearchBar.listOfWords.getSize());

                    }
                    if (sortBySize.isSelected()) {
                        SortBySize();
                        System.out.println("Ordenar por tamano " + SearchBar.listOfWords.getSize());
                    }
                    if (sortByDate.isSelected()) {
                        SortByDate(SearchBar.resultsList);
                        System.out.println("Ordenar por fecha de creacion " + SearchBar.listOfWords.getSize());
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    System.out.println("La lista se encuntra vacia");
                }
            }
        });

    }

    /**
     * Esté metodo es llamado para ordenar los resultados por nombre
     * @param list lista a ordenar
     */

    private void SortByName(LinkedArrayList<Results> list){
        quickSort(list,0, list.getSize()-1);
        SearchBar.updatePositions();
    }


    /**
     * Algortimo de ordenamiento quickSort
     * @param list lista aordenar
     * @param start indide de incio
     * @param end indice final
     */

    private void quickSort(LinkedArrayList<Results> list, int start, int end){
        if(start < end){
            int pos = quickSortAux(list,start, end);

            quickSort(list, start, pos-1);
            quickSort(list,pos+1, end);
        }

    }

    /**
     * Método auxiliar del quickSort
     * @param list lista a ordenar
     * @param start indice de inicio
     * @param end indice final
     * @return nuevo indice
     */
    private int quickSortAux(LinkedArrayList<Results> list, int start, int end) {
        Results pivote = list.getElement(end);
        int i = start-1;
        for(int j=start; j< end; j++){
            if(list.getElement(j).file.getName().toLowerCase().compareTo(pivote.file.getName().toLowerCase()) < 0){
                i++;

                Results temp = list.getElement(i);
                list.replace(i, list.getElement(j));
                list.replace(j, temp);
            }
        }
        Results temp = list.getElement(i+1);
        list.replace(i+1, list.getElement(end));
        list.replace(end, temp);

        return i+1;
    }


    /**
     * Este método es el encargado de ordenar los resultados de la búsqueda utilizando la fecha de creación como
     * referencia. Utiliza el algotritmo de ordenamiento BubbleSort
     * @param list lista a ordenar
     */
    private void SortByDate(LinkedArrayList<Results> list){
        for(int i=0; i<list.getSize(); i++){
            System.out.println(list.getElement(i).file.getName());
        }
        for(int i=0; i < list.getSize()-1; i++) {
            for (int current = 0; current < list.getSize() -i-1; current++) {
                try {
                    FileTime fileTime = getFileTime(list.getElement(current).file);
                    FileTime file1Time2 = getFileTime(list.getElement(current+1).file);
                    System.out.println(list.getElement(current).file.getName() + " " + fileTime + " ||| " + list.getElement(current+1).file.getName()  + " " + file1Time2 + "  res " +   fileTime.compareTo(file1Time2));
                    if(fileTime.compareTo(file1Time2) < 0){
                        Results temp = list.getElement(current);
                        list.replace(current, list.getElement(current+1));
                        list.replace(current+1, temp);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        SearchBar.updatePositions();

    }

    /**
     * Este método retorna la fecha de creación del archivo que ingresa por parámetros
     * @param file archivo para obtener la fehca
     * @return FileTime
     * @throws IOException
     */

    private FileTime getFileTime(File file) throws IOException {
        BasicFileAttributes attributes;
        attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        FileTime file1Time = attributes.creationTime();
        return file1Time;
    }


    /**
     * Este método es el encargado de ordenar los resultados por tamaño del archivo
     */
    private void SortBySize(){
        SearchBar.resultsList = radixSort(SearchBar.resultsList);
        for(int i=0; i<SearchBar.resultsList.getSize();i++){
            System.out.println(SearchBar.resultsList.getElement(i).file.getName());
        }
        SearchBar.updatePositions();
    }


    /**
     * Algoritmo de ordenamiento radixSort
     * @param list
     * @return
     */
    private static LinkedArrayList<Results> radixSort(LinkedArrayList<Results> list){
        int length = String.valueOf(getMax(list)).length();
        System.out.println(length);
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
    private static int getMax(LinkedArrayList<Results> list){
        int max = 0;
        for (int i = 0; i < list.getSize(); i++) {
            if(list.getElement(i).Filesize >max){
                max=list.getElement(i).Filesize;
            }
        }
        return max;
    }
    private static LinkedArrayList<Integer> positions(LinkedArrayList<Integer> nums, int module, LinkedArrayList<Results> list){
        for (int i = 0; i < list.getSize(); i++) {
            int x = (int) (list.getElement(i).Filesize %module/Math.pow(10, (Math.log10(module)-1)));
            int y = nums.getElement((int) (((list.getElement(i).Filesize %module)/Math.pow(10, (Math.log10(module)-1)))))+1;
            nums.replace(x, y);
        }
        return nums;
    }
    private static LinkedArrayList<Results> sort(LinkedArrayList<Integer> nums, int module, LinkedArrayList<Results> list){
        LinkedArrayList<Integer> temp = createList(list.getSize());
        LinkedArrayList<Integer> newNums = sumList(nums);
        for (int i = list.getSize()-1; i >= 0; i--) {
            int num = (int) (list.getElement(i).Filesize %module/Math.pow(10, (Math.log10(module)-1)));
            newNums.replace(num,newNums.getElement(num)-1 );
            temp.replace(newNums.getElement(num),list.getElement(i).Filesize);
        }
        LinkedArrayList<Results> newList = new LinkedArrayList<Results>();
        for (int i = 0; i < temp.getSize(); i++) {
            for (int j = 0; j < list.getSize(); j++) {
                if (temp.getElement(i) == list.getElement(j).Filesize){
                    newList.addLast(list.getElement(j));
                    list.delete(j);
                }
            }
        }
        return newList;
    }
}