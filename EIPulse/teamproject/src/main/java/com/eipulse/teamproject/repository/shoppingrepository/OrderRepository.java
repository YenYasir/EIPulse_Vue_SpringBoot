package com.eipulse.teamproject.repository.shoppingrepository;

import com.eipulse.teamproject.dto.shoppingdto.OrderDTO;
import com.eipulse.teamproject.entity.shoppingentity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select new com.eipulse.teamproject.dto.shoppingdto.OrderDTO(o) from Order o where o.employee.empId=:empId and DATE(o.createdAt) between :startDate and :endDate order by o.createdAt ASC ")
    Page<OrderDTO>findByEmployee_EmpId(@Param("empId")Integer empId,@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate,Pageable pageable);


    @Query("from Order o where o.employee.empId=:empId order by o.createdAt DESC")
    Page<Order> findLatestByEmployee_EmpId(@Param("empId") Integer empId, Pageable pageable);

    @Query("select new com.eipulse.teamproject.dto.shoppingdto.OrderDTO(o) from  Order  o where DATE(o.createdAt) between :startDate and :endDate order by o.createdAt desc ")
    Page<OrderDTO>findAllPage(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate, Pageable pageable);

}