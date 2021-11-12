package controller.manage;

import controller.manage.BookController;
import db.DbConnection;
import model.AvailableBook;
import model.IssueBook;
import model.ReturnBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnBookController {

    public static String getLastRId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT returnId FROM ReturnBook ORDER BY returnId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("returnId");
        }
        return null;
    }

    public IssueBook getIssueBooks(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM IssueBook WHERE bookId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new IssueBook(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        } else {
            return null;
        }
    }


    public boolean saveReturnBook(ReturnBook b, AvailableBook a) throws SQLException, ClassNotFoundException {

        Connection con = null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("INSERT INTO ReturnBook VALUES(?,?,?,?,?,?,?,?,?,?)");


            stm.setObject(1, b.getReturnId());
            stm.setObject(2, b.getIssueId());
            stm.setObject(3, b.getIssueDate());
            stm.setObject(4, b.getEndDate());
            stm.setObject(5, b.getReturnDate());
            stm.setObject(6, b.getBookId());
            stm.setObject(7, b.getMemberId());
            stm.setObject(8, b.getEmpId());
            stm.setObject(9, b.getLateDate());
            stm.setObject(10, b.getFines());
            if (stm.executeUpdate() > 0) {
                if (new BookController().saveABook(a)) {
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

    public static boolean deleteIssueBook(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM IssueBook WHERE bookId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }

    public static List<ReturnBook> searchReturnBook(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM ReturnBook WHERE memberId LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<ReturnBook> returnBooks = new ArrayList<>();

        while (rs.next()) {
            returnBooks.add(new ReturnBook(
                    rs.getString("returnId"),
                   rs.getString("issueId"),
                    rs.getString("issueDate"),
                    rs.getString("endDate"),
                    rs.getString("returnDate"),
                    rs.getString("bookId"),
                    rs.getString("memberId"),
                    rs.getString("empId"),
                    rs.getInt("lateDate"),
                    rs.getDouble("fines")
            ));
        }

        return returnBooks;
    }


    public static List<ReturnBook> getAllMember() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM ReturnBook");
        ResultSet rs = pstm.executeQuery();

        List<ReturnBook> returnBook = new ArrayList<>();

        while (rs.next()) {
            returnBook.add(new ReturnBook(
                    rs.getString("returnId"),
                    rs.getString("issueId"),
                    rs.getString("issueDate"),
                    rs.getString("endDate"),
                    rs.getString("returnDate"),
                    rs.getString("bookId"),
                    rs.getString("memberId"),
                    rs.getString("empId"),
                    rs.getInt("lateDates"),
                    rs.getDouble("fines")

            ));
        }

        return returnBook;
    }
}


