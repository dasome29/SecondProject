package FileReader;

import GUI.Results;
import GUI.SearchBar;
import LinkedArrayList.LinkedArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.apache.poi.hssf.record.formula.functions.Int;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;


public class SortDocumentsBar {
    private Pane root;
    private Pane sortPane;
    private ToggleGroup toggleGroup;
    private RadioButton sortByName;
    private RadioButton sortBySize;
    private RadioButton sortByDate;




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

    private void setEventToggleGroup(){
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                try {
                    if (sortByName.isSelected()) {
                        System.out.println("Ordenar por nombre " + SearchBar.listOfWords.getSize());
                        SortByName(SearchBar.listOfWords);
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


    private void SortByName(LinkedArrayList<File> list){
    }



    private LinkedArrayList<File> quickSort(LinkedArrayList<File> list, int start, int end){
        return quickSortAux(list, start, end);

    }

    private LinkedArrayList<File> quickSortAux(LinkedArrayList<File> list, int start, int end) {
        return null;
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

    private FileTime getFileTime(File file) throws IOException {
        BasicFileAttributes attributes;
        attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        FileTime file1Time = attributes.creationTime();
        return file1Time;
    }

}
