/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import java.sql.Timestamp;

public class OrderItem {
    private long id;
    private Timestamp createdAt;
    private double price;
    private String productName;
    private int quantity;
    private Timestamp updatedAt;
    private long orderId;
    private long productId;

    // Constructors
    public OrderItem() {}

    public OrderItem(long id, Timestamp createdAt, double price, String productName, int quantity, Timestamp updatedAt,
                     long orderId, long productId) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
        this.productName = productName;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.orderId = orderId;
        this.productId = productId;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }
    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }
}