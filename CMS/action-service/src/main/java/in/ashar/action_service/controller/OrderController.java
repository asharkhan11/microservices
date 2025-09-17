package in.ashar.action_service.controller;

import in.ashar.action_service.DTO.RequestOrderDTO;
import in.ashar.action_service.DTO.ResponseOrderDTO;
import in.ashar.action_service.entity.MyOrder;
import in.ashar.action_service.mapper.OrderMapper;
import in.ashar.action_service.service.OrderService;
import in.ashar.action_service.utility.Status;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper mapper;

    @PostMapping
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody @Valid RequestOrderDTO orderDTO){

        MyOrder order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(mapper.mapOrderToResponseDTO(order));

    }

    @PutMapping("/token/{token}")
    public ResponseEntity<ResponseOrderDTO> updateOrder(@PathVariable @Positive int token, @RequestBody @Valid RequestOrderDTO orderDTO){
        MyOrder order = orderService.updateOrder(token, orderDTO);
        return ResponseEntity.ok(mapper.mapOrderToResponseDTO(order));
    }

    @DeleteMapping("/token/{token}")
    public ResponseEntity<Void> deleteOrder(@Positive int token){
        orderService.deleteOrder(token);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/token/{token}/status/{status}")
    public ResponseEntity<ResponseOrderDTO> updateStatus(@PathVariable @Positive int token, @PathVariable Status status){
        MyOrder order = orderService.updateStatus(token,status);
        return ResponseEntity.ok(mapper.mapOrderToResponseDTO(order));
    }
}
