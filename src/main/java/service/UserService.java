package service;

import model.CheckoutItem;
import model.Order;
import model.Review;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserService implements IUserService {
    Connection connection;
    IOrderService orderService;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    public List<User> findAll() {
        try {
            List<User> users = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"));
                users.add(user);
            }
            connection.commit();
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(String username, String password, String firstname, String lastname) throws SQLException {
        String lookupQuery = "SELECT * FROM User WHERE username=?;";
        PreparedStatement p = connection.prepareStatement(lookupQuery);
        p.setString(1, username);
        ResultSet lookupQueryResult = p.executeQuery();
        if (!lookupQueryResult.next()) {
            String insertQuery = "INSERT INTO User (`username`, `password`, `firstname`, `lastname`) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, lastname);
            preparedStatement.execute();
            connection.commit();
            return true;
        }
        else {
            connection.rollback();
            return false;
        }


    }

    public boolean login(String username, String password) throws SQLException {
        String lookupQuery = "SELECT * FROM User WHERE username=? AND password=?";
        PreparedStatement p = connection.prepareStatement(lookupQuery);
        p.setString(1, username);
        p.setString(2, password);
        ResultSet resultSet = p.executeQuery();
        return resultSet.next();
    }

    public boolean submitOrder(String username, List<String> products, List<Integer> quantities) throws SQLException {
        List<CheckoutItem> checkoutItems = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            int quantityDemanded = quantities.get(i);
            String lookupQuantityQuery = "SELECT * FROM Products WHERE name = ?;";
            String productName = products.get(i);
            PreparedStatement p = connection.prepareStatement(lookupQuantityQuery);
            p.setString(1, productName);
            ResultSet resultSet = p.executeQuery();
            if (!resultSet.next()) {
                connection.rollback();
                return false;
            }
            int quantity = Integer.parseInt(resultSet.getString("number_of_units"));
            String productId = resultSet.getString("idProducts");
            if (quantity < quantityDemanded) {
                connection.rollback();
                return false;
            }
            int newQuantity = quantity - quantityDemanded + quantityDemanded;
            String updateQuantityQuery = "UPDATE Products SET number_of_units = ? WHERE name = ?;";
            CheckoutItem checkoutItem = new CheckoutItem(productId, quantityDemanded);
            checkoutItems.add(checkoutItem);
            p = connection.prepareStatement(updateQuantityQuery);
            p.setInt(1, newQuantity);
            p.setString(2, productName);
            p.execute();
        }
        this.orderService = new OrderService(this.connection);
        Order order = new Order(checkoutItems, new Date(Calendar.getInstance().getTime().getTime()), username);
        this.orderService.save(order);
        connection.commit();
        return true;
    }

    public boolean checkReviewByProductId(String username, String productId) throws SQLException {
        String checkQuery = "SELECT * FROM Review WHERE username = ? AND product_id = ?;";
        PreparedStatement p = connection.prepareStatement(checkQuery);
        p.setString(1, username);
        p.setString(2, productId);
        ResultSet resultSet = p.executeQuery();
        if (resultSet.next()) {
            connection.rollback();
            return true;
        }
        return false;
    }

    public boolean postReview(Review review) throws SQLException {
        try {
            String username = review.getUsername();
            String productId = review.getProduct_id();
            Date date = review.getDate();
            String reviewText = review.getText();
            int rating = review.getRating();
            String addReview = "INSERT INTO Review (`username`, `product_id`, `date`, `content`, `rating`) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement p = connection.prepareStatement(addReview);
            p.setString(1, username);
            p.setString(2, productId);
            p.setDate(3, date);
            p.setString(4, reviewText);
            p.setInt(5, rating);
            p.execute();
            connection.commit();
            return true;
        }
        catch (SQLException e) {
            connection.rollback();
            return false;
        }
    }

    public List<String[]> getPurchasedProduct(String username) throws SQLException {
        String query = "SELECT DISTINCT od.product_id, p.name, o.username " +
                "from Order_Details od JOIN Orders o ON od.order_id = o.idOrders " +
                "JOIN Products p ON od.product_id = p.idProducts WHERE username=?";
        PreparedStatement p = connection.prepareStatement(query);
        p.setString(1, username);
        ResultSet resultSet = p.executeQuery();
        List<String[]> res = new ArrayList<>();
        while (resultSet.next()) {
            String[] r = {resultSet.getString("product_id"), resultSet.getString("name")};
            res.add(r);
        }
        return res;
    }

    @Override
    public double getAverageRating(String username) throws SQLException {
        String query = "SELECT (SELECT SUM(rating) FROM Review WHERE username=?  GROUP BY username) / (SELECT COUNT(*) FROM Review WHERE Username=? GROUP BY username) as avg_rating;";
        PreparedStatement p = connection.prepareStatement(query);
        p.setString(1, username);
        p.setString(2, username);
        ResultSet resultSet = p.executeQuery();
        double res = 0.0;
        while (resultSet.next()) {
            res = Double.parseDouble(resultSet.getString(1));
        }
        return res;
    }
}
