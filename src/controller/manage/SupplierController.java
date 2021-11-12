package controller.manage;

import db.DbConnection;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierController {
    public static String getSupId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT  supplierId  FROM Supplier ORDER BY  supplierId  DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("supplierId");
        }
        return null;
    }
    //--------------------save Data db-----------------------------------------------------
    public boolean saveSupplier(Supplier b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Supplier VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getSupplierId());
        stm.setObject(2,b.getSupplierName());
        stm.setObject(3,b.getAddress());
        stm.setObject(4,b.getContact());
        return stm.executeUpdate()>0;
    }

    //--------------------------get db data-----------------------------------------------
    public static List<Supplier> getAllSupplier() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Supplier");
        ResultSet rs = pstm.executeQuery();

        List<Supplier> suppliers = new ArrayList<>();

        while (rs.next()) {
            suppliers.add(new Supplier(
                    rs.getString("supplierId"),
                    rs.getString("supplierName"),
                    rs.getString("address"),
                    rs.getString("contact")
            ));
        }

        return suppliers;
    }
    //----------------------Delete data-----------------------------------------------------
    public static boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Supplier WHERE supplierId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
    //-----------------------Modify data ------------------------------------------------------
    public boolean ModifySupplier(Supplier b) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Supplier SET SupplierName=?, address=?, contact=? WHERE supplierId=?");
        stm.setObject(1,b.getSupplierName());
        stm.setObject(2,b.getAddress());
        stm.setObject(3,b.getContact());
        stm.setObject(4,b.getSupplierId());
        return stm.executeUpdate()>0;
    }

    public Supplier getSupplier(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Supplier WHERE supplierId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );
        } else {
            return null;
        }
    }

    public Supplier getSuppliersDetails(String name) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Supplier WHERE supplierName=?");
        stm.setObject(1,name);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Supplier(
                   rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)
            );

        } else {
            return null;
        }
    }
}
