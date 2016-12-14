package org.itstep.ppjava13v2.kuleba.tastyLunch.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.itstep.ppjava13v2.kuleba.tastyLunch.dao.OrderDAO;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addOrder(Order order, String email) {
        Session session = sessionFactory.getCurrentSession();
        Order previousOrder =
                (Order) session.createQuery("from Order as order where order.dateStartOfWeek =:date and order.user.loginEmail =:email")
                        .setDate("date", order.getDateStartOfWeek()).setString("email", email).uniqueResult();
        User user = (User) session.createQuery("from User as user where user.loginEmail =:email")
                .setString("email", email).uniqueResult();

        if (previousOrder != null) {
            List<Dish> previousDishList = previousOrder.getDishList();
            List<Dish> currentDishList = order.getDishList();
            List<Dish> allDishList = (List<Dish>) session.createQuery("from Dish as dish where dish.dateStartOfNextWeek = :date")
                    .setDate("date", order.getDateStartOfWeek()).list();
            List<Dish> dishListForSaveToDB = new ArrayList<>();
            for (Dish dish : currentDishList) {
                for (Dish dishFromDB : allDishList) {
                    if (dish.getId() == dishFromDB.getId()) {
                        dishListForSaveToDB.add(dishFromDB);
                    }
                }
            }
            for (Dish dishFromSaveToDBList : dishListForSaveToDB) {
                for (int i = 0; i < previousDishList.size(); i++) {
                    if (previousDishList.get(i).getDayOfWeek() == dishFromSaveToDBList.getDayOfWeek()) {
                        if (previousDishList.contains(previousDishList.get(i))) {
                            previousDishList.remove(previousDishList.get(i));
                            i--;
                        }
                    }
                }
            }
            order.setDishList(null);
            for (Dish dish : previousDishList) {
                dishListForSaveToDB.add(dish);
            }
            deleteOrder(previousOrder.getId());
            order.setDishList(dishListForSaveToDB);
        }

        user.addOrder(order);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteOrder(long id) {
        Order order = (Order) sessionFactory.getCurrentSession().load(Order.class, id);
        if (null != order) {
            sessionFactory.getCurrentSession().delete(order);
        }
    }

    @Override
    public List<Order> getUsersOrders(User user) {
        Session session = sessionFactory.getCurrentSession();
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -2);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return session.createQuery("from Order as order where order.user.loginEmail =:email " +
                "and order.dateStartOfWeek > :date order by order.dateStartOfWeek desc")
                .setString("email", user.getLoginEmail()).setDate("date", calendar.getTime()).list();
    }

    @Override
    public List<Order> getOrdersForNextWeek() {
        return sessionFactory.getCurrentSession().createQuery("from Order as order where order.dateStartOfWeek > CURRENT_DATE").list();
    }


}
