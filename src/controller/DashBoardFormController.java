package controller;

import controller.manage.LoginController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBoardFormController {
    public AnchorPane DashBoardContext;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label lblDate;
    public Label lblTime;


    public void initialize(){
        loadDateAndTime();
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



    public void LoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String password=txtPassword.getText();
        String userName=txtUserName.getText();

        Boolean b=new LoginController().getLogin(password,userName);
        if(b==true){
           if (txtUserName.getText().equalsIgnoreCase("Admin")) {
            URL resource = getClass().getResource("../view/AdminHomeForm.fxml");
            Parent load = FXMLLoader.load(resource);
            DashBoardContext.getChildren().clear();
            DashBoardContext.getChildren().add(load);
        }else if(txtUserName.getText().equalsIgnoreCase("Assistant")){
               URL resource = getClass().getResource("../view/AssistantHomeForm.fxml");
               Parent load = FXMLLoader.load(resource);
               DashBoardContext.getChildren().clear();
               DashBoardContext.getChildren().add(load);
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Invalid UserName & Password...").show();
        }

    }

    public void GoPasswordOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
