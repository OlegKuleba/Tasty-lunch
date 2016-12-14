package org.itstep.ppjava13v2.kuleba.tastyLunch.services.impl;

import org.hibernate.Criteria;
import org.itstep.ppjava13v2.kuleba.tastyLunch.dao.DishDAO;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDAO dishDAO;

    @Override
    @Transactional
    public void addDish(Dish dish) {
        dishDAO.addDish(dish);
    }

    @Override
    @Transactional
    public void deleteDish(long id) {
        dishDAO.deleteDish(id);
    }

    @Override
    @Transactional
    public List<Dish> getMenuList(Dish.DaysOfWeek dayOfTheWeek) {
        return dishDAO.getMenuList(dayOfTheWeek);
    }

    @Override
    @Transactional
    public void removeMenuForNextWeek() {
        dishDAO.removeMenuForNextWeek();
    }

    @Override
    @Transactional
    public List<Dish> getDishesForNextWeek() {
        return dishDAO.getDishesForNextWeek();
    }

    @Override
    @Transactional
    public List<Integer> getAmountOfEachDish() {
        return dishDAO.getAmountOfEachDish();
    }
}
