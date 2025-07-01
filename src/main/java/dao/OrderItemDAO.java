package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.OrderItem;

public class OrderItemDAO {
    public List<OrderItem> getOrderItemsByOrderId(long orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.quantity, p.name AS product_name, oi.price " +
                     "FROM order_items oi " +
                     "LEFT JOIN products p ON oi.product_id = p.id " +
                     "WHERE oi.order_id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setQuantity(rs.getInt("quantity"));
                    item.setProductName(rs.getString("product_name"));
                    item.setPrice(rs.getDouble("price"));
                    orderItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách sản phẩm trong order: " + e.getMessage());
        }
        return orderItems;
    }
}