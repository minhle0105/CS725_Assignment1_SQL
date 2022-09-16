package controller;

import model.Review;
import model.User;
import service.IUserService;
import service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    IUserService userService;

    public UserController(Connection connection) {
        this.userService = new UserService(connection);
    }

    public boolean CreateAccount(User user) throws SQLException {
        return this.userService.add(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName());
    }

    public List<User> findAll() {
        return this.userService.findAll();
    }

    public boolean login(String username, String password) throws SQLException {
        return this.userService.login(username, password);
    }

    public boolean submitOrder(String username, List<String> products, List<Integer> quantityDemanded) throws SQLException {
        return this.userService.submitOrder(username, products, quantityDemanded);
    }

    public boolean submitReview(Review review) throws SQLException {
        return this.userService.postReview(review);
    }

    public List<String[]> getPurchasedProduct(String username) throws SQLException {
        return this.userService.getPurchasedProduct(username);
    }

    public boolean checkReviewByProductId(String username, String productId) throws SQLException {
        return this.userService.checkReviewByProductId(username, productId);
    }

    public double getAverageRatingByUser(String username) throws SQLException {
        return this.userService.getAverageRating(username);
    }
}
