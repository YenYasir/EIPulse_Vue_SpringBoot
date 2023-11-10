package com.eipulse.teamproject.controller.shoppingcontroller;


import com.eipulse.teamproject.dto.shoppingdto.CartDTO;
import com.eipulse.teamproject.dto.shoppingdto.OrderDTO;
import com.eipulse.teamproject.entity.shoppingentity.Order;
import com.eipulse.teamproject.entity.shoppingentity.OrderItem;
import com.eipulse.teamproject.repository.shoppingrepository.ProductTypeRepository;
import com.eipulse.teamproject.service.shoppingservice.OrderService;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
public class OrderController {
    private OrderService orderService;
    private  ProductTypeRepository productTypeRepository;

    @Autowired
    public OrderController(OrderService orderService,
                           ProductTypeRepository productTypeRepository) {
        this.orderService = orderService;
        this.productTypeRepository = productTypeRepository;
    }

    @PostMapping("/order/ecPlay")
    public ResponseEntity<?> ecPlayOrder(@RequestBody CartDTO cartDTO) {
        return new ResponseEntity<>(orderService.ecPlayForm(cartDTO), HttpStatus.OK);
    }


    @PostMapping("/ecPlay/success")
    public ResponseEntity<?> PlaySuccess() {
        return new ResponseEntity<>("付款成功", HttpStatus.OK);
    }

    @GetMapping("/order/{empId}/{startDate}/{endDate}/{pageNumber}")
    public ResponseEntity<?> findByEmpOrder(@PathVariable Integer empId,@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Integer pageNumber) {
        return new ResponseEntity<>(orderService.findOrderByEmp(empId,startDate,endDate,pageNumber), HttpStatus.OK);
    }


    @GetMapping("/order/details/{empId}")
    public ResponseEntity<?> findByLastOrder(@PathVariable Integer empId) {
        return new ResponseEntity<>(orderService.findByEmpLastOrder(empId), HttpStatus.OK);
    }

    @GetMapping("/order/item/{orderId}")
    public ResponseEntity<?> findByOrderId(@PathVariable Integer orderId) {
        return new ResponseEntity<>(orderService.findByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/orders/{startDate}/{endDate}/{pageNumber}")
    public ResponseEntity<?> findAllOrder(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Integer pageNumber) {
        return new ResponseEntity<>(orderService.findAllOrder(startDate,endDate,pageNumber), HttpStatus.OK);
    }

    @PutMapping("/order")
    public ResponseEntity<?> updateStatus(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.findByOrderId(orderDTO.getOrderId());
        order.setStatus(orderDTO.getStatus());
        return new ResponseEntity<>(orderService.updateStatus(order), HttpStatus.OK);
    }
}
