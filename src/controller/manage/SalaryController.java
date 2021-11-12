package controller.manage;

import db.DbConnection;
import model.Attendence;
import model.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryController {


    public static String getAId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT  attendenceNumber FROM Attendence ORDER BY   attendenceNumber DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("attendenceNumber");
        }
        return null;
    }

    public Employee getEmpolyee(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Employee WHERE empId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)
            );
        } else {
            return null;
        }
    }

    public boolean saveAttendence(Attendence a) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Attendence VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,a.getAttendenceNumber());
        stm.setObject(2,a.getAttendDate());
        stm.setObject(3,a.getHours());
        stm.setObject(4,a.getWorkingType());
        stm.setObject(5,a.getEmpId());
        stm.setObject(6,a.getEmpName());
        stm.setObject(7,a.getPost());
        return stm.executeUpdate()>0;
    }

    public static List<Attendence> getAllAttendence() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Attendence");
        ResultSet rs = pstm.executeQuery();

        List<Attendence> attendences = new ArrayList<>();

        while (rs.next()) {
            attendences.add(new Attendence(
                   rs.getString("attendenceNumber"),
                    rs.getString("attendDate"),
                    rs.getInt("hours"),
                    rs.getString("workingType"),
                    rs.getString("empId"),
                    rs.getString("empName"),
                    rs.getString("post")
            ));
        }
        return attendences;
    }
}
