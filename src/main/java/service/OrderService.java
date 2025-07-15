package service;

import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.UserDAO;
import dao.dbConnect;
import model.Order;
import model.OrderItem;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public Order getOrderById(int id) throws SQLException {
        return new OrderDAO().getOrderById(id);
    }
    public User getUserById(long userId) throws SQLException {
        return new UserDAO().getUserById(userId);
    }
    public List<OrderItem> getItemsByOrderId(int orderId) throws SQLException {
        return new OrderItemDAO().getItemsByOrderId(orderId);
    }
    public void updateOrderV2(Order order) throws SQLException {
        new OrderDAO().updateOrderV2(order);
    }
    public void updateOrderItem(OrderItem item) throws SQLException {
       new OrderItemDAO().updateOrderItem(item);
    }
}
