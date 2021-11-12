package controller;

import controller.manage.ReportController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminHomeFormController {
    public AnchorPane LoadContext;
    public TextField txtDate;
    public TextField txtTime;
    public AnchorPane AdminContext;
    public Label lblAvailbleBook;
    public Label lblIssueBook;
    public Label lblReturnBooks;
    public Label lblCurrentMember;
    public Label lblCurrentEmployee;
    public Label lblLostBook;
    public Label lblReturnedFines;
    public Label lblLOstFines;


    public void initialize() {
        //----------------------Set Date & time--------------------------------------------------------------
        try {
            new Timer(1000, e -> {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss aa");
                String time = simpleDateFormat.format(new Date());
                String date = new SimpleDateFormat("MMM dd, YYYY", Locale.ENGLISH).format(new Date());
                txtDate.setText(date);
                txtTime.setText(time);
            }).start();
        } catch (Exception exception) {
        }

        SetData();
    }

    public void ManageBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageBooksForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void ManageEmployeeOnActions(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageEmployeeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void ManageSalaryOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageSalaryForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void GoBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdminContext.getChildren().clear();
        AdminContext.getChildren().add(load);
    }

    public void GoOrderNewBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderNewBookForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void ManageSupplierOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageSupplierForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void ViewMemberDetailOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MemberDetailForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void ViewIssueBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ViewIssueBookForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void EmployeeAttendenceOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/EmployeeAttendenceForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadContext.getChildren().clear();
        LoadContext.getChildren().add(load);
    }

    public void SetData(){
        try {
            ReportController.getAvailbleCount(lblAvailbleBook);
            ReportController.getLostCount(lblLostBook);
            ReportController.getIssueCount(lblIssueBook);
            ReportController.getReturnCount(lblReturnBooks);
            ReportController.getMemberCount(lblCurrentMember);
            ReportController.getEmployeeCount(lblCurrentEmployee);
            ReportController.getLateFinesTot(lblReturnedFines);
            ReportController.getLostBooksFinesTot(lblLOstFines);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
