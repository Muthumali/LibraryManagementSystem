package controller.manage;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    public static String getLastEmpId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT empId FROM Employee ORDER BY empId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("empId");
        }
        return null;
    }

    //--------------------save Data db-----------------------------------------------------

    public boolean saveLogin(Login b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Login VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getPassword());
        stm.setObject(2,b.getUserName());
        stm.setObject(3,b.getEmpId());
        return stm.executeUpdate()>0;
    }

    public boolean saveEmployee(Employee b, Login a) throws SQLException, ClassNotFoundException {

        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO Employee VALUES(?,?,?,?,?,?,?,?)");


            stm.setObject(1,b.getEmpId());
            stm.setObject(2,b.getEmpName());
            stm.setObject(3,b.getAddress());
            stm.setObject(4,b.getNIC());
            stm.setObject(5,b.getBirthDay());
            stm.setObject(6,b.getContact());
            stm.setObject(7,b.getPost());
            stm.setObject(8,b.getQualification());
            if (stm.executeUpdate() > 0) {
                if (new EmployeeController().saveLogin(a)) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                    return false;
                }
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try{
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }


    //--------------------------get db data-----------------------------------------------
    public static List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Employee");
        ResultSet rs = pstm.executeQuery();

        List<Employee> Employees = new ArrayList<>();

        while (rs.next()) {
            Employees.add(new Employee(
                    rs.getString("empId"),
                    rs.getString("empName"),
                    rs.getString("address"),
                    rs.getString("NIC"),
                    rs.getString("birthDay"),
                    rs.getString("contact"),
                    rs.getString("post"),
                    rs.getString("quali")
            ));
        }

        return Employees;
    }
    //----------------------Delete Employee-----------------------------------------------------
    public static boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Employee WHERE empId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
    //-----------------------Modify employee ------------------------------------------------------
    public boolean ModifyEmployee(Employee e) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Employee SET empName=?, address=?, NIC=?,birthDay =?,contact=?,post=?,quali=? WHERE empId=?");
        stm.setObject(1,e.getEmpName());
        stm.setObject(2,e.getAddress());
        stm.setObject(3,e.getNIC());
        stm.setObject(4,e.getBirthDay());
        stm.setObject(5,e.getContact());
        stm.setObject(6,e.getPost());
        stm.setObject(7,e.getQualification());
        stm.setObject(8,e.getEmpId());
        return stm.executeUpdate()>0;
    }

    public Employee getEmployee(String id) throws SQLException, ClassNotFoundException {
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


}
