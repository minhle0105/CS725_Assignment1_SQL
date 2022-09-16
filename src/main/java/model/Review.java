package model;

import java.sql.Date;

public class Review {
    String username;
    String product_id;
    Date date;
    String text;
    int rating;

    public Review(String username, String product_id, Date date, String text, int rating) {
        this.username = username;
        this.product_id = product_id;
        this.date = date;
        this.text = text;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review by: " + username + '\n' +
                "Product_id: " + product_id + '\n' +
                "Date: " + date.toString() + '\n' +
                "Text: " + text + '\n' +
                "Rating: " + rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
