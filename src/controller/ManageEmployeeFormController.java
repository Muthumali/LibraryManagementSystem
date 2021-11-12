package controller;
import controller.manage.EmployeeController;
import db.DbConnection;
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
import model.Employee;
import model.Login;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageEmployeeFormController {
    public AnchorPane ManageEmployeeContext;
    public TextField txtEmpId;
    public TextField txtAddress;
    public TextField txtPost;
    public TextField txtName;
    public TextField txtNIC;
    public TextField txtContact;
    public TextField txtQuli;
    public TextField txtBirthday;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colAddress;
    public TableColumn colNic;
    public TableColumn colBirthday;
    public TableColumn colContact;
    public TableColumn colPost;
    public TableColumn colQuli;
    public TableView <EmployeeTm>tblEmployee;
    public Button btnSaveOnAction;
    public TextField txtPassword;
    public Button BtnReport;

    ObservableList<EmployeeTm> tableData = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();

    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-Z  a-z -9 ]{3,}$");
    Pattern NICPattern = Pattern.compile("^[0-9]{9,}[V]$");
    Pattern birthdayPattern = Pattern.compile("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$");
    Pattern contactPattern = Pattern.compile("^[0][7][1,2,4,5,6,7,8]{1}[0-9]{7}$");
    Pattern postPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern qulificationPattern = Pattern.compile("^[A-Z][/][L]$");
    Pattern PasswordPattern = Pattern.compile("^[A-Z]{1,6}[0-9]{2,6}$");


    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtNIC,NICPattern);
        map.put(txtBirthday,birthdayPattern);
        map.put(txtContact, contactPattern);
        map.put(txtPost,postPattern);
        map.put(txtQuli,qulificationPattern);
        map.put(txtPassword,PasswordPattern);
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
        lastEmpId();
        loadTableData();
        storeValidations();

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPost.setCellValueFactory(new PropertyValueFactory<>("post"));
        colQuli.setCellValueFactory(new PropertyValueFactory<>("qualification"));

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                getModify(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void lastEmpId() {
        try {
            String empId = EmployeeController.getLastEmpId();
            String finalId = "E-001";

            if (empId != null) {

                String[] splitString = empId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "E-00" + id;
                } else if (id < 100) {
                    finalId = "E-0" + id;
                } else {
                    finalId = "E-" + id;
                }
                txtEmpId.setText(finalId);
            } else {
                txtEmpId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ModifyEmployeeOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Employee b1 = new Employee(
                txtEmpId.getText(), txtName.getText(),
                txtAddress.getText(), txtNIC.getText(), txtBirthday.getText(), txtContact.getText(), txtPost.getText(), txtQuli.getText()
        );
        if (new EmployeeController().ModifyEmployee(b1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }
    void setData(Employee b){
        txtEmpId.setText(b.getEmpId());
        txtName.setText(b.getEmpName());
        txtAddress.setText(b.getAddress());
        txtNIC.setText(b.getNIC());
        txtBirthday.setText(b.getBirthDay());
        txtContact.setText(b.getContact());
        txtPost.setText(b.getPost());
        txtQuli.setText(b.getQualification());
    }

    public void getModify(EmployeeTm e) throws SQLException, ClassNotFoundException {
        String Id=tblEmployee.getSelectionModel().getSelectedItem().getEmpId();

        Employee b1= new EmployeeController().getEmployee(Id);
        if (b1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(b1);
        }
    }


    public void AddEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Employee e1=new Employee(
                txtEmpId.getText(),txtName.getText(),txtAddress.getText(),txtNIC.getText(),txtBirthday.getText(),txtContact.getText(),txtPost.getText(),
                txtQuli.getText()
        );


        Login l=new Login(
                txtPassword.getText(),
                txtPost.getText(),
                txtEmpId.getText()
        );

        if(new EmployeeController().saveEmployee(e1,l)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastEmpId();
            loadTableData();
            txtName.clear();
            txtAddress.clear();
            txtNIC.clear();
            txtBirthday.clear();
            txtContact.clear();
            txtPost.clear();
            txtQuli.clear();
            txtPassword.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void loadTableData() {
        try {
            List<Employee> allEmployee = EmployeeController.getAllEmployees();
            ArrayList<EmployeeTm> employees=new ArrayList<>();
            for (Employee b : allEmployee) {
                employees.add(new EmployeeTm(
                        b.getEmpId(),
                        b.getEmpName(),
                        b.getAddress(),
                        b.getNIC(),
                        b.getBirthDay(),
                        b.getContact(),
                        b.getPost(),
                        b.getQualification()
                ));
            }

            tableData.clear();
            tableData.addAll(employees);
            tblEmployee.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void RemoveEmployeeOnAction(ActionEvent actionEvent) {
        try {
            String id = tblEmployee.getSelectionModel().getSelectedItem().getEmpId();
            boolean isDeleted = EmployeeController.deleteEmployee(id);
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

    public void OneAction(KeyEvent keyEvent) {
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

    public void TwoAction(KeyEvent keyEvent) {
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

    public void EmployeeReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/EmployeeReport.jrxml"));
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
