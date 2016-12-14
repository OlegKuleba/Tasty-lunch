package org.itstep.ppjava13v2.kuleba.tastyLunch.services;

import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;

import java.util.List;

public interface UserService {

    void addOrUpdateUser(User user);

    void deleteUser(long id);

    boolean provideAccess(String email);

    void savePassword(String password, String email);

    boolean saveOrder(User user, Order order);

    boolean addUser(User user, String role);

    List<User> findUser(String param, String searchWord, String role);

    User editUser(User user, long id, String role);
}
