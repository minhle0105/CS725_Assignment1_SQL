package controller;

import model.Product;
import model.Review;
import service.IProductService;
import service.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductController {
    IProductService productService;

    public ProductController(Connection connection) {
        this.productService = new ProductService(connection);
    }

    public List<Product> findAll() throws SQLException {
        return this.productService.findAll();
    }

    public boolean add(Product p ) throws SQLException {
        return productService.addNew(p);
    }

    public boolean updateStockUnit(Product product, int count) throws SQLException {
        return productService.updateById(product, count);
    }

    public Product getById(String id) throws SQLException {
        return productService.getById(id);
    }

    public List<Review> getReviewsByProduct(String product_id) throws SQLException {
        return productService.getReviewByProduct(product_id);
    }

    public double calculatePercentageWithStockLevelLessThan0() throws SQLException {
        return productService.calculatePercentageOfProductsWithStockLevelLessThan0();
    }
}
