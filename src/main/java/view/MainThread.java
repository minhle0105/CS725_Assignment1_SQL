package view;

import model.Product;
import model.Review;
import model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static view.Main.*;

public class MainThread extends Thread {

    List<Product> products;
    List<User> users;

    int threadId;

    public MainThread(int threadId) throws SQLException {
        this.products = productController.findAll();
        this.users = userController.findAll();
        this.threadId = threadId;
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int value = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(value));
        }
        return sb.toString();
    }

    private int generateRandomInt(int high) {
        return random.nextInt(high);
    }

    private double generateRandomDouble(double high) {
        return high * random.nextDouble();
    }

    private boolean CreateAccount() {
        String username = generateRandomString(20);
        String password = generateRandomString(20);
        String firstname = generateRandomString(10);
        String lastname = generateRandomString(10);
        User user = new User(username, password, firstname, lastname);
        try {
            return userController.CreateAccount(user);
        }
        catch (Exception e) {
            return false;
        }

    }

    private boolean AddProduct() {
        String id = generateRandomString(10);
        String name = generateRandomString(10);
        String description = generateRandomString(100);
        double price = generateRandomDouble(50.0);
        int number_of_units = generateRandomInt(50);
        Product product = new Product(id, name, description, price, number_of_units);
        try {
            return productController.add(product);
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean UpdateStockLevel() {
        int index = generateRandomInt(products.size());
        int quantityToAdd = generateRandomInt(100);

        try {
            boolean res = productController.updateStockUnit(products.get(index), quantityToAdd);
            if (res) {
                products.get(index).setNumberOfUnits(products.get(index).getNumberOfUnits() + quantityToAdd);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean GetProductReview() {
        int index = generateRandomInt(products.size());
        try {
            productController.getReviewsByProduct(products.get(index).getId());
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean GetAverageRatingByUser() {
        int index = generateRandomInt(users.size());
        try {
            userController.getAverageRatingByUser(users.get(index).getUsername());
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean SubmitOrder() {
        int randomUserIndex = generateRandomInt(users.size());
        int numberOfProducts = products.size();
        List<String> productNames = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < numberOfProducts; j++) {
                int id = generateRandomInt(numberOfProducts);
                productNames.add(products.get(id).getName());
                int quantity = generateRandomInt(100);
                quantities.add(quantity);
            }
        }
        try {
            return userController.submitOrder(users.get(randomUserIndex).getUsername(), productNames, quantities);
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean SubmitReview() {
        int randomUserIndex = generateRandomInt(users.size());
        int randomProductIndex = generateRandomInt(products.size());
        int rating = generateRandomInt(5);
        String textReview = generateRandomString(500);
        Review review = new Review(users.get(randomUserIndex).getUsername(), products.get(randomProductIndex).getId(), new Date(Calendar.getInstance().getTime().getTime()), textReview, rating);
        try {
            return userController.submitReview(review);
        }
        catch (Exception e) {
            return false;
        }
    }
    // submit review

    @Override
    public void run() {
        int randomOperationIndex = generateRandomInt(operationIds.size());
        int randomOperationId = operationIds.get(randomOperationIndex);
        switch (randomOperationId) {
            case 1:
                CreateAccount();
                break;
            case 2:
                AddProduct();
                break;
            case 3:
                UpdateStockLevel();
                break;
            case 4:
                GetProductReview();
                break;
            case 5:
                GetAverageRatingByUser();
                break;
            case 6:
                SubmitOrder();
                break;
            case 7:
                SubmitReview();
                break;
        }
        System.out.println("Thread " + threadId + " performs action " + operationIds.get(randomOperationId));
    }
}
