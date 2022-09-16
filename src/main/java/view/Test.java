package view;

import controller.ProductController;
import db_connection.MyJDBC;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public void test(String db_username, String db_password) throws SQLException, IOException {
        MyJDBC jdbc = new MyJDBC(db_username, db_password);
        jdbc.connect();
        Connection connection = jdbc.getConnection();
        ProductController productController = new ProductController(connection);
        PrintWriter out = new PrintWriter(new FileWriter("output.txt", true), true);
        for (int n = 1; n <= 10; n++) {
            System.out.println("Test with " + n + " threads.");
            long[] res = runThreads(n, productController);
            logOutputToFile(n, res, out);
            System.out.println("Test " + n + " done.");
        }
        out.close();
    }
    private long[] runThreads (int n, ProductController productController) throws SQLException {
        long[] res = new long[2];
        long numberOfOperations = 0;
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long endTime = System.currentTimeMillis() + 30*1000;

        while (System.currentTimeMillis() < endTime) {
            try {
                for (int i = 0; i < n; i++) {
                    executor.execute(new MainThread(i + 1));
                    numberOfOperations += 1;
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        executor.shutdown(); // once you are done with ExecutorService
        res[0] = numberOfOperations;
        double percentageOfProductsLessThan0StockUnits = productController.calculatePercentageWithStockLevelLessThan0();
        res[1] = (long) percentageOfProductsLessThan0StockUnits;
        return res;
    }

    private void logOutputToFile(int n, long[] res, PrintWriter out) {
        String testOutput = "Test " + n + " threads: \n" +
                "Operation count: " + res[0] + "\n" +
                "Percentage of products with stock level < 0: " + res[1] + "\n" +
                "-----------------\n";
        out.write(testOutput);
    }
}
