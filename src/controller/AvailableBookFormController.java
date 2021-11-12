package controller;

import com.jfoenix.controls.JFXTextField;
import controller.manage.AvailableBookController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AvailableBook;
import view.tm.AvailableBookTm;


import java.sql.SQLException;
import java.util.List;

public class AvailableBookFormController {

    public JFXTextField txtSearch;
    public TableView<AvailableBookTm> tblABook;
    public TableColumn colBook;
    public TableColumn colName;
    public TableColumn colWName;
    public TableColumn collan;
    public TableColumn colcountBook;
    public TableColumn colPrice;
    public TableColumn colType;
    public TableColumn colDate;


    public void initialize(){


        colBook.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colWName.setCellValueFactory(new PropertyValueFactory<>("writterName"));
        collan.setCellValueFactory(new PropertyValueFactory<>("bookLanguage"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("addDate"));

        tblABook.getItems().setAll(loadTableData());


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
    }

    public ObservableList<AvailableBookTm> loadTableData() {
        try {

            List<AvailableBook> allBooks = AvailableBookController.getAllBooks();
            ObservableList<AvailableBookTm> tableData = FXCollections.observableArrayList();
            for (AvailableBook b : allBooks) {
                tableData.add(new AvailableBookTm(
                        b.getBookId(),
                        b.getBookName(),
                        b.getWritterName(),
                        b.getBookLanguage(),
                        b.getPrice(),
                        b.getType(),
                        b.getAddDate()
                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
return  null;
    }

    public void txtSearch_OnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value){
        try {

            List<AvailableBook> allBooks = AvailableBookController.searchBook(value);
            ObservableList<AvailableBookTm> tableData = FXCollections.observableArrayList();
            for (AvailableBook b : allBooks) {
                tableData.add(new AvailableBookTm(
                        b.getBookId(),
                        b.getBookName(),
                        b.getWritterName(),
                        b.getBookLanguage(),
                        b.getPrice(),
                        b.getType(),
                        b.getAddDate()
                ));
            }

            tblABook.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
