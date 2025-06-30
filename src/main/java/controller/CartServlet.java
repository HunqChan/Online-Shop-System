/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;


import dao.CartDAO;


import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.CartItem;
import model.Product;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        CartDAO cartDAO = new CartDAO();
        ProductDAO productDAO = new ProductDAO();

        // Lấy hoặc tạo cartId
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            Cart cart = new Cart();
            cart.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            cart.setSessionId(session.getId());
            cart.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            cart.setUserId(null);
            if (cartDAO.addCart(cart)) {
                cartId = cartDAO.getAllCarts().stream()
                        .filter(c -> c.getSessionId().equals(session.getId()))
                        .findFirst()
                        .map(Cart::getId)
                        .orElse(null);
                if (cartId != null) {
                    session.setAttribute("cartId", cartId);
                    System.out.println("New cart created with ID: " + cartId);
                } else {
                    System.out.println("Failed to retrieve new cart ID");
                }
            } else {
                System.out.println("Failed to add new cart");
            }
        }

        // Xử lý thêm sản phẩm vào giỏ nếu có productId
        String productIdStr = request.getParameter("productId");
        if (productIdStr != null) {
            long productId = Long.parseLong(productIdStr);
            Product product = productDAO.getAllProducts().stream()
                    .filter(p -> p.getId() == productId)
                    .findFirst()
                    .orElse(null);
            if (product != null && cartId != null) {
                CartItem cartItem = new CartItem();
                cartItem.setCartId(cartId);
                cartItem.setProductId(productId);
                cartItem.setProductName(product.getName());
                cartItem.setPrice(product.getPrice());
                cartItem.setQuantity(1);
                if (cartDAO.addCartItem(cartItem)) {
                    System.out.println("Added product ID " + productId + " to cart ID " + cartId);
                } else {
                    System.out.println("Failed to add cart item for product ID " + productId);
                }
            } else {
                System.out.println("Product or cart not found for productId: " + productIdStr);
            }
        }

        // Lấy danh sách cart items
        List<CartItem> cartItems = (cartId != null) ? cartDAO.getCartItemsByCartId(cartId) : new ArrayList<>();
        request.setAttribute("cartItems", cartItems);

        // Tính tổng giỏ hàng
        double total = (cartId != null) ? cartDAO.calculateCartTotal(cartId) : 0.0;
        request.setAttribute("totalCart", total);

        // Chuyển hướng đến trang cart.jsp
        request.getRequestDispatcher("jsp/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId != null) {
            String cartItemIdStr = request.getParameter("cartItemId");
            String quantityStr = request.getParameter("quantity");
            if (cartItemIdStr != null && quantityStr != null) {
                long cartItemId = Long.parseLong(cartItemIdStr);
                int quantity = Integer.parseInt(quantityStr);
                CartDAO cartDAO = new CartDAO();
                if (cartDAO.updateCartItemQuantity(cartItemId, quantity)) {
                    System.out.println("Updated quantity for cartItem ID " + cartItemId + " to " + quantity);
                } else {
                    System.out.println("Failed to update quantity for cartItem ID " + cartItemId);
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}