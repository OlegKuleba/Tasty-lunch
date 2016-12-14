package org.itstep.ppjava13v2.kuleba.tastyLunch.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private Date dateStartOfWeek;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /*@OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")*/

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_order_dish", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private List<Dish> dishList;

    public Order() {
    }

    public Order(Date dateStartOfWeek, ArrayList<Dish> dishList, User user) {
        this.dateStartOfWeek = dateStartOfWeek;
        this.dishList = dishList;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateStartOfWeek() {
        return dateStartOfWeek;
    }

    public void setDateStartOfWeek(Date dateStartOfWeek) {
        this.dateStartOfWeek = dateStartOfWeek;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", dateStartOfWeek=" + dateStartOfWeek +
                ", user=" + user +
                ", dishList=" + dishList +
                '}';
    }
}
