package service;

import model.Order;

import java.sql.SQLException;

public interface IOrderService {
    boolean save(Order order) throws SQLException;
}
