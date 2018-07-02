package com.sty.object.box.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by tian on 2018/7/2.
 */

@Entity
public class Order {
    @Id
    private long id;

    private String name;

    private ToOne<Customer> customer;

    public Order(){}

    public Order(String name){
        this.name = name;
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

    public ToOne<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(ToOne<Customer> customer) {
        this.customer = customer;
    }
}
