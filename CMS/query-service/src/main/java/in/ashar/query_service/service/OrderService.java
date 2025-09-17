package in.ashar.query_service.service;

import in.ashar.query_service.entity.MyOrder;
import in.ashar.query_service.exception.NotFoundException;
import in.ashar.query_service.repository.OrderRepository;
import in.ashar.query_service.utility.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public MyOrder getOrderOfTodayByToken(int token){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        MyOrder order = repository.findTodayOrderByToken(token, startOfDay, endOfDay).orElseThrow(()-> new NotFoundException("Order not found for token : "+token));
        return order;
    }

    public List<MyOrder> getRecentOrders(int count){
        Page<MyOrder> recentOrders = repository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, count));
        List<MyOrder> orders = recentOrders.getContent();

        return orders;
    }

    public List<MyOrder> getOrdersByStatus(Status status) {

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return repository.findTodayOrdersByStatus(status, startOfDay, endOfDay);

    }
}
