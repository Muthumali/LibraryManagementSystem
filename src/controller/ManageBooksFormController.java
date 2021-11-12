package controller;

import controller.manage.BookController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AvailableBook;
import model.Book;
import view.tm.BookTm;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class ManageBooksFormController {
    public AnchorPane ManageBookContext;


    public TextField txtLan;
    public TextField txtBookPrice;
    public TableView<BookTm> tblBook;
    public TableColumn colBookID;
    public TableColumn colBookName;
    public TableColumn colWritterName;
    public TableColumn colLanauage;
    public TableColumn colCountOfBook;
    public TableColumn colbookPrice;
    public TableColumn colType;
    public TableColumn colAddDate;
    public TextField txtType;
    public Button btnSaveBook;
    public TextField txtName;
    public TextField txtWirtterName;
    public Label lblBookId;
    public Label lblDate;
    public Label lblTime;

    ObservableList<BookTm> tableData = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern writeNamePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern lanPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern typePattern = Pattern.compile("^[A-z ]{3,20}$");


    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtWirtterName, writeNamePattern);
        map.put(txtLan, lanPattern);
        map.put(txtBookPrice, pricePattern);
        map.put(txtType, typePattern);

    }

    public void initialize(){
        loadDateAndTime();
        lastOBookId();
      //btnSaveBook.setDisable(true);
        storeValidations();
        loadTableData();

        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colWritterName.setCellValueFactory(new PropertyValueFactory<>("writterName"));
        colLanauage.setCellValueFactory(new PropertyValueFactory<>("bookLanguage"));
        colbookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAddDate.setCellValueFactory(new PropertyValueFactory<>("addDate"));

        tblBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setModifyToData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private Object validate() {
        //btnSaveBook.setDisable(true);

        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()){
                    textFieldKey.getParent().setStyle("-fx-border-color: red");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: green");
        }
       // btnSaveBook.setDisable(false);
        return true;
    }

    public void ModifyBookOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Book b1=new Book(
                lblBookId.getText(),txtName.getText(),
                txtWirtterName.getText(),txtLan.getText(),Double.parseDouble(txtBookPrice.getText()),txtType.getText(),lblDate.getText()
        );
        if (new BookController().ModifyBook(b1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    void setData(Book b){
        lblBookId.setText(b.getBookId());
        txtName.setText(b.getBookName());
        txtWirtterName.setText(b.getWritterName());
        txtLan.setText(b.getBookLanguage());
        txtBookPrice.setText(String.valueOf(b.getPrice()));
        txtType.setText(b.getType());
    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void AddBookOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        Book B=new Book(
                lblBookId.getText(),txtName.getText(),
                txtWirtterName.getText(),txtLan.getText()
                ,Double.parseDouble(txtBookPrice.getText()),txtType.getText(),lblDate.getText()
        );

        AvailableBook a=new AvailableBook(
                lblBookId.getText(),txtName.getText(),
                txtWirtterName.getText(),txtLan.getText()
                ,Double.parseDouble(txtBookPrice.getText()),txtType.getText(),lblDate.getText()
        );

        if(new BookController().saveBook(B)  && new BookController().saveABook(a)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastOBookId();
            loadTableData();
            txtName.clear();
            txtWirtterName.clear();
            txtBookPrice.clear();
            txtType.clear();
            txtLan.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

    }

    public void onActionTwo(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }

    public void setModifyToData(BookTm b) throws SQLException, ClassNotFoundException {

        String Id=tblBook.getSelectionModel().getSelectedItem().getBookId();

        Book b1= new BookController().getBooks(Id);
        if (b1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(b1);
        }
    }

    public void OnActionOne(KeyEvent keyEvent) {
        Object response = validate();

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
    public void lastOBookId() {
        try {
            String bookId = BookController.getLasBookId();
            String finalId = "B-001";

            if (bookId != null) {

                String[] splitString = bookId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "B-00" + id;
                } else if (id < 100) {
                    finalId = "B-0" + id;
                } else {
                    finalId = "B-" + id;
                }
                lblBookId.setText(finalId);
            } else {
                lblBookId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void loadTableData() {
        try {

            List<Book> allBooks = BookController.getAllBooks();
            ArrayList<BookTm> books=new ArrayList<>();
            for (Book b : allBooks) {
                books.add(new BookTm(
                        b.getBookId(),
                        b.getBookName(),
                        b.getWritterName(),
                        b.getBookLanguage(),
                        b.getPrice(),
                        b.getType(),
                        b.getAddDate()
                ));
            }

            tableData.clear();
            tableData.addAll(books);
            tblBook.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void RemoveBookOnAction(ActionEvent actionEvent) {
        try {
            String id = tblBook.getSelectionModel().getSelectedItem().getBookId();
            boolean isDeleted = BookController.deleteBook(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Success").show();
                loadTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
}
