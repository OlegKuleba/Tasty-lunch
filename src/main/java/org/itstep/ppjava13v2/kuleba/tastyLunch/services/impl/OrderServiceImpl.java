package org.itstep.ppjava13v2.kuleba.tastyLunch.services.impl;

import org.itstep.ppjava13v2.kuleba.tastyLunch.dao.OrderDAO;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    @Transactional
    public void addOrder(Order order, String email) {
        orderDAO.addOrder(order, email);
    }

    @Override
    @Transactional
    public void deleteOrder(long id) {
        orderDAO.deleteOrder(id);
    }

    @Override
    @Transactional
    public List<Order> getUsersOrders(User user) {
        return orderDAO.getUsersOrders(user);
    }

    @Override
    @Transactional
    public List<Order> getOrdersForNextWeek() {
        return orderDAO.getOrdersForNextWeek();
    }

}
