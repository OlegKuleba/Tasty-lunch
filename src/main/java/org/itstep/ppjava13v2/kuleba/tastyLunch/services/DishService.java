package org.itstep.ppjava13v2.kuleba.tastyLunch.services;

import org.hibernate.Criteria;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;

import java.util.List;

public interface DishService {

    void addDish(Dish dish);

    void deleteDish(long id);

    List<Dish> getMenuList(Dish.DaysOfWeek dayOfTheWeek);

    void removeMenuForNextWeek();

    List<Dish> getDishesForNextWeek();

    List<Integer> getAmountOfEachDish();
}
