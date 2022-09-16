package service;

import model.CheckoutItem;
import model.Order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService {

    Connection connection;

    public OrderService(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean save(Order order) throws SQLException {
        String generateOrderQuery = "INSERT INTO Orders VALUES (?, ?, ?);";
        String orderId = order.getId();
        String username = order.getUsername();
        Date date = order.getDate();
        PreparedStatement p = connection.prepareStatement(generateOrderQuery);
        p.setString(1, orderId);
        p.setString(2, username);
        p.setDate(3, date);
        p.execute();
        connection.commit();
        List<CheckoutItem> checkoutItems = order.getProductsPurchased();
        String addToOrderDetail = "INSERT INTO Order_Details (`order_id`, `product_id`, `quantity`) VALUES (?, ?, ?);";
        for (CheckoutItem item : checkoutItems) {
            p = connection.prepareStatement(addToOrderDetail);
            p.setString(1, orderId);
            p.setString(2, item.getProductId());
            p.setInt(3, item.getQuantity());
            p.execute();
        }
        connection.commit();
        return true;
    }
}
