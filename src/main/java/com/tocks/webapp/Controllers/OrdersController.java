package com.tocks.webapp.Controllers;

import com.tocks.webapp.Models.Order;
import com.tocks.webapp.Models.Product;
import com.tocks.webapp.Models.ProductOrder;
import com.tocks.webapp.Models.User;
import com.tocks.webapp.Repositories.OrderRepository;
import com.tocks.webapp.Repositories.ProductOrderRepository;
import com.tocks.webapp.Repositories.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrdersController
{
    private final OrderRepository orderRepository;
    private final UsersRepository usersRepository;
    private final ProductOrderRepository productOrderRepository;

    public OrdersController(OrderRepository orderRepository, UsersRepository usersRepository, ProductOrderRepository productOrderRepository)
    {
        this.orderRepository = orderRepository;
        this.usersRepository = usersRepository;
        this.productOrderRepository = productOrderRepository;
    }
    @PostMapping
    public Order createOrder(@RequestBody Order order)
    {
        return orderRepository.save(order);
    }
    @GetMapping
    public List<Order> getOrders()
    {
        return orderRepository.getAll();
    }

    @GetMapping("{id}")
    public List<Order> getCurrentOrders(@PathVariable Integer id)
    {
        return orderRepository.findAllByUser_Userid(id);
    }
    @GetMapping("/products")
    public List<ProductOrder> getProductOrders()
    {
        return productOrderRepository.findAll();
    }
    @PostMapping("/products")
    public void createProductOrder(@RequestBody ProductOrder productOrder)
    {
        productOrderRepository.save(productOrder);
    }
    @GetMapping("/products/{id}")
    public List<ProductOrder> getProductOrdersById(@PathVariable Integer id)
    {
        return productOrderRepository.findAllAndGroup(id);
    }
}
