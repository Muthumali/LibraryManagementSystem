package controller;

import com.jfoenix.controls.JFXTextField;
import controller.manage.BookController;
import controller.manage.IsssueBookController;
import controller.manage.MissingBookController;
import controller.manage.ReturnBookController;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Book;
import model.IssueBook;
import model.LostBook;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.BookTm;
import view.tm.LostBookTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MissingBooksFormController {
    public Label lblDate;
    public Label lblTime;
    public Label lblId;
    public JFXTextField txtBookId;
    public JFXTextField txtIssueId;
    public JFXTextField txtIssueDate;
    public JFXTextField txtEndDate;
    public JFXTextField txtMemberId;
    public JFXTextField txtEmpId;
    public JFXTextField txtBookName;
    public JFXTextField txtWritterName;
    public JFXTextField txtLan;
    public JFXTextField txtPrice;
    public JFXTextField txtBookType;
    public JFXTextField txtAddDAte;
    public Label lblTotalFines;
    public JFXTextField txtSearch;
    public TableView <LostBookTm>tbllostBooks;
    public TableColumn colIssueBookId;
    public TableColumn colMemberId;
    public TableColumn colTotalFines;
    public TableColumn colIssueDate;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colBookPrice;
    public TableColumn colPayDate;
    public Button btnPayReceipt;


    ObservableList<LostBookTm> tableData = FXCollections.observableArrayList();

    public void initialize(){
        loadDateAndTime();
        lastLostId();
        loadTableData();

        colIssueBookId.setCellValueFactory(new PropertyValueFactory<>("lostId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBookPrice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        colTotalFines.setCellValueFactory(new PropertyValueFactory<>("totalFines"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
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

    public void lastLostId() {
        try {
            String lostbookId = MissingBookController.getLastLostId();
            String finalId = "L-001";

            if (lostbookId != null) {

                String[] splitString = lostbookId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "L-00" + id;
                } else if (id < 100) {
                    finalId = "L-0" + id;
                } else {
                    finalId = "L-" + id;
                }
                lblId.setText(finalId);
            } else {
                lblId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddReturnBook(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LostBook l=new LostBook(
                lblId.getText(),txtMemberId.getText(),txtIssueDate.getText(),
                txtBookId.getText(),txtBookName.getText(),Double.parseDouble(txtPrice.getText()),Double.parseDouble(lblTotalFines.getText()),
                lblDate.getText()
        );

        boolean isDelete= IsssueBookController.deleteIssueData(txtBookId.getText());

        if(new MissingBookController().saveLostBook(l) && isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastLostId();
            loadTableData();
            /*txtBookId.clear();
            txtBookName.clear();
            txtIssueId.clear();
            txtIssueDate.clear();
            txtEndDate.clear();
            txtMemberId.clear();
            txtEmpId.clear();
            txtWritterName.clear();
            txtBookType.clear();
            txtAddDAte.clear();
            txtLan.clear();
            txtPrice.clear();
            lblTotalFines.setText("0");*/
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value){
        try {
            List<LostBook> allLostBooks = MissingBookController.searchLostBooks(value);
            ObservableList<LostBookTm> tableData = FXCollections.observableArrayList();
            for (LostBook b : allLostBooks) {
                tableData.add(new LostBookTm(
                        b.getLostId(),
                        b.getMemberId(),
                        b.getIssueDate(),
                        b.getBookId(),
                        b.getBookName(),
                        b.getBookPrice(),
                        b.getTotalFines(),
                        b.getPayDate()
                ));
            }
            tbllostBooks.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadTableData() {
        try {
            List<LostBook> allLostBooks =MissingBookController.getAllLostBook();
            ArrayList<LostBookTm> lostBookTms=new ArrayList<>();
            for (LostBook b : allLostBooks) {
               lostBookTms.add(new LostBookTm(
                        b.getLostId(),
                        b.getMemberId(),
                        b.getIssueDate(),
                        b.getBookId(),
                        b.getBookName(),
                        b.getBookPrice(),
                        b.getTotalFines(),
                        b.getPayDate()
                ));
            }
            tableData.clear();
            tableData.addAll(lostBookTms);
            tbllostBooks.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void SetAllDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Id = txtBookId.getText();

        IssueBook b1= new ReturnBookController().getIssueBooks(Id);
        Book b=new BookController().getBooks(Id);
        if (b1==null ) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(b1,b);
        }
    }


    void setData(IssueBook b, Book b1){
        txtIssueId.setText(b.getIssueId());
        txtIssueDate.setText(b.getIssueDate());
        txtEndDate.setText(b.getEndDate());
        txtBookId.setText(b.getBookId());
        txtMemberId.setText(b.getMemberId());
        txtEmpId.setText(b.getEmpId());
        txtBookName.setText(b1.getBookName());
        txtWritterName.setText(b1.getWritterName());
        txtLan.setText(b1.getBookLanguage());
        txtPrice.setText(String.valueOf(b1.getPrice()));
        txtBookType.setText(b1.getType());
        txtAddDAte.setText(b1.getAddDate());

    }

    public void CalculateLostBookFines(){
        Double lostPrice=Double.parseDouble(txtPrice.getText());
        Double totFine;
        Double vat=100.00;
        totFine=lostPrice+vat;
        lblTotalFines.setText(String.valueOf(totFine));
    }

    public void CalcFinesOnAction(ActionEvent actionEvent) {
        CalculateLostBookFines();
    }

    public void PayReceiptOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/LostBookFine.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            /*Setting values for parameters*/
            /*Get the customer id input field value*/
            String memID =txtMemberId.getText();

            /*Setting parameter values for the report*/
            HashMap map = new HashMap();
            map.put("SearchID", memID);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

            /*JasperPrintManager.printReport(jasperPrint,false);*/

            txtBookId.clear();
            txtBookName.clear();
            txtIssueId.clear();
            txtIssueDate.clear();
            txtEndDate.clear();
            txtMemberId.clear();
            txtEmpId.clear();
            txtWritterName.clear();
            txtBookType.clear();
            txtAddDAte.clear();
            txtLan.clear();
            txtPrice.clear();
            lblTotalFines.setText("0");

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
