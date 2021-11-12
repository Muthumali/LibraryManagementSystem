package controller;

import com.jfoenix.controls.JFXTextField;
import controller.manage.PaymentController;
import controller.manage.ReturnBookController;
import controller.manage.SalaryController;
import controller.manage.SupplierController;
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
import model.Attendence;
import model.Employee;
import model.PaymentDetail;
import model.Supplier;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.PaymentDetailTm;
import view.tm.SupplierTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ManageSalaryFormController {

    public Label lblPayNumber;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtWorkingHours;
    public JFXTextField txtEmpName;
    public JFXTextField txtPost;
    public Label lblTotalSalary;
    public JFXTextField txtEmpId;
    public TableView<PaymentDetailTm> tblPayment;
    public TableColumn colId;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colPost;
    public TableColumn colWorkingHours;
    public TableColumn colWorkingDates;
    public TableColumn colTotalSalary;
    public TableColumn colDate;
    public Button btnSalaryShhet;

    ObservableList<PaymentDetailTm> tableData = FXCollections.observableArrayList();


    public  void initialize(){
        lastPayId();
        loadDateAndTime();
        loadTableData();


        colId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colPost.setCellValueFactory(new PropertyValueFactory<>("post"));
        colWorkingHours.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        colTotalSalary.setCellValueFactory(new PropertyValueFactory<>("totalSal"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
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

    public void lastPayId() {
        try {
            String rbookId =PaymentController.getLastPayId();
            String finalId = "P-001";

            if (rbookId != null) {

                String[] splitString = rbookId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "P-00" + id;
                } else if (id < 100) {
                    finalId = "P-0" + id;
                } else {
                    finalId = "P-" + id;
                }
                lblPayNumber.setText(finalId);
            } else {
                lblPayNumber.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void PayOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        PaymentDetail p=new PaymentDetail(
                lblPayNumber.getText(),
                txtEmpId.getText(),
                txtEmpName.getText(),
                txtPost.getText(),
                Integer.parseInt(txtWorkingHours.getText()),
                Double.parseDouble(lblTotalSalary.getText()),
                lblDate.getText()
                );


        if(new PaymentController().savePayment(p)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();

           lastPayId();
           loadTableData();

           txtEmpName.clear();
           txtWorkingHours.clear();
           txtPost.clear();
           txtEmpId.clear();
           lblTotalSalary.setText("0");
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void setDataOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Id = txtEmpId.getText();

        Attendence b1= new PaymentController().getAttendenceDetail(Id);
        if (b1==null ) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();

        } else {
            setData(b1);
        }
    }

    void setData(Attendence b) throws SQLException, ClassNotFoundException {
        txtEmpName.setText(b.getEmpName());
        txtPost.setText(b.getPost());
        calcHours();
    }

    public void calcHours() throws SQLException, ClassNotFoundException {

        List<String> hours = new PaymentController().getWorkingHours(txtEmpId.getText());

        int hoursCount=0;
        for (String h : hours) {
            hoursCount=hoursCount+Integer.parseInt(h);
        }
        txtWorkingHours.setText(String.valueOf(hoursCount));
    }

    public void calcSalary(){
        int hours=Integer.parseInt(txtWorkingHours.getText());
        String post =txtPost.getText();

        if(post.equalsIgnoreCase("Assistant")){
            Double salHour=250.00;
            Double totSal=salHour*hours;
            lblTotalSalary.setText(String.valueOf(totSal));
        }else if(post.equalsIgnoreCase("Cleaner")){
            Double salHour=150.00;
            Double totSal=salHour*hours;
            lblTotalSalary.setText(String.valueOf(totSal));
        }else if(post.equalsIgnoreCase("Librarian")){
            Double salHour=450.00;
            Double totSal=salHour*hours;
            lblTotalSalary.setText(String.valueOf(totSal));
        }else{
            Double salHour=200.00;
            Double totSal=salHour*hours;
            lblTotalSalary.setText(String.valueOf(totSal));
        }

    }

    public void CalculateSalaryOnAction(ActionEvent actionEvent) {
        calcSalary();
    }

    public void loadTableData() {
        try {

            List<PaymentDetail> allPayment = PaymentController.getAllPayment();
            ArrayList<PaymentDetailTm> paymentDetailTms=new ArrayList<>();
            for (PaymentDetail b : allPayment) {
                paymentDetailTms.add(new PaymentDetailTm(
                        b.getPaymentId(),
                        b.getEmpId(),
                        b.getEmpName(),
                        b.getPost(),
                        b.getWorkingHours(),
                        b.getTotalSal(),
                        b.getPaymentDate()
                ));
            }
            tableData.clear();
            tableData.addAll(paymentDetailTms);
            tblPayment.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void PrintSalShhetONAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/SalaryReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);

            /*Setting values for parameters*/
            /*Get the customer id input field value*/
            String employeeID = txtEmpId.getText();

            /*Setting parameter values for the report*/
            HashMap map = new HashMap();
            map.put("SearchID", employeeID);

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

            /*JasperPrintManager.printReport(jasperPrint,false);*/

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
