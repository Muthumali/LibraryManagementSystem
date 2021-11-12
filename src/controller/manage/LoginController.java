package controller.manage;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    public Boolean getLogin(String password,String post) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM Login WHERE password=? AND post=?");
        stm.setObject(1, password);
        stm.setObject(2, post);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return true;
        }else{
            return false;
        }
    }
}
