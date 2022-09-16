package view;

import controller.ProductController;
import controller.UserController;
import db_connection.MyJDBC;
import io.codearte.jfairy.Fairy;
import model.Product;
import model.Review;
import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class Main {

    static ProductController productController;
    static UserController userController;

    static StringBuilder alphabet = new StringBuilder();

    static Random random;

//    public static void main(String[] args) throws SQLException {
//        Scanner sc = new Scanner(System.in);
////        System.out.println("Enter SQL username: ");
////        String db_user = sc.nextLine();
////        System.out.println("Enter SQL password: ");
////        String db_password = sc.nextLine();
//        String db_user = args[0];
//        String db_password = args[1];
//        MyJDBC jdbc = new MyJDBC(db_user, db_password);
//        jdbc.connect();
//        Connection connection = jdbc.getConnection();
//        UserController userController = new UserController(connection);
//        ProductController productController = new ProductController(connection);
//        while (true) {
//            System.out.println("Enter choice: ");
//            int input = Integer.parseInt(sc.nextLine());
//            if (input == 0) {
//                break;
//            }
    // create account
//            if (input == 1) {
//                System.out.println("Enter username: ");
//                String username = sc.nextLine();
//                System.out.println("Enter password: ");
//                String password = sc.nextLine();
//                System.out.println("Enter firstname: ");
//                String firstname = sc.nextLine();
//                System.out.println("Enter lastname: ");
//                String lastname = sc.nextLine();
//                boolean res = userController.CreateAccount(username, password, firstname, lastname);
//                if (res) {
//                    System.out.println("Success");
//                }
//                else {
//                    System.out.println("Fail");
//                }
//            }
//            if (input == 2) {
//                List<User> users = userController.findAll();
//                for (User user : users) {
//                    System.out.println(user);
//                }
//            }
//            // place order
//            if (input == 3) {
//                System.out.println("Please log in");
//                System.out.println("Enter username: ");
//                String username = sc.nextLine();
//                System.out.println("Enter password: ");
//                String password = sc.nextLine();
//                boolean isAuthenticated = userController.login(username, password);
//                if (!isAuthenticated) {
//                    System.out.println("Invalid login credentials");
//                }
//                else {
//                    List<Product> products = productController.findAll();
//                    for (Product product : products) {
//                        System.out.println(product);
//                    }
//                    List<String> productNames = new ArrayList<>();
//                    List<Integer> quantityDemanded = new ArrayList<>();
//                    while (true) {
//                        System.out.println("Enter product name, or press enter to end: ");
//                        String name = sc.nextLine();
//                        if (name.length() == 0) break;
//                        System.out.println("Enter quantity: ");
//                        int quant = Integer.parseInt(sc.nextLine());
//                        productNames.add(name);
//                        quantityDemanded.add(quant);
//                    }
//                    boolean res = userController.submitOrder(username, productNames, quantityDemanded);
//                    if (res) {
//                        System.out.println("Order submitted successfully");
//                    }
//                    else {
//                        System.out.println("Fail to submit order");
//                    }
//                }
//            }
//            if (input == 4) {
//                System.out.println("Please log in");
//                System.out.println("Enter username: ");
//                String username = sc.nextLine();
//                System.out.println("Enter password: ");
//                String password = sc.nextLine();
//                boolean isAuthenticated = userController.login(username, password);
//                if (!isAuthenticated) {
//                    System.out.println("Invalid login credentials");
//                }
//                else {
//                    List<String[]> purchasedProducts = userController.getPurchasedProduct(username);
//                    for (String[] p : purchasedProducts) {
//                        System.out.println("product ID: " + p[0] + ", product name: " + p[1]);
//                    }
//                    System.out.println("Please enter the id of the product you want to write review: ");
//                    String id = sc.nextLine();
//                    boolean hasReviewed = userController.checkReviewByProductId(username, id);
//                    if (hasReviewed) {
//                        System.out.println("You have already reviewed this product!");
//                    }
//                    else {
//                        System.out.println("Please write your review (Maximum 1000 words): ");
//                        String reviewText = sc.nextLine();
//                        System.out.println("Please enter ratings (out of 5): ");
//                        int rating = Integer.parseInt(sc.nextLine());
//                        boolean res = userController.submitReview(new Review(username, id, new Date(Calendar.getInstance().getTime().getTime()), reviewText, rating));
//                        if (res) {
//                            System.out.println("Successfully submitted review");
//                        }
//                        else {
//                            System.out.println("Fail to submit reivew");
//                        }
//                    }
//                }
//            }
//            if (input == 5) {
//                System.out.println("Enter product ID: ");
//                String id = sc.nextLine();
//                System.out.println("Enter product name: ");
//                String name = sc.nextLine();
//                System.out.println("Enter product description: ");
//                String description = sc.nextLine();
//                System.out.println("Enter product price: ");
//                double price = Double.parseDouble(sc.nextLine());
//                System.out.println("Enter product quantity: ");
//                int quantity = Integer.parseInt(sc.nextLine());
//                Product p = new Product(id, name, description, price, quantity);
//                boolean res = productController.add(p);
//                if (res) {
//                    System.out.println("Successfully added " + id + ".");
//                }
//                else {
//                    System.out.println("Fail to add");
//                }
//            }
//            if (input == 6) {
//                System.out.println("Enter productId: ");
//                String id = sc.nextLine();
//                Product product = productController.getById(id);
//                if (product == null) {
//                    System.out.println("Fail");
//                }
//                else {
//                    System.out.println("Enter number of stock units to add: ");
//                    int quantity = Integer.parseInt(sc.nextLine());
//                    boolean res = productController.updateStockUnit(product, quantity);
//                    if (res) {
//                        System.out.println("DONE");
//                    }
//                    else {
//                        System.out.println("Fail");
//                    }
//                }
//
//            }
//            if (input ==  7) {
//                List<Product> products = productController.findAll();
//                System.out.println("All products in stock");
//                for (Product p : products) {
//                    System.out.println(p);
//                }
//                System.out.println("Enter product id to see all reviews: ");
//                String id = sc.nextLine();
//                List<Review> reviews = productController.getReviewsByProduct(id);
//                if (reviews.size() == 0) {
//                    System.out.println("No reviews, or invalid product id.");
//                }
//                for (Review review : reviews) {
//                    System.out.println(review);
//                    System.out.println("-------------------------");
//                }
//            }
//            if (input == 8) {
//                System.out.println("Enter username to find average rating: ");
//                String username = sc.nextLine();
//                double res = userController.getAverageRatingByUser(username);
//                System.out.println("Average rating user " + username + " has given is " + res);
//            }
//        }
//        sc.close();
//    }

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
            for (int j = 0; j < numberOfProducts; j++) {
                int id = generateRandomInt(numberOfProducts);
                productNames.add(products.get(id).getName());
                int quantity = generateRandomInt(30);
                quantities.add(quantity);
            }
            userController.submitOrder(users.get(randomUserIndex).getUsername(), productNames, quantities);
        }
    }

    public static void main(String[] args) throws SQLException {
        generateAlphabetString();
        random = new Random();
        String db_user = args[0];
        String db_password = args[1];
        MyJDBC jdbc = new MyJDBC(db_user, db_password);
        jdbc.connect();
        Connection connection = jdbc.getConnection();
        productController = new ProductController(connection);
        userController = new UserController(connection);
        List<Product> products = generateProducts(10);
        for (Product product : products) {
            productController.add(product);
        }
        List<User> users = generateUsers(10);
        for (User user : users) {
            userController.CreateAccount(user);
        }
        List<Review> reviews = generateReviews(users, products, 200);
        for (Review review : reviews) {
            userController.submitReview(review);
        }
        generateOrders(users, products, 100);
    }
}
