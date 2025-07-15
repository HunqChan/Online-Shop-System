package dal;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext<User> {

    public User findById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND is_deleted = 0";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND is_deleted = 0";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND is_deleted = 0";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> select() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE is_deleted = 0";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User select(int... id) {
        if (id.length == 0) return null;
        String sql = "SELECT * FROM users WHERE user_id = ? AND is_deleted = 0";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id[0]);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(User user) {
        String sql = "INSERT INTO users (role_id, full_name, password, avatar_url, gender, email, phone_number, " +
                "province_id, province_name, district_id, district_name, ward_code, ward_name, detail_address, created_at, is_deleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), 0)";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getRoleId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAvatarUrl());
            ps.setBoolean(5, user.getGender());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPhoneNumber());
            ps.setObject(8, user.getProvinceId(), Types.INTEGER);
            ps.setString(9, user.getProvinceName());
            ps.setObject(10, user.getDistrictId(), Types.INTEGER);
            ps.setString(11, user.getDistrictName());
            ps.setString(12, user.getWardCode());
            ps.setString(13, user.getWardName());
            ps.setString(14, user.getDetailAddress());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(User user) {
        String sql = "UPDATE users SET role_id = ?, full_name = ?, password = ?, avatar_url = ?, gender = ?, " +
                "email = ?, phone_number = ?, province_id = ?, province_name = ?, district_id = ?, district_name = ?, " +
                "ward_code = ?, ward_name = ?, detail_address = ?, updated_at = GETDATE() WHERE user_id = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getRoleId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAvatarUrl());
            ps.setBoolean(5, user.getGender());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPhoneNumber());
            ps.setObject(8, user.getProvinceId(), Types.INTEGER);
            ps.setString(9, user.getProvinceName());
            ps.setObject(10, user.getDistrictId(), Types.INTEGER);
            ps.setString(11, user.getDistrictName());
            ps.setString(12, user.getWardCode());
            ps.setString(13, user.getWardName());
            ps.setString(14, user.getDetailAddress());
            ps.setInt(15, user.getUserId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int delete(int... id) {
        if (id.length == 0) return 0;
        String sql = "UPDATE users SET is_deleted = 1 WHERE user_id = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id[0]);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .roleId(rs.getInt("role_id"))
                .fullName(rs.getString("full_name"))
                .password(rs.getString("password"))
                .avatarUrl(rs.getString("avatar_url"))
                .gender(rs.getBoolean("gender"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .provinceId((Integer) rs.getObject("province_id"))
                .provinceName(rs.getString("province_name"))
                .districtId((Integer) rs.getObject("district_id"))
                .districtName(rs.getString("district_name"))
                .wardCode(rs.getString("ward_code"))
                .wardName(rs.getString("ward_name"))
                .detailAddress(rs.getString("detail_address"))
                .createdAt(rs.getTimestamp("created_at"))
                .updatedAt(rs.getTimestamp("updated_at"))
                .isDeleted(rs.getBoolean("is_deleted"))
                .resetPasswordToken(rs.getString("reset_password_token"))
                .resetPasswordExpiry(rs.getTimestamp("reset_password_expiry"))
                .build();
    }

}
