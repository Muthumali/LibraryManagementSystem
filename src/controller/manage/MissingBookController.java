package controller.manage;

import db.DbConnection;
import model.LostBook;
import model.ReturnBook;
import model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MissingBookController {
    public static String getLastLostId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT lostId FROM LostBook ORDER BY lostId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("lostId");
        }
        return null;
    }

    public boolean saveLostBook(LostBook b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO LostBook VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getLostId());
        stm.setObject(2,b.getMemberId());
        stm.setObject(3,b.getIssueDate());
        stm.setObject(4,b.getBookId());
        stm.setObject(5,b.getBookName());
        stm.setObject(6,b.getBookPrice());
        stm.setObject(7,b.getTotalFines());
        stm.setObject(8,b.getPayDate());
        return stm.executeUpdate()>0;
    }


 /* public static boolean deleteBook(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM IssueBook WHERE bookId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }*/

    public static List<LostBook> getAllLostBook() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM LostBook");
        ResultSet rs = pstm.executeQuery();

        List<LostBook> lostBooks = new ArrayList<>();

        while (rs.next()) {
            lostBooks.add(new LostBook(
                    rs.getString("lostId"),
                    rs.getString("memberId"),
                    rs.getString("issueDate"),
                    rs.getString("bookId"),
                    rs.getString("bookName"),
                    rs.getDouble("bookPrice"),
                    rs.getDouble("totalFines"),
                    rs.getString("payDate")
            ));
        }

        return lostBooks;
    }


    public static List<LostBook> searchLostBooks(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM LostBook WHERE lostId LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<LostBook> lostBooks = new ArrayList<>();

        while (rs.next()) {
            lostBooks.add(new LostBook(
                    rs.getString("lostId"),
                    rs.getString("memberId"),
                    rs.getString("issueDate"),
                    rs.getString("bookId"),
                    rs.getString("bookName"),
                    rs.getDouble("bookPrice"),
                    rs.getDouble("totalFines"),
                    rs.getString("payDate")
            ));
        }

        return lostBooks;
    }


}
