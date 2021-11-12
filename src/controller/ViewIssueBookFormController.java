package controller;

import com.jfoenix.controls.JFXTextField;
import controller.manage.IsssueBookController;
import db.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.IssueBook;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.IssueBookTm;

import java.sql.SQLException;
import java.util.List;

public class ViewIssueBookFormController {

    public JFXTextField txtSearch;
    public TableColumn colMemberId;
    public TableView <IssueBookTm>tblIssue;
    public TableColumn colAssistantId;
    public TableColumn colIssuId;
    public TableColumn colIssueDate;
    public TableColumn colEndDate;
    public TableColumn colBookId;
    public Button btnReportIssueBook;

    public void initialize(){


        colIssuId.setCellValueFactory(new PropertyValueFactory<>("issueId"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colAssistantId.setCellValueFactory(new PropertyValueFactory<>("empId"));

        tblIssue.getItems().setAll(loadTableData());


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
    }

    public ObservableList<IssueBookTm> loadTableData() {
        try {

            List<IssueBook> allBooks = IsssueBookController.getAllIssueBooks();
            ObservableList<IssueBookTm> tableData = FXCollections.observableArrayList();
            for (IssueBook b : allBooks) {
                tableData.add(new IssueBookTm(
                        b.getIssueId(),
                        b.getIssueDate(),
                        b.getEndDate(),
                        b.getBookId(),
                        b.getMemberId(),
                        b.getEmpId()

                ));
            }

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value) {
        try {

            List<IssueBook> allBooks = IsssueBookController.searchIssueBook(value);
            ObservableList<IssueBookTm> tableData = FXCollections.observableArrayList();
            for (IssueBook b : allBooks) {
                tableData.add(new IssueBookTm(
                        b.getIssueId(),
                        b.getIssueDate(),
                        b.getEndDate(),
                        b.getBookId(),
                        b.getMemberId(),
                        b.getEmpId()
                        ));
            }

            tblIssue.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void PrintReportIssueBookOnAction(ActionEvent actionEvent) {

        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/IssueBookReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            /*Here we can set the DBConnection to the data source because this report use mysql to get data*/
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
