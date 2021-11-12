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

public class AssistantHomeFormController {
    public AnchorPane AssistantContext;
    public AnchorPane LoadOneContext;
    public TextField txtDate;
    public TextField txtTime;
    public Label lblAvailbleBook;
    public Label lblIssueBook;
    public Label lblReturnBooks;
    public Label lblCurrentMember;
    public Label lblCurrentEmployee;
    public Label lblReturnedFines;
    public Label lblLOstFines;
    public Label lblLostBook;

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

    public void GoBackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AssistantContext.getChildren().clear();
        AssistantContext.getChildren().add(load);
    }

    public void GoManageMemberOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MemberManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadOneContext.getChildren().clear();
        LoadOneContext.getChildren().add(load);
    }


    public void GoManageIssueOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/IssueBookForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadOneContext.getChildren().clear();
        LoadOneContext.getChildren().add(load);
    }

    public void GoReturnBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ReturnBookForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadOneContext.getChildren().clear();
        LoadOneContext.getChildren().add(load);

    }

    public void GoAvailableBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AvailableBookForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadOneContext.getChildren().clear();
        LoadOneContext.getChildren().add(load);
    }


    public void LostBookOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MissingBooksForm.fxml");
        Parent load = FXMLLoader.load(resource);
        LoadOneContext.getChildren().clear();
        LoadOneContext.getChildren().add(load);
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
