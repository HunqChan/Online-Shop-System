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
import model.Product;

public class ProductDAO {
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, active, createdAt, description, image, imageUrl, name, price, stock, updatedAt FROM products";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setActive(rs.getBoolean("active"));
                product.setCreatedAt(rs.getTimestamp("createdAt"));
                product.setDescription(rs.getString("description"));
                product.setImage(rs.getString("image"));
                product.setImageUrl(rs.getString("imageUrl"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setUpdatedAt(rs.getTimestamp("updatedAt"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách products: " + e.getMessage());
        }
        return products;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (active, createdAt, description, image, imageUrl, name, price, stock, updatedAt) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, product.isActive());
            ps.setTimestamp(2, product.getCreatedAt());
            ps.setString(3, product.getDescription());
            ps.setString(4, product.getImage());
            ps.setString(5, product.getImageUrl());
            ps.setString(6, product.getName());
            ps.setDouble(7, product.getPrice());
            ps.setInt(8, product.getStock());
            ps.setTimestamp(9, product.getUpdatedAt());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm product: " + e.getMessage());
        }
        return false;
    }
}