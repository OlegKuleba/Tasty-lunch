package org.itstep.ppjava13v2.kuleba.tastyLunch.dao;

import org.hibernate.Criteria;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;

import java.util.List;

public interface DishDAO {

    void addDish(Dish dish);

    void deleteDish(long id);

    List getMenuList(Dish.DaysOfWeek dayOfTheWeek);

    void removeMenuForNextWeek();

    List<Dish> getDishesForNextWeek();

    List<Integer> getAmountOfEachDish();
}
