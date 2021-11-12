package controller;

import controller.manage.SupplierController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Supplier;
import view.tm.SupplierTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ManageSupplierFormController {
    public AnchorPane ManageSupplierContext;
    public TextField txtSupId;
    public TextField txtAddress;
    public TextField txtName;
    public TextField txtContact;
    public TableView <SupplierTm>tblSupplier;
    public TableColumn colSupId;
    public TableColumn colSupName;
    public TableColumn colAddress;
    public TableColumn colContact;

    ObservableList<SupplierTm> tableData = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-Z  a-z -9 ]{3,}");
    Pattern contactPattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");

    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact, contactPattern);
    }

    public void initialize() {
        lastSupplierId();
        loadTableData();
        storeValidations();

        colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                SetDataModify(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public void ModifySupplierOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Supplier s1=new Supplier(
                txtSupId.getText(),txtName.getText(),
                txtAddress.getText(),txtContact.getText()
        );
        if (new SupplierController().ModifySupplier(s1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }
    void setData(Supplier b){
        txtSupId.setText(b.getSupplierId());
        txtName.setText(b.getSupplierName());
        txtAddress.setText(b.getAddress());
        txtContact.setText(b.getContact());

    }

    public void SetDataModify(SupplierTm s) throws SQLException, ClassNotFoundException {
        String Id=tblSupplier.getSelectionModel().getSelectedItem().getSupplierId();

        Supplier b1= new SupplierController().getSupplier(Id);
        if (b1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
            loadTableData();
        } else {
            setData(b1);
        }
    }

    public void RemoveSupplierOnAction(ActionEvent actionEvent) {
        try {
            String id = tblSupplier.getSelectionModel().getSelectedItem().getSupplierId();
            boolean isDeleted = SupplierController.deleteSupplier(id);
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

    public void AddSupplierOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Supplier s1=new Supplier(
                txtSupId.getText(),txtName.getText(),txtAddress.getText(),txtContact.getText()
        );
        if(new SupplierController().saveSupplier(s1)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();

            lastSupplierId();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void loadTableData() {
        try {

            List<Supplier> allSupplier = SupplierController.getAllSupplier();
            ArrayList<SupplierTm> suppliers=new ArrayList<>();
            for (Supplier b : allSupplier) {
                suppliers.add(new SupplierTm(
                        b.getSupplierId(),
                        b.getSupplierName(),
                        b.getAddress(),
                        b.getContact()
                ));
            }
            tableData.clear();
            tableData.addAll(suppliers);
            tblSupplier.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void lastSupplierId() {
        try {
            String supId = SupplierController.getSupId();
            String finalId = "S-001";

            if (supId != null) {

                String[] splitString = supId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "S-00" + id;
                } else if (id < 100) {
                    finalId = "S-0" + id;
                } else {
                    finalId = "S-" + id;
                }
                txtSupId.setText(finalId);
            } else {
                txtSupId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public void OneOnAction(KeyEvent keyEvent) {
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

    public void TwoOnAction(KeyEvent keyEvent) {
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
