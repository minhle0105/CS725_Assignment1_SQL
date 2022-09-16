package model;

import java.sql.Date;
import java.util.List;

public class Order {
    List<CheckoutItem> productsPurchased;
    Date date;
    String username;

    public List<CheckoutItem> getProductsPurchased() {
        return this.productsPurchased;
    }

    public String getId() {
        return username + "," + System.currentTimeMillis();
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public Order(List<CheckoutItem> productsPurchased, Date date, String username) {
        this.productsPurchased = productsPurchased;
        this.date = date;
        this.username = username;
    }
}
