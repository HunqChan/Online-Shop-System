package controller;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Product;

@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách sản phẩm từ DAO
        ProductDAO productDAO = new ProductDAO();
        List<Product> productList = productDAO.getAllProducts();
        
        // Đưa danh sách sản phẩm vào request
        request.setAttribute("productList", productList);
        
        // Chuyển hướng đến trang productList.jsp
        request.getRequestDispatcher("jsp/productList.jsp").forward(request, response);
    }
}