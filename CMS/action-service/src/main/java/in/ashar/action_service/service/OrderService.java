package in.ashar.action_service.service;

import in.ashar.action_service.DTO.RequestOrderDTO;
import in.ashar.action_service.FeignClient.CmsClient;
import in.ashar.action_service.entity.Inventory;
import in.ashar.action_service.entity.MenuItem;
import in.ashar.action_service.entity.MyOrder;
import in.ashar.action_service.exception.CannotChangeOrderException;
import in.ashar.action_service.exception.InvalidOrderStatusException;
import in.ashar.action_service.mapper.OrderMapper;
import in.ashar.action_service.repository.InventoryRepository;
import in.ashar.action_service.repository.OrderRepository;
import in.ashar.action_service.utility.DailyCounter;
import in.ashar.action_service.utility.Status;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper mapper;
    @Autowired
    private DailyCounter counter;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CmsClient cmsClient;
    @Autowired
    private InventoryRepository inventoryRepository;

    public MyOrder createOrder(RequestOrderDTO orderDTO) {

        MyOrder order = mapper.mapRequestDtoToOrder(orderDTO);

        int tokenNumber = counter.getNextNumber();
        order.setTokenNumber(tokenNumber);

        order.setStatus(Status.PLACED);

        return orderRepository.save(order);
    }

    public MyOrder updateOrder(int token, RequestOrderDTO orderDTO) {

        MyOrder existingOrder = cmsClient.getOrderOfTodayByToken(token);

        if (!existingOrder.getStatus().equals(Status.PLACED)) {
            throw new CannotChangeOrderException("Order is in %s state, cannot update".formatted(existingOrder.getStatus().name()));
        }

        List<String> existingItemNames = existingOrder.getMenuItems().stream().map(MenuItem::getItemName).toList();
        List<Integer> existingQuantities = existingOrder.getMenuItems().stream().map(MenuItem::getItemQuantity).toList();

        List<Inventory> inventoryItems = cmsClient.getAllInventoryItemsByName(existingItemNames);

        for (int i = 0; i < inventoryItems.size(); i++) {
            inventoryItems.get(i).setAvailableQuantity(inventoryItems.get(i).getAvailableQuantity() + existingQuantities.get(i));
        }

        inventoryRepository.saveAll(inventoryItems);

        MyOrder updatedOrder = mapper.mapRequestDtoToOrder(orderDTO);
        updatedOrder.setOrderId(existingOrder.getOrderId());
        updatedOrder.setTokenNumber(existingOrder.getTokenNumber());
        updatedOrder.setStatus(Status.PLACED);

        return orderRepository.save(updatedOrder);
    }

    public void deleteOrder(int token) {
        MyOrder existingOrder = cmsClient.getOrderOfTodayByToken(token);
        orderRepository.delete(existingOrder);
    }


    public MyOrder updateStatus(@Positive int token, Status status) {

        MyOrder order = cmsClient.getOrderOfTodayByToken(token);

        Status existingStatus = order.getStatus();

        final String formatted = "Cannot Change %s Status to %s".formatted(existingStatus.name(), status.name());

        switch (status) {
            case PLACED -> {
                if (!existingStatus.equals(Status.PLACED)) {
                    throw new InvalidOrderStatusException(formatted);
                }
            }
            case PREPARING -> {
                if (!existingStatus.equals(Status.PLACED)) {
                    throw new InvalidOrderStatusException(formatted);
                }
                order.setStatus(status);
            }
            case READY -> {
                if (!existingStatus.equals(Status.PREPARING)) {
                    throw new InvalidOrderStatusException(formatted);
                }
                order.setStatus(status);
            }
            case SERVED -> {
                if (!existingStatus.equals(Status.READY)) {
                    throw new InvalidOrderStatusException(formatted);
                }
                order.setStatus(status);
            }
            case CANCELLED -> {
                if (existingStatus.equals(Status.READY)) {
                    throw new InvalidOrderStatusException("Cannot cancel order as its already READY");
                }
                order.setStatus(status);
            }
            default -> {
                throw new InvalidOrderStatusException("Invalid Status %s".formatted(status));
            }
        }

        return orderRepository.save(order);
    }
}
