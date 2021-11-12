package controller;

import controller.manage.BookController;
import controller.manage.IsssueBookController;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.IssueBook;
import model.Member;
import view.tm.IssueBookTm;
import view.tm.MemberTm;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class IssueBookFormController {
    public TextField txtBookId;
    public TextField txtMemberId;
    public TextField txtAssistantId;
    public Label lblDate;
    public Label lblTime;
    public Label lblId;
    public TableView <IssueBookTm>tblIssueBook;
    public TableColumn colIssueId;
    public TableColumn colDate;
    public TableColumn colEndDate;
    public TableColumn colBookId;
    public TableColumn colMemberId;
    public TableColumn colAssistantId;
    public DatePicker DatePickerEnd;

    ObservableList<IssueBookTm> tableData = FXCollections.observableArrayList();


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern bookIdPattern = Pattern.compile("^[B][-][0-9]{3,}$");
    Pattern memberIdPattern = Pattern.compile("^[M][-][0-9]{3,}$");
    Pattern empIdPattern = Pattern.compile("^[E][-][0-9]{3,}$");

    private void storeValidations() {
        map.put(txtBookId,bookIdPattern);
        map.put(txtMemberId,memberIdPattern);
        map.put(txtAssistantId,empIdPattern);

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

    public void initialize(){
        loadDateAndTime();
        lastIssueBookId();
        storeValidations();
        loadTableData();

        colIssueId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colAssistantId.setCellValueFactory(new PropertyValueFactory<>("empId"));

        tblIssueBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setModifyToData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

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

    public void ModifyIssueBookOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        LocalDate date=DatePickerEnd.getValue();
        IssueBook i1=new IssueBook(
                lblId.getText(),lblDate.getText(),date.toString(),txtBookId.getText(),txtMemberId.getText(),txtAssistantId.getText()
        );
        if (new IsssueBookController().ModifyData(i1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    void setData(IssueBook b){
        lblId.setText(b.getIssueId());
        //DatePickerEnd.setValue(b.getEndDate());
        txtBookId.setText(b.getBookId());
        txtMemberId.setText(b.getMemberId());
        txtAssistantId.setText(b.getEmpId());
    }

    public void setModifyToData(IssueBookTm m) throws SQLException, ClassNotFoundException {
        String Id=tblIssueBook.getSelectionModel().getSelectedItem().getIssueId();

        IssueBook b1= new IsssueBookController().getIssueBooks(Id);
        if (b1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(b1);
        }
    }

    public void AddIssueBookOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        LocalDate date=DatePickerEnd.getValue();

        IssueBook i=new IssueBook(
                lblId.getText(),lblDate.getText(),date.toString(),txtBookId.getText(),txtMemberId.getText(),txtAssistantId.getText()
        );

        boolean isDeleted = IsssueBookController.deleteBook(txtBookId.getText());
        if(new IsssueBookController().saveIssueBook(i) && isDeleted){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastIssueBookId();
            loadTableData();
            txtBookId.clear();
            txtMemberId.clear();
            txtAssistantId.clear();

        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

    }

    public void lastIssueBookId() {
        try {
            String IssueId = IsssueBookController.getLastIssueBookId();
            String finalId = "I-001";

            if (IssueId != null) {

                String[] splitString = IssueId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "I-00" + id;
                } else if (id < 100) {
                    finalId = "I-0" + id;
                } else {
                    finalId = "I-" + id;
                }
                lblId.setText(finalId);
            } else {
                lblId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void loadTableData() {
        try {

            List<IssueBook> allIssueBooks = IsssueBookController.getAllIssueBooks();
            ArrayList<IssueBookTm> issueBooks=new ArrayList<>();
            for (IssueBook b : allIssueBooks) {
                issueBooks.add(new IssueBookTm(
                        b.getIssueId(),
                       b.getIssueDate(),
                        b.getEndDate(),
                        b.getBookId(),
                        b.getMemberId(),
                        b.getEmpId()

                ));
            }

            tableData.clear();
            tableData.addAll(issueBooks);
            tblIssueBook.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void RemoveIsssueBookOnAction(ActionEvent actionEvent) {
        try {
            String id = tblIssueBook.getSelectionModel().getSelectedItem().getIssueId();
            boolean isDeleted = IsssueBookController.deleteData(id);
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


    public void ValidationOn(KeyEvent keyEvent) {
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
}
