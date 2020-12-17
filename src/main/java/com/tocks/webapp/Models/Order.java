package com.tocks.webapp.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersid;
    @ManyToOne
    @JoinColumn(name = "user_userid")
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductOrder> productOrders = new ArrayList<>();


    public Order()
    {
    }

    public Order(Long ordersid, User user)
    {
        this.ordersid = ordersid;
        this.user = user;
    }

    public Long getOrdersid()
    {
        return ordersid;
    }

    public void setOrdersid(Long ordersid)
    {
        this.ordersid = ordersid;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
