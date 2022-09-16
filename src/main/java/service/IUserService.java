package service;

import model.Review;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    List<User> findAll();

    boolean add(String username, String password, String firstname, String lastname) throws SQLException;

    boolean login(String username, String password) throws SQLException;

    boolean submitOrder(String username, List<String> products, List<Integer> quantities) throws SQLException;

    boolean postReview(Review review) throws SQLException;

    List<String[]> getPurchasedProduct(String username) throws SQLException;

    boolean checkReviewByProductId(String username, String productId) throws SQLException;

    double getAverageRating(String username) throws SQLException;
}
