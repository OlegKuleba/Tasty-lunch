package org.itstep.ppjava13v2.kuleba.tastyLunch.services;

import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;

import java.util.List;

public interface OrderService {

    void addOrder(Order order, String email);

    void deleteOrder(long id);

    List<Order> getUsersOrders(User user);

    List<Order> getOrdersForNextWeek();

}
