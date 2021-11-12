package controller;

import com.jfoenix.controls.JFXTextField;
import controller.manage.SalaryController;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.AttendenceTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeAttendenceFormController {
    public JFXTextField txtEmpId;
    public JFXTextField txtEmpName;
    public JFXTextField txtAddress;
    public JFXTextField txtNic;
    public JFXTextField txtBirthday;
    public JFXTextField txtContact;
    public JFXTextField txtPost;
    public JFXTextField txtQuli;
    public Label lblDate;
    public Label lblTime;
    public ComboBox <String>cmbWorkingTypes;
    public Label lblWokingHours;
    public TableView<AttendenceTm> tblAttendence;
    public TableColumn colNumber;
    public TableColumn colEmpId;
    public TableColumn colDate;
    public TableColumn colHours;
    public TableColumn colWorkingType;
    public TableColumn colEmpName;
    public TableColumn colPost;
    public Label lblId;
    public Button btnReport;


    ObservableList<AttendenceTm> tableData = FXCollections.observableArrayList();
    public static ArrayList<String> WorkingTypes=new ArrayList<>();
    static {
        WorkingTypes.add("Full Day");
        WorkingTypes.add("Half Day");

    }

    public void initialize() throws SQLException, ClassNotFoundException {
         load();
         loadDateAndTime();
         lastAttendId();
         loadTableData();


        colNumber.setCellValueFactory(new PropertyValueFactory<>("attendenceNumber"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("attendDate"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colWorkingType.setCellValueFactory(new PropertyValueFactory<>("WorkingType"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colPost.setCellValueFactory(new PropertyValueFactory<>("post"));

        cmbWorkingTypes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setWorkingData( newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }


    public void lastAttendId() {
        try {
            String orderId = SalaryController.getAId();
            String finalId = "A-001";

            if (orderId != null) {

                String[] splitString = orderId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "A-00" + id;
                } else if (id < 100) {
                    finalId = "A-0" + id;
                } else {
                    finalId = "A-" + id;
                }
                lblId.setText(finalId);
            } else {
                lblId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


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

    private void setWorkingData(String type) throws SQLException, ClassNotFoundException {

        if (type.equalsIgnoreCase("Full Day")) {
           lblWokingHours.setText("06");
        } else {
            lblWokingHours.setText("03");
        }
    }

    private void load() throws SQLException, ClassNotFoundException {
        cmbWorkingTypes.getItems().addAll(WorkingTypes);
    }

    public void AddAttendenceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Attendence a=new Attendence(
               lblId.getText(),lblDate.getText(),Integer.parseInt(lblWokingHours.getText()),
                cmbWorkingTypes.getSelectionModel().getSelectedItem(),txtEmpId.getText(),
                txtEmpName.getText(),txtPost.getText()
        );

        if(new SalaryController().saveAttendence(a)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastAttendId();
            loadTableData();
            txtEmpId.clear();
            txtEmpName.clear();
            txtAddress.clear();
            txtNic.clear();
            txtBirthday.clear();
            txtContact.clear();
            txtPost.clear();
            txtQuli.clear();
            cmbWorkingTypes.getItems().clear();
            lblWokingHours.setText("0");
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }

    }

    public void SelectEmpData(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String Id = txtEmpId.getText();

        Employee b1= new SalaryController().getEmpolyee(Id);
        if (b1==null ) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(b1);
        }
    }

    void setData(Employee b) {
        txtEmpName.setText(b.getEmpName());
        txtAddress.setText(b.getAddress());
        txtNic.setText(b.getNIC());
        txtBirthday.setText(b.getBirthDay());
        txtContact.setText(b.getContact());
        txtPost.setText(b.getPost());
        txtQuli.setText(b.getQualification());
    }


    public void loadTableData() {
        try {

            List<Attendence> allAttendence = SalaryController.getAllAttendence();
            ArrayList<AttendenceTm> attendenceTms=new ArrayList<>();
            for (Attendence b : allAttendence) {
                attendenceTms.add(new AttendenceTm(
                        b.getAttendenceNumber(),
                        b.getAttendDate(),
                        b.getHours(),
                        b.getWorkingType(),
                        b.getEmpId(),
                        b.getEmpName(),
                        b.getPost()

                ));
            }

            tableData.clear();
            tableData.addAll(attendenceTms);
            tblAttendence.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void AttendenceReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/EmployeeAttendenceReport.jrxml"));
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
