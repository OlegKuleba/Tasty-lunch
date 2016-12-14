package org.itstep.ppjava13v2.kuleba.tastyLunch.controllers;

import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Order;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.DishService;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.OrderService;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    DishService dishService;

    @Autowired
    OrderService orderService;

    private void isAdmin(ModelAndView mv) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        Iterator<?> iterator = user.getAuthorities().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().toString().equals("ROLE_ADMIN")) {
                mv.addObject("adminButton", "Go to admin panel");
            }
        }
    }

    @RequestMapping(value = "/order")
    public ModelAndView order() {
        ModelAndView mv = new ModelAndView("order");

        isAdmin(mv);

        ArrayList<Dish> mondayMenuFirstDish = new ArrayList<>();
        ArrayList<Dish> mondayMenuSecondDish = new ArrayList<>();
        ArrayList<Dish> mondayMenuSalad = new ArrayList<>();

        ArrayList<Dish> tuesdayMenuFirstDish = new ArrayList<>();
        ArrayList<Dish> tuesdayMenuSecondDish = new ArrayList<>();
        ArrayList<Dish> tuesdayMenuSalad = new ArrayList<>();

        ArrayList<Dish> wednesdayMenuFirstDish = new ArrayList<>();
        ArrayList<Dish> wednesdayMenuSecondDish = new ArrayList<>();
        ArrayList<Dish> wednesdayMenuSalad = new ArrayList<>();

        ArrayList<Dish> thursdayMenuFirstDish = new ArrayList<>();
        ArrayList<Dish> thursdayMenuSecondDish = new ArrayList<>();
        ArrayList<Dish> thursdayMenuSalad = new ArrayList<>();

        ArrayList<Dish> fridayMenuFirstDish = new ArrayList<>();
        ArrayList<Dish> fridayMenuSecondDish = new ArrayList<>();
        ArrayList<Dish> fridayMenuSalad = new ArrayList<>();

        List<Dish> mondayMenuDishes = dishService.getMenuList(Dish.DaysOfWeek.MONDAY);
        List<Dish> tuesdayMenuDishes = dishService.getMenuList(Dish.DaysOfWeek.TUESDAY);
        List<Dish> wednesdayMenuDishes = dishService.getMenuList(Dish.DaysOfWeek.WEDNESDAY);
        List<Dish> thursdayMenuDishes = dishService.getMenuList(Dish.DaysOfWeek.THURSDAY);
        List<Dish> fridayMenuDishes = dishService.getMenuList(Dish.DaysOfWeek.FRIDAY);

        separateDishByCategory(mondayMenuDishes, mondayMenuFirstDish, mondayMenuSecondDish, mondayMenuSalad);
        separateDishByCategory(tuesdayMenuDishes, tuesdayMenuFirstDish, tuesdayMenuSecondDish, tuesdayMenuSalad);
        separateDishByCategory(wednesdayMenuDishes, wednesdayMenuFirstDish, wednesdayMenuSecondDish, wednesdayMenuSalad);
        separateDishByCategory(thursdayMenuDishes, thursdayMenuFirstDish, thursdayMenuSecondDish, thursdayMenuSalad);
        separateDishByCategory(fridayMenuDishes, fridayMenuFirstDish, fridayMenuSecondDish, fridayMenuSalad);

        mv.addObject("mondayMenuFirstDish", mondayMenuFirstDish);
        mv.addObject("mondayMenuSecondDish", mondayMenuSecondDish);
        mv.addObject("mondayMenuSalad", mondayMenuSalad);

        mv.addObject("tuesdayMenuFirstDish", tuesdayMenuFirstDish);
        mv.addObject("tuesdayMenuSecondDish", tuesdayMenuSecondDish);
        mv.addObject("tuesdayMenuSalad", tuesdayMenuSalad);

        mv.addObject("wednesdayMenuFirstDish", wednesdayMenuFirstDish);
        mv.addObject("wednesdayMenuSecondDish", wednesdayMenuSecondDish);
        mv.addObject("wednesdayMenuSalad", wednesdayMenuSalad);

        mv.addObject("thursdayMenuFirstDish", thursdayMenuFirstDish);
        mv.addObject("thursdayMenuSecondDish", thursdayMenuSecondDish);
        mv.addObject("thursdayMenuSalad", thursdayMenuSalad);

        mv.addObject("fridayMenuFirstDish", fridayMenuFirstDish);
        mv.addObject("fridayMenuSecondDish", fridayMenuSecondDish);
        mv.addObject("fridayMenuSalad", fridayMenuSalad);

        return mv;
    }

    private void separateDishByCategory(List<Dish> list, ArrayList<Dish> firstDish, ArrayList<Dish> secondDish, ArrayList<Dish> saladDish) {
        for (Dish dish : list) {
            if (dish.getCategory().matches("^1.*|[Пп]ервое.*")) {
                firstDish.add(dish);
            }
            if (dish.getCategory().matches("^2.*|[Вв]торое.*")) {
                secondDish.add(dish);
            }
            if (dish.getCategory().matches("^3.*|[Сс]алат.*")) {
                saladDish.add(dish);
            }
        }
    }

    @RequestMapping(value = "/order'sHistory")
    public ModelAndView ordersHistory(Principal principal) {
        ModelAndView mv = new ModelAndView("order'sHistory");
        isAdmin(mv);
        User user = new User();
        user.setLoginEmail(principal.getName());

        List<Order> orderList = orderService.getUsersOrders(user);
        sortDishesById(orderList);
        mv.addObject("orderList", orderList);
        return mv;
    }

    private void sortDishesById(List<Order> list) {
        for (Order order : list) {
            Collections.sort(order.getDishList(), Dish.COMPARE_BY_ID);
        }
    }

    @RequestMapping(value = "/saveDishes", method = RequestMethod.POST)
    public String saveDishes(@RequestParam("array") String[] array) {
        return array.toString();
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveOrder(@RequestParam("array") String array, Principal principal) {

        String[] arr = array.split(",");

        User user = new User();
        user.setLoginEmail(principal.getName());
        List<Dish> dishList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        Dish dish = null;
        for (String elem : arr) {
            stringBuilder.append(elem).append(",");
            dish = new Dish();
            dish.setId(Integer.parseInt(elem));
            dishList.add(dish);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        dish.setDateStartOfNextWeek();
        Order order = new Order();
        order.setUser(user);
        order.setDateStartOfWeek(dish.getDateStartOfNextWeek());
        order.setDishList(dishList);

        orderService.addOrder(order, user.getLoginEmail());

        return stringBuilder.toString();
    }

}
