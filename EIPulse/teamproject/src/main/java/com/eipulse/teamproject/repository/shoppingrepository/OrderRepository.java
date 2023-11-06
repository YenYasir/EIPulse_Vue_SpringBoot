package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.entity.shoppingentity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("from Order o where o.employee.empId=:empId order by o.createdAt ASC ")
    List<Order>findByEmployee_EmpId(@Param("empId")Integer empId);


    @Query("from Order o where o.employee.empId=:empId order by o.createdAt DESC")
    Page<Order> findLatestByEmployee_EmpId(@Param("empId") Integer empId, Pageable pageable);
}