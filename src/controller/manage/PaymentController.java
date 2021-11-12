package controller.manage;

import db.DbConnection;
import model.Attendence;
import model.Employee;
import model.PaymentDetail;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    public Attendence getAttendenceDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Attendence WHERE empId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Attendence(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        } else {
            return null;
        }
    }

    public ArrayList<String> getWorkingHours(String id) throws SQLException, ClassNotFoundException {
        ArrayList<String> hours = new ArrayList<>();
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT hours FROM Attendence WHERE  empId=? ");
        stm.setObject(1, id);
        ResultSet rst = stm.executeQuery();
        while (rst.next()) {

                hours.add(rst.getString(1));
        }
        return hours;
    }

    public static String getLastPayId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT paymentId FROM PaymentDetail ORDER BY paymentId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("paymentId");
        }
        return null;
    }


    public boolean savePayment(PaymentDetail b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO PaymentDetail VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getPaymentId());
        stm.setObject(2,b.getEmpId());
        stm.setObject(3,b.getEmpName());
        stm.setObject(4,b.getPost());
        stm.setObject(5,b.getWorkingHours());
        stm.setObject(6,b.getTotalSal());
        stm.setObject(7,b.getPaymentDate());
        return stm.executeUpdate()>0;
    }


    public static List<PaymentDetail> getAllPayment() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM PaymentDetail");
        ResultSet rs = pstm.executeQuery();

        List<PaymentDetail> payment = new ArrayList<>();

        while (rs.next()) {
            payment.add(new PaymentDetail(
                    rs.getString("paymentId"),
                    rs.getString("empId"),
                    rs.getString("empName"),
                    rs.getString("post"),
                    rs.getInt("workingHours"),
                    rs.getDouble("totalSal"),
                    rs.getString("paymentDate")
            ));
        }

        return payment;
    }
}
