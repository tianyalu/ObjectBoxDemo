package com.sty.object.box.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by tian on 2018/7/2.
 */

@Entity
public class Customer {

    @Id
    private long id;
    private String name;
    private int age;
    @Backlink
    private ToMany<Order> order;

    public Customer(){}

    public Customer(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Customer(long id, String name, int age){
        this(name, age);
        this.id = id;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ToMany<Order> getOrder() {
        return order;
    }

    public void setOrder(ToMany<Order> order) {
        this.order = order;
    }

}
