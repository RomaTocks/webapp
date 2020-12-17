package com.tocks.webapp.Models;

import javax.persistence.*;

@Entity
@Table(name = "productorder")
public class ProductOrder
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productorderid;
    @ManyToOne
    @JoinColumn(name = "orders_ordersid")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_productid")
    private Product products;

    public ProductOrder(Long productorderid, Order order, Product products)
    {
        this.productorderid = productorderid;
        this.order = order;
        this.products = products;
    }

    public ProductOrder(Order order, Product products)
    {
        this.order = order;
        this.products = products;
    }

    public ProductOrder(Product products)
    {
        this.products = products;
    }

    public ProductOrder(Order order)
    {
        this.order = order;
    }

    public ProductOrder(Long productorderid, Order order)
    {
        this.productorderid = productorderid;
        this.order = order;
    }

    public ProductOrder(Long productorderid)
    {
        this.productorderid = productorderid;
    }

    public ProductOrder()
    {
    }

    public Long getProductorderid()
    {
        return productorderid;
    }

    public void setProductorderid(Long productorderid)
    {
        this.productorderid = productorderid;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public Product getProducts()
    {
        return products;
    }

    public void setProducts(Product products)
    {
        this.products = products;
    }
}
