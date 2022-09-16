package service;

import model.Product;
import model.Review;

import java.sql.SQLException;
import java.util.List;

public interface IProductService {
    List<Product> findAll() throws SQLException;

    boolean addNew(Product p) throws SQLException;

    Product getById(String id) throws SQLException;

    boolean deleteById(String id) throws SQLException;

    boolean updateById(Product product, int countToAdd) throws SQLException;

    List<Review> getReviewByProduct(String id) throws SQLException;
}
