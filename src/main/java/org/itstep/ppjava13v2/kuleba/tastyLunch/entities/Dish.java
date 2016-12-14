package org.itstep.ppjava13v2.kuleba.tastyLunch.entities;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

@Entity
@Table(name = "tb_dish", indexes = {@Index(unique = true, columnList = "name, dateStartOfNextWeek")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "dateStartOfNextWeek"})})
//indexes = {@Index(name = "abc", columnList = {"name", "dateStartOfNextWeek"})}
public class Dish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private BigDecimal cost;

    @Column
    private String portion;

    @Column
    private String category;

    @Column
    private String description;

    public enum DaysOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY;

        DaysOfWeek() {
        }
    }

    @Enumerated(EnumType.STRING)
    private DaysOfWeek dayOfWeek;

    private Date dateStartOfNextWeek;

    public Dish() {
    }

    public Dish(String name, BigDecimal cost, DaysOfWeek dayOfWeek, String portion, String category, String description) {
        this.name = name;
        this.cost = cost;
        this.dayOfWeek = dayOfWeek;
        this.portion = portion;
        this.category = category;
        this.description = description;
    }

    public static final Comparator<Dish> COMPARE_BY_ID = new Comparator<Dish>() {
        @Override
        public int compare(Dish o1, Dish o2) {
            return Long.compare(o1.getId(), o2.getId());
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public DaysOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DaysOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStartOfNextWeek() {
        return dateStartOfNextWeek;
    }

    public void setDateStartOfNextWeek() {

        Calendar calendar = Calendar.getInstance();

        do {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        } while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        this.dateStartOfNextWeek = calendar.getTime();
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", portion='" + portion + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", dateStartOfNextWeek=" + dateFormat.format(dateStartOfNextWeek) +
                '}';
    }
}
