package FileReader;

import GUI.SearchBar;
import LinkedArrayList.LinkedArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.apache.poi.hwpf.sprm.SprmIterator;

import java.io.File;
import java.nio.file.Files;


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
                        SearchBar.addDocumentsToScreen();
                        System.out.println("Ordenar por nombre " + SearchBar.listOfWords.getSize());


                    }
                    if (sortBySize.isSelected()) {
                        System.out.println("Ordenar por tamano " + SearchBar.listOfWords.getSize());
                    }
                    if (sortByDate.isSelected()) {
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
        quickSort(list, 0, list.getSize()-1);

    }


    private void quickSort(LinkedArrayList<File> list, int start, int end){
        if(start < end){
            int newpos = quickSortAux(list, start, end);

            quickSort(list, start, newpos +1);
            quickSort(list, newpos +1, end);
        }

    }

    private int quickSortAux(LinkedArrayList<File> list, int start, int end){
        File filePivot = list.getElement(end);
        int i = (start-1);

        for(int j= start; j<end; j++){
            File file = list.getElement(j);
            if(file.getName().compareTo(filePivot.getName()) <=0){
                i++;
            }

            File fileSwap = list.getElement(i);
            list.add(list.getElement(j), i);
            list.add(fileSwap, j);
        }

        File fileSwap = list.getElement(i+1);
        list.add(list.getElement(end), i+1);
        list.add(fileSwap, end);

        return i+1;

    }


}

