/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.sql.Timestamp;

public class CartItem {
    private long id;
    private Timestamp createdAt;
    private double price;
    private int quantity;
    private Timestamp updatedAt;
    private Long cartId;
    private long productId;
    private String productName;

    // Constructors
    public CartItem() {}

    public CartItem(long id, Timestamp createdAt, double price, int quantity, Timestamp updatedAt, Long cartId,
                    long productId, String productName) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
        this.quantity = quantity;
        this.updatedAt = updatedAt;
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
}