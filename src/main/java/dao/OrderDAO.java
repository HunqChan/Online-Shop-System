/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDAO {
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, createdAt, orderNumber, paymentMethod, paymentStatus, paymentTransactionId, " +
                     "shippingAddress, shippingFee, status, subtotal, total, updatedAt, user_id FROM orders";
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
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách orders: " + e.getMessage());
        }
        return orders;
    }

    public boolean addOrder(Order order) {
        String sql = "INSERT INTO orders (createdAt, orderNumber, paymentMethod, paymentStatus, paymentTransactionId, " +
                     "shippingAddress, shippingFee, status, subtotal, total, updatedAt, user_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setLong(12, order.getUserId() != null ? order.getUserId() : 0);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm order: " + e.getMessage());
        }
        return false;
    }
}