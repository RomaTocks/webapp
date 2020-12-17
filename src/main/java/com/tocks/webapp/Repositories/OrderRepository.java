package com.tocks.webapp.Repositories;

import com.tocks.webapp.Models.Order;
import com.tocks.webapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query("select o from Order o")
    List<Order> getAll();
    Order findByUser_Userid(Integer id);
    List<Order> findAllByUser_Userid(Integer id);
}