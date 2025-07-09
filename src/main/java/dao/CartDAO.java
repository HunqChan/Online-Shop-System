/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.CartItem;

public class CartDAO {
    public List<Cart> getAllCarts() {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT id, createdAt, sessionId, updatedAt, user_id FROM carts";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getLong("id"));
                cart.setCreatedAt(rs.getTimestamp("createdAt"));
                cart.setSessionId(rs.getString("sessionId"));
                cart.setUpdatedAt(rs.getTimestamp("updatedAt"));
                cart.setUserId(rs.getLong("user_id"));
                carts.add(cart);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách carts: " + e.getMessage());
        }
        return carts;
    }

    public List<CartItem> getCartItemsByCartId(long cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT id, createdAt, price, quantity, updatedAt, cart_id, product_id, productName FROM cart_items WHERE cart_id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem item = new CartItem();
                    item.setId(rs.getLong("id"));
                    item.setCreatedAt(rs.getTimestamp("createdAt"));
                    item.setPrice(rs.getDouble("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    item.setCartId(rs.getLong("cart_id"));
                    item.setProductId(rs.getLong("product_id"));
                    item.setProductName(rs.getString("productName"));
                    cartItems.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách cart_items: " + e.getMessage());
        }
        return cartItems;
    }

    public boolean addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO cart_items (createdAt, price, quantity, updatedAt, cart_id, product_id, productName) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setDouble(2, cartItem.getPrice());
            ps.setInt(3, cartItem.getQuantity());
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            ps.setLong(5, cartItem.getCartId());
            ps.setLong(6, cartItem.getProductId());
            ps.setString(7, cartItem.getProductName());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm cart_item: " + e.getMessage());
        }
        return false;
    }

    public boolean updateCartItemQuantity(long cartItemId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = ?, updatedAt = ? WHERE id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.setLong(3, cartItemId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật số lượng cart_item: " + e.getMessage());
        }
        return false;
    }

    public double calculateCartTotal(long cartId) {
        double total = 0;
        String sql = "SELECT SUM(price * quantity) as total FROM cart_items WHERE cart_id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, cartId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tính tổng giỏ hàng: " + e.getMessage());
        }
        return total;
    }

    public Cart getCartById(long cartId) {
    Cart cart = null;
    String sql = "SELECT id, createdAt, sessionId, updatedAt, user_id FROM carts WHERE id = ?";
    try (Connection conn = dbConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setLong(1, cartId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                cart = new Cart();
                cart.setId(rs.getLong("id"));
                cart.setCreatedAt(rs.getTimestamp("createdAt"));
                cart.setSessionId(rs.getString("sessionId"));
                cart.setUpdatedAt(rs.getTimestamp("updatedAt"));
                cart.setUserId(rs.getLong("user_id") != 0 ? rs.getLong("user_id") : null);
            }
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy thông tin cart theo id: " + e.getMessage());
    }
    return cart;
}
    // Thêm phương thức addCart
    public boolean addCart(Cart cart) {
        String sql = "INSERT INTO carts (createdAt, sessionId, updatedAt, user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, cart.getCreatedAt());
            ps.setString(2, cart.getSessionId());
            ps.setTimestamp(3, cart.getUpdatedAt());
            ps.setLong(4, cart.getUserId() != null ? cart.getUserId() : 0); // Xử lý null
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm cart: " + e.getMessage());
        }
        return false;
    }
}