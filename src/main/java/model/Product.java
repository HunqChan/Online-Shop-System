package model;

import java.sql.Timestamp;

public class Product {
    private long id;
    private boolean active;
    private Timestamp createdAt;
    private String description;
    private String imageUrl;
    private String name;
    private double price;
    private int stock;
    private Timestamp updatedAt;
    private Integer weight;
    private Integer length;
    private Integer width;
    private Integer height;

    // Constructors
    public Product() {}

    public Product(long id, boolean active, Timestamp createdAt, String description, String imageUrl,
                   String name, double price, int stock, Timestamp updatedAt, Integer weight, Integer length, Integer width, Integer height) {
        this.id = id;
        this.active = active;
        this.createdAt = createdAt;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.updatedAt = updatedAt;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }
    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
}