package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDAO {
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, createdAt, orderNumber, paymentMethod, paymentStatus, paymentTransactionId, " +
                     "shippingAddress, shippingFee, status, subtotal, total, updatedAt, user_id, " +
                     "to_district_id, to_ward_code, service_id FROM orders";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setCreatedAt(rs.getTimestamp("createdAt"));
                order.setOrderNumber(rs.getString("orderNumber"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));
                order.setPaymentTransactionId(rs.getString("paymentTransactionId"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setShippingFee(rs.getDouble("shippingFee"));
                order.setStatus(rs.getString("status"));
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setTotal(rs.getDouble("total"));
                order.setUpdatedAt(rs.getTimestamp("updatedAt"));
                order.setUserId(rs.getLong("user_id"));
                order.setToDistrictId(rs.getInt("to_district_id"));
                order.setToWardCode(rs.getString("to_ward_code"));
                order.setServiceId(rs.getInt("service_id"));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách orders: " + e.getMessage());
        }
        return orders;
    }

    // Thêm vào OrderDAO class

public Order getOrderByNumber(String orderNumber) {
    String sql = "SELECT * FROM orders WHERE orderNumber = ?";
    try (var conn = dbConnect.getConnection();
         var ps = conn.prepareStatement(sql)) {
        ps.setString(1, orderNumber);
        try (var rs = ps.executeQuery()) {
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setOrderNumber(rs.getString("orderNumber"));
                order.setUserId(rs.getLong("user_id"));
                order.setSubtotal(rs.getDouble("subtotal"));
                order.setShippingFee(rs.getDouble("shippingFee"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(rs.getString("status"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));
                order.setPaymentTransactionId(rs.getString("paymentTransactionId"));
                order.setShippingAddress(rs.getString("shippingAddress"));
                order.setCreatedAt(rs.getTimestamp("createdAt"));
                order.setUpdatedAt(rs.getTimestamp("updatedAt"));
                order.setToDistrictId(rs.getInt("to_district_id"));
                order.setToWardCode(rs.getString("to_ward_code"));
                order.setServiceId(rs.getInt("service_id"));
                return order;
            }
        }
    } catch (SQLException e) {
        System.out.println("Error getting order by number: " + e.getMessage());
        e.printStackTrace();
    }
    return null;
}

public boolean updateOrder(Order order) {
    String sql = "UPDATE orders SET paymentStatus = ?, paymentTransactionId = ?, status = ?, updatedAt = ? WHERE orderNumber = ?";
    try (var conn = dbConnect.getConnection();
         var ps = conn.prepareStatement(sql)) {
        ps.setString(1, order.getPaymentStatus());
        ps.setString(2, order.getPaymentTransactionId());
        ps.setString(3, order.getStatus());
        ps.setTimestamp(4, order.getUpdatedAt());
        ps.setString(5, order.getOrderNumber());
        
        int rowsUpdated = ps.executeUpdate();
        System.out.println("Rows updated: " + rowsUpdated + " for order: " + order.getOrderNumber());
        return rowsUpdated > 0;
    } catch (SQLException e) {
        System.out.println("Error updating order: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

// Method để debug - liệt kê tất cả orders
public void debugPrintAllOrders() {
    String sql = "SELECT orderNumber, status, paymentStatus, createdAt FROM orders ORDER BY createdAt DESC";
    try (var conn = dbConnect.getConnection();
         var ps = conn.prepareStatement(sql);
         var rs = ps.executeQuery()) {
        
        System.out.println("=== Recent Orders ===");
        int count = 0;
        while (rs.next() && count < 10) {
            System.out.println("Order: " + rs.getString("orderNumber") + 
                             ", Status: " + rs.getString("status") + 
                             ", Payment: " + rs.getString("paymentStatus") + 
                             ", Created: " + rs.getTimestamp("createdAt"));
            count++;
        }
    } catch (SQLException e) {
        System.out.println("Error debugging orders: " + e.getMessage());
        e.printStackTrace();
    }
}
    public boolean addOrder(Order order) {
        String sql = "INSERT INTO orders (createdAt, orderNumber, paymentMethod, paymentStatus, paymentTransactionId, " +
                     "shippingAddress, shippingFee, status, subtotal, total, updatedAt, user_id, " +
                     "to_district_id, to_ward_code, service_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, order.getCreatedAt());
            ps.setString(2, order.getOrderNumber());
            ps.setString(3, order.getPaymentMethod());
            ps.setString(4, order.getPaymentStatus());
            ps.setString(5, order.getPaymentTransactionId());
            ps.setString(6, order.getShippingAddress());
            ps.setDouble(7, order.getShippingFee());
            ps.setString(8, order.getStatus());
            ps.setDouble(9, order.getSubtotal());
            ps.setDouble(10, order.getTotal());
            ps.setTimestamp(11, order.getUpdatedAt());
            ps.setObject(12, order.getUserId(), java.sql.Types.BIGINT);
            ps.setObject(13, order.getToDistrictId(), java.sql.Types.INTEGER);
            ps.setString(14, order.getToWardCode());
            ps.setObject(15, order.getServiceId(), java.sql.Types.INTEGER);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm order: " + e.getMessage());
            return false;
        }
    }
    
     public Order getOrderById(long orderId) {
        Order order = null;
        String sql = "SELECT id, createdAt, orderNumber, paymentMethod, paymentStatus, paymentTransactionId, " +
                     "shippingAddress, shippingFee, status, subtotal, total, updatedAt, user_id, " +
                     "to_district_id, to_ward_code, service_id FROM orders WHERE id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setCreatedAt(rs.getTimestamp("createdAt"));
                    order.setOrderNumber(rs.getString("orderNumber"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setPaymentStatus(rs.getString("paymentStatus"));
                    order.setPaymentTransactionId(rs.getString("paymentTransactionId"));
                    order.setShippingAddress(rs.getString("shippingAddress"));
                    order.setShippingFee(rs.getDouble("shippingFee"));
                    order.setStatus(rs.getString("status"));
                    order.setSubtotal(rs.getDouble("subtotal"));
                    order.setTotal(rs.getDouble("total"));
                    order.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    order.setUserId(rs.getLong("user_id") != 0 ? rs.getLong("user_id") : null);
                    order.setToDistrictId(rs.getInt("to_district_id") != 0 ? rs.getInt("to_district_id") : null);
                    order.setToWardCode(rs.getString("to_ward_code"));
                    order.setServiceId(rs.getInt("service_id") != 0 ? rs.getInt("service_id") : null);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy thông tin order: " + e.getMessage());
        }
        return order;
    }
}