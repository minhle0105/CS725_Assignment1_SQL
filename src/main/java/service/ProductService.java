package service;

import model.Product;
import model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService{

    Connection connection;

    public ProductService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String query = "SELECT * FROM Products";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product(resultSet.getString("idProducts"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Double.parseDouble(resultSet.getString("price")) ,
                    Integer.parseInt(resultSet.getString("number_of_units")));
            products.add(product);
        }
        return products;
    }

    @Override
    public boolean addNew(Product p) throws SQLException {
        String id = p.getId();
        String name = p.getName();
        String description = p.getDescription();
        double price = p.getPrice();
        int stock = p.getNumberOfUnits();
        String insertQuery = "INSERT INTO Products VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, description);
        preparedStatement.setDouble(4, price);
        preparedStatement.setInt(5, stock);
        preparedStatement.execute();
        connection.commit();
        Product product = getById(id);
        if (product == null) {
            connection.rollback();
            return false;
        }
        return true;
    }

    @Override
    public Product getById(String id) throws SQLException {
        String query = "SELECT * FROM Products WHERE idProducts = ?";
        PreparedStatement p = connection.prepareStatement(query);
        p.setString(1, id);
        ResultSet resultSet = p.executeQuery();
        if (resultSet.next()) {
            Product product = new Product(resultSet.getString("idProducts"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    Double.parseDouble(resultSet.getString("price")),
                    Integer.parseInt(resultSet.getString("number_of_units")));
            return product;
        }
        return null;
    }

    @Override
    public boolean deleteById(String id) throws SQLException {
        String deleteQuery = "DELETE FROM Products WHERE idProducts = ?";
        PreparedStatement p = connection.prepareStatement(deleteQuery);
        p.setString(1, id);
        p.execute();
        connection.commit();
        Product product = getById(id);
        if (product != null) {
            connection.rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateById(Product product, int countToAdd) throws SQLException {
        int newQuantity = product.getNumberOfUnits() + countToAdd;
        Product newProduct = new Product(product.getId(), product.getName(),
                product.getDescription(), product.getPrice(), newQuantity);
        deleteById(product.getId());
        addNew(newProduct);
        Product check = getById(newProduct.getId());
        if (check != null) {
            return true;
        }
        else {
            connection.rollback();
            return false;
        }
    }

    @Override
    public List<Review> getReviewByProduct(String id) throws SQLException {
        String query = "SELECT * FROM Review WHERE product_id = ?;";
        PreparedStatement p = connection.prepareStatement(query);
        p.setString(1, id);
        ResultSet resultSet = p.executeQuery();
        List<Review> reviews = new ArrayList<>();
        while (resultSet.next()) {
            Review review = new Review(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5),
                    resultSet.getInt(6)
                    );
            reviews.add(review);
        }
        return reviews;

    }


}
