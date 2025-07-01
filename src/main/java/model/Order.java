/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.sql.Timestamp;

public class Order {
    private long id;
    private Timestamp createdAt;
    private String orderNumber;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentTransactionId;
    private String shippingAddress;
    private double shippingFee;
    private String status;
    private double subtotal;
    private double total;
    private Timestamp updatedAt;
    private Long userId;
    private Integer toDistrictId;
    private String toWardCode;
    private Integer serviceId;

    public Order() {}

    public Order(long id, Timestamp createdAt, String orderNumber, String paymentMethod, String paymentStatus,
                 String paymentTransactionId, String shippingAddress, double shippingFee, String status,
                 double subtotal, double total, Timestamp updatedAt, Long userId, Integer toDistrictId,
                 String toWardCode, Integer serviceId) {
        this.id = id;
        this.createdAt = createdAt;
        this.orderNumber = orderNumber;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentTransactionId = paymentTransactionId;
        this.shippingAddress = shippingAddress;
        this.shippingFee = shippingFee;
        this.status = status;
        this.subtotal = subtotal;
        this.total = total;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.toDistrictId = toDistrictId;
        this.toWardCode = toWardCode;
        this.serviceId = serviceId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentTransactionId() { return paymentTransactionId; }
    public void setPaymentTransactionId(String paymentTransactionId) { this.paymentTransactionId = paymentTransactionId; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public double getShippingFee() { return shippingFee; }
    public void setShippingFee(double shippingFee) { this.shippingFee = shippingFee; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getToDistrictId() { return toDistrictId; }
    public void setToDistrictId(Integer toDistrictId) { this.toDistrictId = toDistrictId; }
    public String getToWardCode() { return toWardCode; }
    public void setToWardCode(String toWardCode) { this.toWardCode = toWardCode; }
    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }
}