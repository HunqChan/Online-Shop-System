package controller;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long cartId = (Long) session.getAttribute("cartId");
        if (cartId == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=Giỏ hàng không tồn tại");
            return;
        }

        CartDAO cartDAO = new CartDAO();
        if (cartDAO.getCartById(cartId) == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=Giỏ hàng không hợp lệ");
            return;
        }

        double subtotal = cartDAO.calculateCartTotal(cartId);
        request.setAttribute("subtotal", subtotal);
        request.getRequestDispatcher("jsp/checkout.jsp").forward(request, response);
    }
}