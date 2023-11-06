package com.eipulse.teamproject.service.shoppingservice;


import com.eipulse.teamproject.dto.shoppingdto.CartDTO;
import com.eipulse.teamproject.dto.shoppingdto.OrderDTO;
import com.eipulse.teamproject.dto.shoppingdto.ProductDTO;
import com.eipulse.teamproject.entity.employee.Employee;
import com.eipulse.teamproject.entity.shoppingentity.Cart;
import com.eipulse.teamproject.entity.shoppingentity.CartItem;
import com.eipulse.teamproject.entity.shoppingentity.Order;
import com.eipulse.teamproject.entity.shoppingentity.OrderItem;
import com.eipulse.teamproject.repository.employeerepository.EmployeeRepository;
import com.eipulse.teamproject.repository.shoppingrepository.CartRepository;
import com.eipulse.teamproject.repository.shoppingrepository.OrderRepository;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class OrderService {
    private OrderRepository orderRepository;

    private CartRepository cartRepository;
    private EmployeeRepository empRepository;
    private ProductService productService;
    final static String   MERCHANT ="2000132";


    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, EmployeeRepository empRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.empRepository = empRepository;
        this.productService = productService;
    }



    //購物車轉換訂單
    @Transactional
    public Order saveOrder(CartDTO cartDTO) {
        if (cartDTO == null || cartDTO.getEmpId() == null) {
            throw new RuntimeException("空值");
        }
        int orderTotalPrice = 0;
        Employee employee = empRepository.findById(cartDTO.getEmpId()).orElseThrow(() -> new RuntimeException("無員工"));
        Cart cart = cartRepository.findByEmployee_EmpId(cartDTO.getEmpId());
        Set<OrderItem> orderItems = new LinkedHashSet<>();
        Order order = orderRepository.save(new Order(employee));

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice());

            productService.updateStockQuantity(new ProductDTO(orderItem.getProduct().getId()), orderItem);

            orderItems.add(orderItem);
            orderTotalPrice += orderItem.getTotalPrice();
        }
        order.setOrderItems(orderItems);
        order.setStatus("未付款");
        order.setTotalPrice(orderTotalPrice);
        cartRepository.delete(cart);

        return orderRepository.save(order);

    }

    //單員工所有訂單查詢
    public List<Order> findOrderByEmp(Integer empId){
        return orderRepository.findByEmployee_EmpId(empId);
    }


    // orderId 查詢
    public Order findByOrderId(Integer orderId){
        Optional<Order> optional = orderRepository.findById(orderId);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new RuntimeException("異常");
    }
    
    //抓取員工最後一筆訂單
    public Order findByEmpLastOrder(Integer empId){
        Page<Order> result = orderRepository.findLatestByEmployee_EmpId(empId, PageRequest.of(0, 1));
        List<Order> orders = result.getContent();
        return orders.isEmpty() ? null : orders.get(0);
    }
    
    //抓取所有訂單
    public List<OrderDTO> findAllOrder(){
        List<Order>orderList =orderRepository.findAll();
        List<OrderDTO> result = new ArrayList<>();
        for(Order order :orderList){
            result.add(new OrderDTO(order));
        }
        return result;
    }



    //綠界支付
    public String ecPlayForm(CartDTO cartDTO){
        //建立訂單
        Order order = saveOrder(cartDTO);

        //設定 log 回應，若無設定空字串
        AllInOne allInOne = new AllInOne("");
        //呼叫信用卡付款
        AioCheckOutOneTime aioCheckOutOneTime = new AioCheckOutOneTime();

        LocalDateTime localDateTime = LocalDateTime.now();
        //設定唯一訂單碼
        DateTimeFormatter orderNoTime = DateTimeFormatter.ofPattern("ssMMddHH");
        String orderTrade = localDateTime.format(orderNoTime);
        String tradeNo  = orderTrade+order.getOrderId()+((int)Math.random()*100);
        aioCheckOutOneTime.setMerchantTradeNo(tradeNo);

        //設定訂單日期
        DateTimeFormatter dateTimeFormatter  =DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String orderDate = localDateTime.format(dateTimeFormatter);
        aioCheckOutOneTime.setMerchantTradeDate(orderDate);

        //設定商家碼
        aioCheckOutOneTime.setMerchantID(MERCHANT);


        aioCheckOutOneTime.setTradeDesc("訂單描述");

        //設定總金額
        aioCheckOutOneTime.setTotalAmount(Integer.toString(order.getTotalPrice()));

        //寫入商品名稱
        StringBuilder stringBuilder = new StringBuilder();
        for(OrderItem orderItem : order.getOrderItems()){
            stringBuilder.append(orderItem.getProduct().getProductName());
            stringBuilder.append("#");
        }
        aioCheckOutOneTime.setItemName(stringBuilder.toString());

        //設定接受回傳結果路徑
        aioCheckOutOneTime.setReturnURL("http://localhost:8090/eipulse/ecPlay/success");


        aioCheckOutOneTime.setNeedExtraPaidInfo("Y");
        aioCheckOutOneTime.setRedeem("Y");
        //跳轉至綠界完成畫面由按鈕跳回頁面
        aioCheckOutOneTime.setClientBackURL("http://localhost:5173/mall/order");

        //呼叫綠界支付頁面，將回傳form表單至前端
        String form =  allInOne.aioCheckOut(aioCheckOutOneTime,null);
        order.setStatus("已付款");
        updateStatus(order);
        return form;
    }

    //更新訂單狀態
    public boolean updateStatus(Order order){
        if (order!=null){
            orderRepository.save(order);
            return true;
        }else {
            return false;
        }
    }
}