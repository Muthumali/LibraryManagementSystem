package controller.manage;
import db.DbConnection;
import model.IssueBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IsssueBookController {

    public static String getLastIssueBookId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT issueId FROM IssueBook ORDER BY issueId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("issueId");
        }
        return null;
    }

    //--------------------save Data db-----------------------------------------------------
    public boolean saveIssueBook(IssueBook b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO IssueBook VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getIssueId());
        stm.setObject(2,b.getIssueDate());
        stm.setObject(3,b.getEndDate());
        stm.setObject(4,b.getBookId());
        stm.setObject(5,b.getMemberId());
        stm.setObject(6,b.getEmpId());
        return stm.executeUpdate()>0;
    }

    public static boolean deleteBook(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM AvailableBook WHERE bookId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }


    //--------------------------get db data-----------------------------------------------
    public static List<IssueBook> getAllIssueBooks() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM IssueBook");
        ResultSet rs = pstm.executeQuery();

        List<IssueBook> issueBooks = new ArrayList<>();

        while (rs.next()) {
            issueBooks.add(new IssueBook(
                   rs.getString("issueId"),
                   rs.getString("issueDate"),
                   rs.getString("endDate"),
                   rs.getString("bookId"),
                   rs.getString("memberId"),
                   rs.getString("empId")
            ));
        }
        return issueBooks;
    }
    //----------------------Delete data-----------------------------------------------------
    public static boolean deleteData(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM IssueBook WHERE issueId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
    //-----------------------Modify data ------------------------------------------------------
    public boolean ModifyData(IssueBook b) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE IssueBook SET issueDate=?, endDate=?, bookId=?,memberId =?,empId=? WHERE issueId=?");
        stm.setObject(1,b.getIssueDate());
        stm.setObject(2,b.getEndDate());
        stm.setObject(3,b.getBookId());
        stm.setObject(4,b.getMemberId());
        stm.setObject(5,b.getEmpId());
        stm.setObject(6,b.getIssueId());
        return stm.executeUpdate()>0;
    }

    public IssueBook getIssueBooks(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM IssueBook WHERE issueId=?");
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

    public static List<IssueBook> searchIssueBook(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM IssueBook WHERE bookId LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<IssueBook> issueBooks = new ArrayList<>();

        while (rs.next()) {

           issueBooks.add(new IssueBook(
                    rs.getString("issueId"),
                    rs.getString("issueDate"),
                    rs.getString("endDate"),
                    rs.getString("bookId"),
                    rs.getString("memberId"),
                    rs.getString("empId")
            ));
        }
        return issueBooks;
    }
    public static boolean deleteIssueData(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM IssueBook WHERE bookId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
}
