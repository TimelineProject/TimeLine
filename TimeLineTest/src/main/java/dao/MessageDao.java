package dao;

import bean.Message;
import database.DatabaseConnect;

import java.sql.*;

public class MessageDao {

    public void addMessage(Message message){
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DatabaseConnect.conn();
            String sql1 = "select user_id from users where account = '" + message.getUserName() + "'";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            int user_id = rs.getInt("user_id");
            rs.close();
            stmt.close();
            String sql2 = "insert into infos(user_id,information,image,time) values (?,?,?,?);";
            pstmt = connection.prepareStatement(sql2);
            pstmt.setInt(1, user_id);
            pstmt.setString(2, message.getInformation());
            pstmt.setString(3, message.getImage());
            pstmt.setString(4, message.getTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
