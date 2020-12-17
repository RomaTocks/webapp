package com.tocks.webapp.Repositories;

import com.tocks.webapp.Models.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long>
{
    @Query("select po from ProductOrder po where po.order.user.userid = :id")
    List<ProductOrder> findAllAndGroup(@Param("id") Integer id);
}
