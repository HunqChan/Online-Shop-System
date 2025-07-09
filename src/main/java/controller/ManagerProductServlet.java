package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/manager")
public class ManagerProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Product> products = productDAO.getAllProductsMA();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/jsp/productManagement.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
            request.getRequestDispatcher("/jsp/productManagement.jsp").forward(request, response);
        }
    }

    
}