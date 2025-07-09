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
        String sql = "SELECT id, active, createdAt, description, imageUrl, name, price, stock, updatedAt, weight, length, width, height FROM products where active = 1";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setActive(rs.getBoolean("active"));
                product.setCreatedAt(rs.getTimestamp("createdAt"));
                product.setDescription(rs.getString("description"));
                product.setImageUrl(rs.getString("imageUrl"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setUpdatedAt(rs.getTimestamp("updatedAt"));
                product.setWeight(rs.getObject("weight") != null ? rs.getInt("weight") : null);
                product.setLength(rs.getObject("length") != null ? rs.getInt("length") : null);
                product.setWidth(rs.getObject("width") != null ? rs.getInt("width") : null);
                product.setHeight(rs.getObject("height") != null ? rs.getInt("height") : null);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách products: " + e.getMessage());
        }
        return products;
    }
    
    public List<Product> getAllProductsMA() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, active, createdAt, description, imageUrl, name, price, stock, updatedAt, weight, length, width, height FROM products";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setActive(rs.getBoolean("active"));
                product.setCreatedAt(rs.getTimestamp("createdAt"));
                product.setDescription(rs.getString("description"));
                product.setImageUrl(rs.getString("imageUrl"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock"));
                product.setUpdatedAt(rs.getTimestamp("updatedAt"));
                product.setWeight(rs.getObject("weight") != null ? rs.getInt("weight") : null);
                product.setLength(rs.getObject("length") != null ? rs.getInt("length") : null);
                product.setWidth(rs.getObject("width") != null ? rs.getInt("width") : null);
                product.setHeight(rs.getObject("height") != null ? rs.getInt("height") : null);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách products: " + e.getMessage());
        }
        return products;
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (active, createdAt, description, imageUrl, name, price, stock, updatedAt, weight, length, width, height) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, product.isActive());
            ps.setTimestamp(2, product.getCreatedAt());
            ps.setString(3, product.getDescription());
            ps.setString(4, product.getImageUrl());
            ps.setString(5, product.getName());
            ps.setDouble(6, product.getPrice());
            ps.setInt(7, product.getStock());
            ps.setTimestamp(8, product.getUpdatedAt());
            ps.setObject(9, product.getWeight() != null ? product.getWeight() : 1000);
            ps.setObject(10, product.getLength() != null ? product.getLength() : 50);
            ps.setObject(11, product.getWidth() != null ? product.getWidth() : 30);
            ps.setObject(12, product.getHeight() != null ? product.getHeight() : 20);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm product: " + e.getMessage());
        }
        return false;
    }

    public Product getProductById(long id) {
        String sql = "SELECT id, active, createdAt, description, imageUrl, name, price, stock, updatedAt, weight, length, width, height FROM products WHERE id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setActive(rs.getBoolean("active"));
                    product.setCreatedAt(rs.getTimestamp("createdAt"));
                    product.setDescription(rs.getString("description"));
                    product.setImageUrl(rs.getString("imageUrl"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock"));
                    product.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    product.setWeight(rs.getObject("weight") != null ? rs.getInt("weight") : null);
                    product.setLength(rs.getObject("length") != null ? rs.getInt("length") : null);
                    product.setWidth(rs.getObject("width") != null ? rs.getInt("width") : null);
                    product.setHeight(rs.getObject("height") != null ? rs.getInt("height") : null);
                    return product;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy product theo ID: " + e.getMessage());
        }
        return null;
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET active = ?, description = ?, imageUrl = ?, name = ?, price = ?, stock = ?, updatedAt = ?, weight = ?, length = ?, width = ?, height = ? WHERE id = ?";
        try (Connection conn = dbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, product.isActive());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getImageUrl());
            ps.setString(4, product.getName());
            ps.setDouble(5, product.getPrice());
            ps.setInt(6, product.getStock());
            ps.setTimestamp(7, product.getUpdatedAt());
            ps.setObject(8, product.getWeight());
            ps.setObject(9, product.getLength());
            ps.setObject(10, product.getWidth());
            ps.setObject(11, product.getHeight());
            ps.setLong(12, product.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật product: " + e.getMessage());
        }
        return false;
    }
}