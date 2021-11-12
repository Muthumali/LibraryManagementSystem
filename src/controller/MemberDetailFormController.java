package controller;

import com.jfoenix.controls.JFXTextField;
import controller.manage.BookController;
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
import model.Member;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.MemberTm;

import java.sql.SQLException;
import java.util.List;

public class MemberDetailFormController {

    public JFXTextField txtSearch;
    public TableView <MemberTm>tblMember;
    public TableColumn colId;
    public TableColumn colAddress;
    public TableColumn colName;
    public TableColumn colNIC;
    public TableColumn colBirthday;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colDate;
    public TableColumn colFee;
    public Button btnMemberReport;


    public void initialize(){


        colId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("registerFee"));

        tblMember.getItems().setAll(loadTableData());


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                search(newValue);
            }
        });
    }

    public ObservableList<MemberTm> loadTableData() {
        try {

            List<Member> allMember = BookController.MemberController.getAllMember();
            ObservableList<MemberTm> tableData = FXCollections.observableArrayList();
            for (Member b : allMember) {
                tableData.add(new MemberTm(
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

            return tableData;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public void txtSerach(ActionEvent actionEvent) {
        search(txtSearch.getText());
    }

    private void search(String value){
        try {

            List<Member> allMember = BookController.MemberController.searchMember(value);
            ObservableList<MemberTm> tableData = FXCollections.observableArrayList();
            for (Member b : allMember) {
                tableData.add(new MemberTm(
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

            tblMember.setItems(tableData);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void MemberReportOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/Reports/MemberReport.jrxml"));
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


