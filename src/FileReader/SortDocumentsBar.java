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
 * Clase que permite el ordenamiento de los resultados
 */
public class SortDocumentsBar {
    private Pane root;
    private Pane sortPane;
    private ToggleGroup toggleGroup;
    private RadioButton sortByName;
    private RadioButton sortBySize;
    private RadioButton sortByDate;


    /**
     * Constructir
     * @param root Panel root
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
     * Método facade que llama los métodos correspondiente dependiendo de la opción elegida para ordenar los resultados
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
                        System.out.println("Ordenar por tamano " + SearchBar.listOfWords.getSize());
                    }
                    if (sortByDate.isSelected()) {
                        //SortByDate(SearchBar.listOfWords);
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
     * Método que es llamado para ordenar los resultados por su nombre
     * @param list lista de ordenar
     */
    private void SortByName(LinkedArrayList<Results> list){
        quickSort(list, 0, list.getSize()-1);
        SearchBar.updatePositions();

    }


    /**
     * Método que implementa el algoritmo de ordenamiento QuickSort
     * @param list lista a ordenar
     * @param start indice de inicio
     * @param end indice final
     */

    private void quickSort(LinkedArrayList<Results> list, int start, int end){
        if (start < end) {
            int pos = quickSortAux(list, start, end);

            quickSort(list, start, pos - 1);
            quickSort(list,pos +1, end);

        }
    }


    /**
     * Método auxiliar del quicksort
     * @param list lista a ordenar
     * @param start indice de inicio
     * @param end indice de final
     * @return nuevo indice
     */


    private int quickSortAux(LinkedArrayList<Results> list, int start, int end) {

        File pivote = list.getElement(end).file;
        int i = start-1;
        for(int j=start; j<end; j++){
            File file = list.getElement(j).file;
            if(file.getName().compareTo(pivote.getName()) < 0){
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
        for(int i=0; i < list.getSize(); i++) {
            for (int current = 0; current < list.getSize() - 1; current++) {
                try {
                    FileTime fileTime = getFileTime(list.getElement(i).file);
                    FileTime file1Time2 = getFileTime(list.getElement(i+1).file);
                    if(fileTime.compareTo(file1Time2) > 0){
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


}