package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User getUserById(long userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id=?";
        PreparedStatement ps = dbConnect.getConnection().prepareStatement(sql);
        ps.setLong(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setFullName(rs.getString("full_name"));
            user.setPhoneNumber(rs.getString("phone_number"));
            return user;
        }
        return null;
    }
}
