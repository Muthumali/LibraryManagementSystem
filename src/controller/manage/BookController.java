package controller.manage;

import db.DbConnection;
import model.AvailableBook;
import model.Book;
import model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    public static String getLasBookId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT bookId FROM Book ORDER BY bookId DESC LIMIT 1");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getString("bookId");
        }
        return null;
    }
//--------------------save Data db-----------------------------------------------------
    public boolean saveBook(Book b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Book VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getBookId());
        stm.setObject(2,b.getBookName());
        stm.setObject(3,b.getWritterName());
        stm.setObject(4,b.getBookLanguage());
        stm.setObject(5,b.getPrice());
        stm.setObject(6,b.getType());
        stm.setObject(7,b.getAddDate());
        return stm.executeUpdate()>0;
    }

    public boolean saveABook(AvailableBook b) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO AvailableBook VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,b.getBookId());
        stm.setObject(2,b.getBookName());
        stm.setObject(3,b.getWritterName());
        stm.setObject(4,b.getBookLanguage());
        stm.setObject(5,b.getPrice());
        stm.setObject(6,b.getType());
        stm.setObject(7,b.getAddDate());
        return stm.executeUpdate()>0;
    }

    //--------------------------get db data-----------------------------------------------
    public static List<Book> getAllBooks() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM Book");
        ResultSet rs = pstm.executeQuery();

        List<Book> Books = new ArrayList<>();

        while (rs.next()) {
            Books.add(new Book(
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
//----------------------Delete Book-----------------------------------------------------
    public static boolean deleteBook(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("DELETE FROM Book WHERE bookId=?");
        pstm.setObject(1,id);
        return pstm.executeUpdate() > 0;
    }
//-----------------------Modify Book ------------------------------------------------------
    public boolean ModifyBook(Book b) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Book SET bookName=?, writterName=?, bookLanguage=?,price=?,bType=?,addDate=? WHERE bookId=?");
        stm.setObject(1,b.getBookName());
        stm.setObject(2,b.getWritterName());
        stm.setObject(3,b.getBookLanguage());
        stm.setObject(4,b.getPrice());
        stm.setObject(5,b.getType());
        stm.setObject(6,b.getAddDate());
        stm.setObject(7,b.getBookId());
        return stm.executeUpdate()>0;
    }

    public Book getBooks(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Book WHERE bookId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Book(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7)
            );
        } else {
            return null;
        }
    }


    public static class MemberController {
        public static String getLastMemberId() throws SQLException, ClassNotFoundException {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT memberId FROM Member ORDER BY memberId DESC LIMIT 1");
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("memberId");
            }
            return null;
        }
        //--------------------save Data db-----------------------------------------------------
        public boolean saveMember(Member b) throws SQLException, ClassNotFoundException {
            Connection con= DbConnection.getInstance().getConnection();
            String query="INSERT INTO Member VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setObject(1,b.getMemberId());
            stm.setObject(2,b.getMemberName());
            stm.setObject(3,b.getAddress());
            stm.setObject(4,b.getNic());
            stm.setObject(5,b.getBirthday());
            stm.setObject(6,b.getContact());
            stm.setObject(7,b.getEmail());
            stm.setObject(8,b.getRegisterDate());
            stm.setObject(9,b.getRegisterFee());
            return stm.executeUpdate()>0;
        }

        //--------------------------get db data-----------------------------------------------
        public static List<Member> getAllMember() throws SQLException, ClassNotFoundException {
            Connection con = DbConnection.getInstance().getConnection();

            PreparedStatement pstm = con.prepareStatement("SELECT * FROM Member");
            ResultSet rs = pstm.executeQuery();

            List<Member> members = new ArrayList<>();

            while (rs.next()) {
                members.add(new Member(
                        rs.getString("memberId"),
                        rs.getString("memberName"),
                        rs.getString("address"),
                        rs.getString("nic"),
                        rs.getString("birthday"),
                        rs.getString("contact"),
                        rs.getString("email"),
                        rs.getString("registerDate"),
                        rs.getDouble("registerFee")

                ));
            }

            return members;
        }
        //----------------------Delete Member-----------------------------------------------------
        public static boolean deleteMember(String id) throws SQLException, ClassNotFoundException {
            Connection con = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = con.prepareStatement("DELETE FROM Member WHERE memberId=?");
            pstm.setObject(1,id);
            return pstm.executeUpdate() > 0;
        }
        //-----------------------Modify Member ------------------------------------------------------
        public boolean ModifyMember(Member b) throws SQLException, ClassNotFoundException {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Member SET memberName=?, address=?, nic=?,birthDay =?,contact=?,email=?, registerDate=?,registerFee=? WHERE memberId=?");
            stm.setObject(1,b.getMemberName());
            stm.setObject(2,b.getAddress());
            stm.setObject(3,b.getNic());
            stm.setObject(4,b.getBirthday());
            stm.setObject(5,b.getContact());
            stm.setObject(6,b.getEmail());
            stm.setObject(7,b.getRegisterDate());
            stm.setObject(8,b.getRegisterFee());
            stm.setObject(9,b.getMemberId());
            return stm.executeUpdate()>0;
        }

        public Member getMembers(String id) throws SQLException, ClassNotFoundException {
            PreparedStatement stm = DbConnection.getInstance()
                    .getConnection().prepareStatement("SELECT * FROM Member WHERE memberId=?");
            stm.setObject(1, id);

            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return new Member(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getDouble(9)
                );
            } else {
                return null;
            }
        }


        public static List<Member> searchMember(String value) throws SQLException, ClassNotFoundException {
            Connection con = DbConnection.getInstance().getConnection();

            PreparedStatement pstm = con.prepareStatement("SELECT * FROM Member WHERE memberName LIKE '%"+value+"%'");
            ResultSet rs = pstm.executeQuery();

            List<Member> members = new ArrayList<>();

            while (rs.next()) {
                members.add(new Member(
                        rs.getString("memberId"),
                        rs.getString("memberName"),
                        rs.getString("address"),
                        rs.getString("nic"),
                        rs.getString("birthday"),
                        rs.getString("contact"),
                        rs.getString("email"),
                        rs.getString("registerDate"),
                        rs.getDouble("registerFee")
                ));
            }

            return members;
        }

    }
}
