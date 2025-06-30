/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;



import java.sql.Timestamp;

public class Cart {
    private long id;
    private Timestamp createdAt;
    private String sessionId;
    private Timestamp updatedAt;
    private Long userId; // Sử dụng Long để cho phép null

    // Constructors
    public Cart() {}

    public Cart(long id, Timestamp createdAt, String sessionId, Timestamp updatedAt, Long userId) {
        this.id = id;
        this.createdAt = createdAt;
        this.sessionId = sessionId;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}