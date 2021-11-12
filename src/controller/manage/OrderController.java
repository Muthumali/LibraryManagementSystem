package controller.manage;

import db.DbConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public static String getOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("orderId");
        }
        return null;
    }

    public boolean saveOrder(Order b, OrderDetail a) throws SQLException, ClassNotFoundException {

        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO Orders VALUES(?,?,?,?)");
            stm.setObject(1, b.getOrderId());
            stm.setObject(2, b.getBookName());
            stm.setObject(3, b.getQty());
            stm.setObject(4, b.getOrderDate());
            if (stm.executeUpdate() > 0) {
                if (new OrderController().saveOrderDetail(a)) {
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
        } finally {
            try {

                con.setAutoCommit(true);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }


    public boolean saveOrderDetail(OrderDetail a) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO OrderDetail VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,a.getOrderId());
        stm.setObject(2,a.getSupplierId());
        stm.setObject(3,a.getSupplierName());
        stm.setObject(4,a.getAddress());
        stm.setObject(5,a.getContact());
        stm.setObject(6,a.getBookName());
        stm.setObject(7,a.getQty());
        stm.setObject(8,a.getOrderDate());
        return stm.executeUpdate()>0;
    }

    public List<String> getSupplierName() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM Supplier").executeQuery();
        List<String> names = new ArrayList<>();
        while (rst.next()){
           names.add(
                    rst.getString(2)
            );
        }
        return names;
    }

    public static List<OrderDetail> getAllOrders() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM OrderDetail");
        ResultSet rs = pstm.executeQuery();

        List<OrderDetail> orders = new ArrayList<>();

        while (rs.next()) {
            orders.add(new OrderDetail(
                   rs.getString("orderId"),
                   rs.getString("supplierId") ,
                    rs.getString("supplierName"),
                    rs.getString("supAddress")    ,
                    rs.getString("contact")   ,
                    rs.getString("bookName"),
                    rs.getString("qty"),
                    rs.getString("orderDate")
            ));
        }
        return orders;
    }

    public static boolean deleteList(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM OrderDetail WHERE orderId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
