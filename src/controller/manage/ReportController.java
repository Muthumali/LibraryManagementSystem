package controller.manage;

import db.DbConnection;
import javafx.scene.control.Label;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportController {
    public static void getAvailbleCount(Label lblAvailbleBook) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM AvailableBook");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lblAvailbleBook.setText(rst.getString(1));
        }
    }

    public static void getLostCount(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM LostBook");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }

    public static void getIssueCount(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM IssueBook");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }

    public static void getReturnCount(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM ReturnBook");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }


    public static void getMemberCount(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM Member");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }

    public static void getEmployeeCount(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT COUNT(*) FROM Employee");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }
    public static void getLateFinesTot(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(fines) FROM ReturnBook ");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }

    public static void getLostBooksFinesTot(Label lbl) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT SUM(totalFines) FROM LostBook ");
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {
            lbl.setText(rst.getString(1));
        }
    }
}
