package org.itstep.ppjava13v2.kuleba.tastyLunch.services.impl;


import org.itstep.ppjava13v2.kuleba.tastyLunch.dao.UserDAO;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void addOrUpdateUser(User user) {
        userDAO.addOrUpdateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public boolean provideAccess(String email) {
        return userDAO.provideAccess(email);
    }

    @Override
    @Transactional
    public void savePassword(String password, String email) {
        userDAO.savePassword(password, email);
    }

    @Override
    @Transactional
    public boolean saveOrder(User user, Order order) {
        return userDAO.saveOrder(user, order);
    }

    @Override
    @Transactional
    public boolean addUser(User user, String role) {
        return userDAO.addUser(user, role);
    }

    @Override
    @Transactional
    public List<User> findUser(String param, String searchWord, String role) {
        return userDAO.findUser(param, searchWord, role);
    }

    @Override
    @Transactional
    public User editUser(User user, long id, String role) {
        return userDAO.editUser(user, id, role);
    }


}
