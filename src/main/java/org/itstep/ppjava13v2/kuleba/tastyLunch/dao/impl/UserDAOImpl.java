package org.itstep.ppjava13v2.kuleba.tastyLunch.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.itstep.ppjava13v2.kuleba.tastyLunch.dao.UserDAO;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addOrUpdateUser(User user) {
        Session session = sessionFactory.getCurrentSession();

        User userTMP = (User) session.createQuery("from User as user where user.loginEmail =:email")
                .setString("email", user.getLoginEmail()).uniqueResult();

        if (userTMP == null) {
            session.saveOrUpdate(user);
        }
    }

    @Override
    public void deleteUser(long id) {
        User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
        if (null != user) {
            Session session = sessionFactory.getCurrentSession();
            session.delete(user);
            session.createSQLQuery("DELETE FROM authorities WHERE username = :email")
                    .setParameter("email", user.getLoginEmail()).executeUpdate();
            session.createSQLQuery("DELETE FROM users WHERE username = :email")
                    .setParameter("email", user.getLoginEmail()).executeUpdate();
        }
    }

    @Override
    public boolean provideAccess(String email) {
        Session session = sessionFactory.getCurrentSession();
        boolean enabled;
        try {
            enabled = (boolean) session.createSQLQuery("SELECT enabled FROM users WHERE users.username = :email")
                    .setParameter("email", email).uniqueResult();
            if (!enabled) {
                Query query = session.createSQLQuery("UPDATE users SET users.enabled = TRUE WHERE users.username = :email")
                        .setParameter("email", email);
                int i = query.executeUpdate();
                if (i == 1) {
                    enabled = true;
                }
            }
        } catch (NullPointerException e) {
            enabled = false;
        }
        return enabled;
    }

    @Override
    public void savePassword(String password, String email) {
        Query query = sessionFactory.getCurrentSession()
                .createSQLQuery("UPDATE users SET users.password = :password WHERE users.username = :email")
                .setParameter("password", password).setParameter("email", email);
        int i = query.executeUpdate();
    }

    @Override
    public boolean saveOrder(User user, Order order) {

        Session session = sessionFactory.getCurrentSession();
        List<Order> orderList = session.createQuery("from Order as order where order.user = :user")
                .setEntity("user", user).list();
        orderList.add(order);
        user.setOrderList(orderList);
        session.saveOrUpdate(user);

        return true;
    }

    @Override
    public boolean addUser(User user, String role) {
        Session session = sessionFactory.getCurrentSession();
        boolean isSave;
        try {
            session.createSQLQuery("INSERT INTO users VALUES (:name, :password, 0)")
                    .setParameter("name", user.getLoginEmail()).setParameter("password", 1234).executeUpdate();

            session.createSQLQuery("INSERT INTO authorities VALUES (:name, :authority)")
                    .setParameter("name", user.getLoginEmail()).setParameter("authority", role).executeUpdate();
            isSave = true;

            if (!user.getLastName().equals("") || !user.getPhone().equals("")) {
                session.saveOrUpdate(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSave = false;
        }
        return isSave;
    }

    @Override
    public List<User> findUser(String param, String searchWord, String role) {

        Session session = sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(User.class);

        if (!searchWord.equals("")) {

            switch (param) {
                case "email":
                    criteria.add(Restrictions.eq("loginEmail", "%" + searchWord + "%"));
                    break;
                case "phone":
                    criteria.add(Restrictions.like("phone", "%" + searchWord + "%"));
                    break;
                case "lastName":
                    criteria.add(Restrictions.like("lastName", "%" + searchWord + "%"));
                    break;
            }
        }
        criteria.addOrder(org.hibernate.criterion.Order.asc("lastName"));
        return criteria.list();
    }

    @Override
    public User editUser(User user, long id, String role) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("update User set name =:name, lastName =:lastName," +
                "phone =:phone, groupId =:groupId where id =:id");
        query.setParameter("name", user.getName()).setParameter("lastName", user.getLastName());
        query.setParameter("phone", user.getPhone()).setParameter("groupId", user.getGroupId());
        query.setParameter("id", id);
        int i = query.executeUpdate();
        User userFromDB = new User();
        if (i > 0) {
            userFromDB = (User) session.get(User.class, id);
        }
        return userFromDB;
    }
}
