package in.ashar.query_service.controller;

import in.ashar.query_service.entity.MyOrder;
import in.ashar.query_service.service.OrderService;
import in.ashar.query_service.utility.Status;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/token/{token}")
    public ResponseEntity<MyOrder> getOrderOfTodayByToken(@PathVariable @Positive int token){
        MyOrder order = service.getOrderOfTodayByToken(token);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/recent/{count}")
    public ResponseEntity<List<MyOrder>> getRecentOrders(@PathVariable @Positive int count){
        List<MyOrder> orders = service.getRecentOrders(count);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MyOrder>> getOrdersByStatus(@PathVariable Status status){
        List<MyOrder> orders = service.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

}
