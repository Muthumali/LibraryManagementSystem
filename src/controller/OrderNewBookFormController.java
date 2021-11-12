package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.manage.OrderController;
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
import model.Order;
import model.OrderDetail;
import model.Supplier;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.OrderDetailTm;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderNewBookFormController {
    public JFXTextField txtBookName;
    public JFXTextField txtQty;
    public TableView <OrderDetailTm> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colSupplierName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colQty;
    public TableColumn colOrderDate;
    public JFXComboBox <String>cmbSupName;
    public JFXTextField txtSupId;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public TableColumn colSupId;
    public TableColumn colBookName;
    public Button btnOrderBookReport;

    ObservableList<OrderDetailTm> tableData = FXCollections.observableArrayList();


    public void lastOrderId() {
        try {
            String orderId = OrderController.getOrderId();
            String finalId = "OI-001";

            if (orderId != null) {

                String[] splitString = orderId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "OI-00" + id;
                } else if (id < 100) {
                    finalId = "OI-0" + id;
                } else {
                    finalId = "OI-" + id;
                }
                lblOrderId.setText(finalId);
            } else {
                lblOrderId.setText(finalId);
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


    public void initialize() throws SQLException, ClassNotFoundException {
        loadSupplierName();
        lastOrderId();
        loadDateAndTime();
        loadTableData();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));



        cmbSupName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setSupplierData( newValue);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
    }


    private void setSupplierData(String supName) throws SQLException, ClassNotFoundException {
        Supplier s= new SupplierController().getSuppliersDetails(supName);
        if (s == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtSupId.setText(s.getSupplierId());
           txtAddress.setText(s.getAddress());
           txtContact.setText(s.getContact());
        }
    }

    private void loadSupplierName() throws SQLException, ClassNotFoundException {
        List<String> supName = new OrderController().getSupplierName();
        cmbSupName.getItems().addAll(supName);
    }



    public void AddOrderBookListOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Order o1=new Order(
                lblOrderId.getText(),txtBookName.getText(),txtQty.getText(),lblDate.getText()
        );

        OrderDetail o2=new OrderDetail(
                lblOrderId.getText(),txtSupId.getText(),cmbSupName.getSelectionModel().getSelectedItem(),txtAddress.getText()
                ,txtContact.getText(),txtBookName.getText(),txtQty.getText(),lblDate.getText()
        );

        if(new OrderController().saveOrder(o1,o2)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastOrderId();
            loadTableData();
            cmbSupName.getItems().clear();
            txtSupId.clear();
            txtAddress.clear();
            txtContact.clear();
            txtBookName.clear();
            txtQty.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void loadTableData() {
        try {

            List<OrderDetail> allOrders = OrderController.getAllOrders();
            ArrayList<OrderDetailTm> o=new ArrayList<>();
            for (OrderDetail b : allOrders) {
                o.add(new OrderDetailTm(
                        b.getOrderId(),
                        b.getSupplierId(),
                        b.getSupplierName(),
                        b.getAddress(),
                        b.getContact(),
                        b.getBookName(),
                        b.getQty(),
                        b.getOrderDate()

                ));
            }

            tableData.clear();
            tableData.addAll(o);
            tblOrder.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void RemoveOrderListOnAction(ActionEvent actionEvent) {
        try {
            String id = tblOrder.getSelectionModel().getSelectedItem().getOrderId();
            boolean isDeleted = OrderController.deleteList(id);
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

    public void OrderBookReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/OrderBookReport.jrxml"));
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
