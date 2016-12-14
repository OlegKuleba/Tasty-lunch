package org.itstep.ppjava13v2.kuleba.tastyLunch.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.IntegerType;
import org.itstep.ppjava13v2.kuleba.tastyLunch.dao.DishDAO;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DishDAOImpl implements DishDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addDish(Dish dish) {
        sessionFactory.getCurrentSession().saveOrUpdate(dish);
    }

    @Override
    public void deleteDish(long id) {
        Dish dish = (Dish) sessionFactory.getCurrentSession().load(Dish.class, id);
        if (null != dish) {
            sessionFactory.getCurrentSession().delete(dish);
        }
    }

    @Override
    public List<Dish> getMenuList(Dish.DaysOfWeek dayOfTheWeek) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Dish as dish where dish.dayOfWeek = :dayOfTheWeek and dish.dateStartOfNextWeek > :date");
        query.setParameter("dayOfTheWeek", dayOfTheWeek);
        query.setDate("date", new Date());

        return query.list();
    }

    public void removeMenuForNextWeek() {
        Dish dish = new Dish();
        dish.setDateStartOfNextWeek();

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Dish as dish where dish.dateStartOfNextWeek =:date")
                .setDate("date", dish.getDateStartOfNextWeek());
        query.executeUpdate();
    }

    @Override
    public List<Dish> getDishesForNextWeek() {
        Session session = sessionFactory.getCurrentSession();
        List<String> ids = session.createSQLQuery("SELECT dish_id FROM tb_order_dish, tb_dish WHERE tb_dish.id = dish_id AND tb_dish.dateStartOfNextWeek > CURRENT_DATE ORDER BY tb_order_dish.order_id, tb_dish.id").list();
        Query query1 = session.createQuery("from Dish as dish where dish.id in (:ids) group by dish.name order by dish.id");
        List<Dish> dishList = query1.setParameterList("ids", ids, BigIntegerType.INSTANCE).list();
        return dishList;
    }

    @Override
    public List<Integer> getAmountOfEachDish() {
        Session session = sessionFactory.getCurrentSession();
        List<Integer> amountOfEachDish = session.createSQLQuery("SELECT COUNT(*) AS cnt FROM tb_order_dish, tb_dish WHERE dish_id = id AND dateStartOfNextWeek > CURDATE() GROUP BY tb_dish.name ORDER BY tb_dish.id")
                .addScalar("cnt", IntegerType.INSTANCE).list();
        return amountOfEachDish;
    }

}
