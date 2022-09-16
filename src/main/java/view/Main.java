package view;

import controller.ProductController;
import controller.UserController;
import db_connection.MyJDBC;
import model.Product;
import model.Review;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Thread {

    static List<Integer> operationIds;

    static ProductController productController;
    static UserController userController;

    static StringBuilder alphabet;

    static Random random;

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int value = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(value));
        }
        return sb.toString();
    }

    private static int generateRandomInt(int high) {
        return random.nextInt(high);
    }

    private static double generateRandomDouble(double high) {
        return high * random.nextDouble();
    }

    private static void generateAlphabetString() {
        alphabet = new StringBuilder();
        for (int i = 65; i < 90; i++) {
            alphabet.append((char) i);
        }
        for (int i = 97; i < 122; i++) {
            alphabet.append((char) i);
        }
    }
    private static List<Product> generateProducts(int n) {
        // 65-90
        // 97-122
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String id = generateRandomString(10);
            String name = generateRandomString(10);
            String description = generateRandomString(100);
            double price = generateRandomDouble(50.0);
            int number_of_units = generateRandomInt(50);
            Product product = new Product(id, name, description, price, number_of_units);
            products.add(product);
        }
        return products;
    }

    private static List<User> generateUsers(int n) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String username = generateRandomString(20);
            String password = generateRandomString(20);
            String firstname = generateRandomString(10);
            String lastname = generateRandomString(10);
            User user = new User(username, password, firstname, lastname);
            users.add(user);
        }
        return users;
    }

    private static List<Review> generateReviews(List<User> users, List<Product> products, int n) {
        List<Review> reviews = new ArrayList<>();
        int numberOfUsers = users.size();
        int numberOfProducts = products.size();
        for (int i = 0; i < n; i++) {
            String textReview = generateRandomString(500);
            int rating = generateRandomInt(5);
            int randomUserIndex = generateRandomInt(numberOfUsers);
            int randomProductIndex = generateRandomInt(numberOfProducts);
            Review review = new Review(users.get(randomUserIndex).getUsername(), products.get(randomProductIndex).getId(), new Date(Calendar.getInstance().getTime().getTime()), textReview, rating);
            reviews.add(review);
        }
        return reviews;
    }

    private static void generateOrders(List<User> users, List<Product> products, int n) throws SQLException {
        int numberOfUsers = users.size();
        int numberOfProducts = products.size();
        for (int i = 0; i < n; i++) {
            List<String> productNames = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            int randomUserIndex = generateRandomInt(numberOfUsers);
            for (int j = 0; j < 10; j++) {
                int id = generateRandomInt(numberOfProducts);
                productNames.add(products.get(id).getName());
                int quantity = generateRandomInt(10);
                quantities.add(quantity);
            }
            userController.submitOrder(users.get(randomUserIndex).getUsername(), productNames, quantities);
        }
    }

    private static void generateOperationIdsByProbability() {
        for (int i = 0; i < 3; i++) {
            operationIds.add(1);
        }
        for (int i = 0; i < 2; i++) {
            operationIds.add(2);
        }
        for (int i = 0; i < 10; i++) {
            operationIds.add(3);
        }
        for (int i = 0; i < 65; i++) {
            operationIds.add(4);
        }
        for (int i = 0; i < 5; i++) {
            operationIds.add(5);
        }
        for (int i = 0; i < 10; i++) {
            operationIds.add(6);
        }
        for (int i = 0; i < 5; i++) {
            operationIds.add(7);
        }
    }
    private static void initializeDatabaseRecords() throws SQLException {
        List<Product> products = generateProducts(1000);
        for (Product product : products) {
            productController.add(product);
        }
        List<User> users = generateUsers(1000);
        for (User user : users) {
            userController.CreateAccount(user);
        }
        List<Review> reviews = generateReviews(users, products, 20000);
        for (Review review : reviews) {
            userController.submitReview(review);
        }
        generateOrders(users, products, 10000);
    }

    private static double[] runThreads (int n) throws SQLException {
        double[] res = new double[2];
        int numberOfOperations = 0;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long endTime = System.currentTimeMillis() + 60*1000;

        while (System.currentTimeMillis() < endTime) {
            try {
                for (int i = 0; i < n; i++) {
                    executor.execute(new MainThread(i + 1));
                }
                numberOfOperations += n;
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        executor.shutdown(); // once you are done with ExecutorService
        res[0] = numberOfOperations;
        double percentageOfProductsLessThan0StockUnits = productController.calculatePercentageWithStockLevelLessThan0();
        res[1] = percentageOfProductsLessThan0StockUnits;
        return res;
    }

    public static void main(String[] args) throws SQLException, IOException {
        String db_user = args[0];
        String db_password = args[1];
        MyJDBC jdbc = new MyJDBC(db_user, db_password);
        jdbc.connect();
        Connection connection = jdbc.getConnection();

        generateAlphabetString();
        random = new Random();
        operationIds = new ArrayList<>();
        generateOperationIdsByProbability();
        productController = new ProductController(connection);
        userController = new UserController(connection);

//        initializeDatabaseRecords();


//        Map<Integer, double[]> map = new HashMap<>();
            PrintWriter out = new PrintWriter(new FileWriter("output.txt", true), true);
        for (int n = 1; n <= 10; n++) {
            System.out.println("Test with " + n + " threads.");
            double[] res = runThreads(n);
            String testOutput = "Test " + n + " threads: \n" +
            "Operation count: " + res[0] + "\n" +
            "Percentage of products with stock level < 0: " + res[1] + "\n" +
                    "-----------------\n";
            out.write(testOutput);
            System.out.println("Test " + n + " done.");
        }
//        logOutput(map);
        out.close();
    }

    private static void logOutput(Map<Integer, double[]> map) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("output.txt", true), true);
        for (Integer key : map.keySet()) {
            out.write(key + "->" + Arrays.toString(map.get(key)) + "\n");
        }
        out.close();
    }
}
