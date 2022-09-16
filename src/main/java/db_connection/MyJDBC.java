package db_connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyJDBC implements DBConnection {

    String url;
    String db_user;
    String db_password;

    Connection connection;

    public MyJDBC(String db_user, String db_password) {
        this.url = "jdbc:mysql://localhost:3306/CS725_Assignment1_Fake";
        this.db_user = db_user;
        this.db_password = db_password;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(this.url, this.db_user, this.db_password);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }
        catch (Exception e) {
            System.out.println("Fail to connect to database");
        }
    }
}
