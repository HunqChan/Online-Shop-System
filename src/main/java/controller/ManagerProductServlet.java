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
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "edit":
                showEditForm(request, response);
                break;
            case "list":
            default:
                listProducts(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        }
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

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Product product = productDAO.getProductById(id);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/jsp/productManagement.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID sản phẩm không hợp lệ!");
            listProducts(request, response);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Product product = new Product();
            String name = request.getParameter("name");
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
            }
            product.setName(name);
            product.setDescription(request.getParameter("description"));
            String priceParam = request.getParameter("price");
            if (priceParam == null || priceParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Giá sản phẩm không được để trống!");
            }
            product.setPrice(Double.parseDouble(priceParam));
            String stockParam = request.getParameter("stock");
            if (stockParam == null || stockParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Số lượng tồn kho không được để trống!");
            }
            product.setStock(Integer.parseInt(stockParam));
            String imageUrl = request.getParameter("imageUrl");
            product.setImageUrl(imageUrl != null && imageUrl.startsWith("https://res.cloudinary.com") ? imageUrl : "https://via.placeholder.com/64");
            String activeParam = request.getParameter("active");
            product.setActive(activeParam != null && Boolean.parseBoolean(activeParam));
            String weightParam = request.getParameter("weight");
            product.setWeight(weightParam == null || weightParam.trim().isEmpty() ? 1000 : Integer.parseInt(weightParam));
            String lengthParam = request.getParameter("length");
            product.setLength(lengthParam == null || lengthParam.trim().isEmpty() ? 50 : Integer.parseInt(lengthParam));
            String widthParam = request.getParameter("width");
            product.setWidth(widthParam == null || widthParam.trim().isEmpty() ? 30 : Integer.parseInt(widthParam));
            String heightParam = request.getParameter("height");
            product.setHeight(heightParam == null || heightParam.trim().isEmpty() ? 20 : Integer.parseInt(heightParam));
            product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            if (productDAO.addProduct(product)) {
                request.setAttribute("message", "Thêm sản phẩm thành công!");
            } else {
                request.setAttribute("message", "Lỗi khi thêm sản phẩm!");
            }
            listProducts(request, response);
        } catch ( NumberFormatException e) {
            request.setAttribute("message", "Dữ liệu không hợp lệ: " + e.getMessage());
            listProducts(request, response);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Product product = new Product();
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("ID sản phẩm không được để trống!");
            }
            product.setId(Long.parseLong(idParam));
            String name = request.getParameter("name");
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên sản phẩm không được để trống!");
            }
            product.setName(name);
            product.setDescription(request.getParameter("description"));
            String priceParam = request.getParameter("price");
            if (priceParam == null || priceParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Giá sản phẩm không được để trống!");
            }
            product.setPrice(Double.parseDouble(priceParam));
            String stockParam = request.getParameter("stock");
            if (stockParam == null || stockParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Số lượng tồn kho không được để trống!");
            }
            product.setStock(Integer.parseInt(stockParam));
            String imageUrl = request.getParameter("imageUrl");
            product.setImageUrl(imageUrl != null && imageUrl.startsWith("https://res.cloudinary.com") ? imageUrl : "https://via.placeholder.com/64");
            String activeParam = request.getParameter("active");
            product.setActive(activeParam != null && Boolean.parseBoolean(activeParam));
            String weightParam = request.getParameter("weight");
            product.setWeight(weightParam == null || weightParam.trim().isEmpty() ? 1000 : Integer.parseInt(weightParam));
            String lengthParam = request.getParameter("length");
            product.setLength(lengthParam == null || lengthParam.trim().isEmpty() ? 50 : Integer.parseInt(lengthParam));
            String widthParam = request.getParameter("width");
            product.setWidth(widthParam == null || widthParam.trim().isEmpty() ? 30 : Integer.parseInt(widthParam));
            String heightParam = request.getParameter("height");
            product.setHeight(heightParam == null || heightParam.trim().isEmpty() ? 20 : Integer.parseInt(heightParam));
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            if (productDAO.updateProduct(product)) {
                request.setAttribute("message", "Cập nhật sản phẩm thành công!");
            } else {
                request.setAttribute("message", "Lỗi khi cập nhật sản phẩm!");
            }
            listProducts(request, response);
        } catch ( NumberFormatException e) {
            request.setAttribute("message", "Dữ liệu không hợp lệ: " + e.getMessage());
            listProducts(request, response);
        }
    }
}