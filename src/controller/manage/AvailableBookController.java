package controller.manage;

import db.DbConnection;
import model.AvailableBook;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvailableBookController {
    public static List<AvailableBook> getAllBooks() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM AvailableBook");
        ResultSet rs = pstm.executeQuery();

        List<AvailableBook> Books = new ArrayList<>();

        while (rs.next()) {
            Books.add(new AvailableBook(
                    rs.getString("bookId"),
                    rs.getString("bookName"),
                    rs.getString("writterName"),
                    rs.getString("bookLanguage"),
                    rs.getDouble("price"),
                    rs.getString("bType"),
                    rs.getString("addDate")
            ));
        }

        return Books;
    }

    public static List<AvailableBook> searchBook(String value) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM AvailableBook WHERE bookName LIKE '%"+value+"%'");
        ResultSet rs = pstm.executeQuery();

        List<AvailableBook> books = new ArrayList<>();

        while (rs.next()) {
            books.add(new AvailableBook(
                    rs.getString("bookId"),
                    rs.getString("bookName"),
                    rs.getString("writterName"),
                    rs.getString("bookLanguage"),
                    rs.getDouble("price"),
                    rs.getString("bType"),
                    rs.getString("addDate")
            ));
        }

        return books;
    }
}
