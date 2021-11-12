package controller;

import controller.manage.BookController;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.Member;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.MemberTm;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class MemberManageFormController {
    public TextField txtAddress;
    public TextField txtBirthday;
    public TextField txtName;
    public TextField txtNIC;
    public TextField txtEmail;
    public TextField txtMemberId;
    public TextField txtContact;
    public TextField txtFee;
    public TextField txtDate;
    public TextField txtTime;
    public AnchorPane ManageMemberContext;
    public TableColumn colMemberId;
    public TableColumn colMemeberName;
    public TableColumn colAddress;
    public TableColumn colNIc;
    public TableColumn colBirthday;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colAddDate;
    public TableView <MemberTm>tblMember;
    public Label lblDate;
    public Label lblTime;
    public TableColumn colRegisterFee;
    public Button printReceipt;

    ObservableList<MemberTm> tableData = FXCollections.observableArrayList();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-Z  a-z -9 ]{3,}$");
    Pattern NICPattern = Pattern.compile("^[0-9]{9,}[V]$");
    Pattern birthdayPattern = Pattern.compile("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$");
    Pattern contactPattern = Pattern.compile("^[0][7][1,2,4,5,6,7,8]{1}[0-9]{7}$");
    Pattern gmailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private void storeValidations() {
        map.put(txtName, namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtNIC,NICPattern);
        map.put(txtBirthday,birthdayPattern);
        map.put(txtContact, contactPattern);
        map.put(txtEmail,gmailPattern);
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

    public void initialize() {
            lastMemberId();
            loadTableData();
            loadDateAndTime();
            storeValidations();

            colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
            colMemeberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colNIc.setCellValueFactory(new PropertyValueFactory<>("nic"));
            colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colAddDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
            colRegisterFee.setCellValueFactory(new PropertyValueFactory<>("registerFee"));


        tblMember.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setModifyToData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void ModifyMemberOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        Member m1=new Member(
                txtMemberId.getText(),txtName.getText(),
                txtAddress.getText(),txtNIC.getText(),txtBirthday.getText(),txtContact.getText(),txtEmail.getText(),lblDate.getText(),Double.parseDouble(txtFee.getText())
        );
        if (new BookController.MemberController().ModifyMember(m1)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    void setData(Member b){
        txtMemberId.setText(b.getMemberId());
        txtName.setText(b.getMemberName());
        txtAddress.setText(b.getAddress());
        txtNIC.setText(b.getNic());
        txtBirthday.setText(b.getBirthday());
        txtContact.setText(b.getContact());
        txtEmail.setText(b.getEmail());
        txtFee.setText(String.valueOf(b.getRegisterFee()));
    }

    public void setModifyToData(MemberTm m) throws SQLException, ClassNotFoundException {
        String Id=tblMember.getSelectionModel().getSelectedItem().getMemberId();

        Member b1= new BookController.MemberController().getMembers(Id);
        if (b1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(b1);
        }
    }

    public void lastMemberId() {
        try {
            String memberId = BookController.MemberController.getLastMemberId();
            String finalId = "M-001";

            if (memberId != null) {

                String[] splitString = memberId.split("-");
                int id = Integer.parseInt(splitString[1]);
                id++;

                if (id < 10) {
                    finalId = "M-00" + id;
                } else if (id < 100) {
                    finalId = "M-0" + id;
                } else {
                    finalId = "M-" + id;
                }
                txtMemberId.setText(finalId);
            } else {
                txtMemberId.setText(finalId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadTableData() {
        try {

            List<Member> allMember = BookController.MemberController.getAllMember();
            ArrayList<MemberTm> members=new ArrayList<>();
            for (Member b : allMember) {
                members.add(new MemberTm(
                        b.getMemberId(),
                        b.getMemberName(),
                        b.getAddress(),
                        b.getNic(),
                        b.getBirthday(),
                        b.getContact(),
                        b.getEmail(),
                        b.getRegisterDate(),
                        b.getRegisterFee()
                ));
            }
            tableData.clear();
            tableData.addAll(members);
            tblMember.setItems(tableData);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void AddMemberOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Member m1=new Member(
              txtMemberId.getText(),txtName.getText(),txtAddress.getText(),txtNIC.getText(),txtBirthday.getText(),txtContact.getText(),txtEmail.getText(),lblDate.getText(),Double.parseDouble(txtFee.getText())
        );
        if(new BookController.MemberController().saveMember(m1)){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            lastMemberId();
            loadTableData();
            txtName.clear();
            txtAddress.clear();
            txtBirthday.clear();
            txtContact.clear();
            txtEmail.clear();
            txtNIC.clear();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try again").show();
        }
    }

    public void RemoveMemberOnAction(ActionEvent actionEvent) {
        try {
            String id = tblMember.getSelectionModel().getSelectedItem().getMemberId();
            boolean isDeleted = BookController.MemberController.deleteMember(id);
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

    public void PrintReceiptOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/PayRegisterFee.jrxml"));
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

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
