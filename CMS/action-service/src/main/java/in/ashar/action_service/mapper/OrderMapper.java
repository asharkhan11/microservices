package in.ashar.action_service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.action_service.entity.MenuItem;
import in.ashar.action_service.DTO.RequestOrderDTO;
import in.ashar.action_service.DTO.ResponseOrderDTO;
import in.ashar.action_service.FeignClient.CmsClient;
import in.ashar.action_service.entity.Inventory;
import in.ashar.action_service.entity.MyOrder;
import in.ashar.action_service.exception.InsufficientQuantity;
import in.ashar.action_service.utility.AsyncOrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {


    @Autowired
    private AsyncOrderHelper asyncOrderHelper;
    @Autowired
    private CmsClient cmsClient;
    @Autowired
    private ObjectMapper objectMapper;

    public MyOrder mapRequestDtoToOrder(RequestOrderDTO orderDTO){

        MyOrder order = new MyOrder();
        List<String> menuItemNames = orderDTO.getMenuItemNames();
        List<Integer> quantities = orderDTO.getQuantities();
        List<MenuItem> list = new ArrayList<>();
        
        int totalPrice = 0;

        List<Inventory> inventoryItems = cmsClient.getAllInventoryItemsByName(menuItemNames);

        for (int i=0; i< inventoryItems.size(); i++) {

            Inventory item = inventoryItems.get(i);

            int availableQuantity = item.getAvailableQuantity();
            Integer requestedQuantity = quantities.get(i);

            if(availableQuantity < requestedQuantity){
                throw new InsufficientQuantity("quantities required : %d, but available quantities are : %d".formatted(quantities.get(i), availableQuantity));
            }

            MenuItem menu = new MenuItem();

            menu.setItemName(item.getItemName());
            menu.setItemQuantity(requestedQuantity);
            menu.setItemPrice(item.getItemPrice());

            list.add(menu);

            totalPrice += item.getItemPrice()*requestedQuantity;

//            update inventory
            inventoryItems.get(i).setAvailableQuantity(availableQuantity-requestedQuantity);
        }


        order.setMenuItems(list);
        order.setTotalPrice(totalPrice);

        asyncOrderHelper.updateInventory(inventoryItems);

        return order;
    }


    public ResponseOrderDTO mapOrderToResponseDTO(MyOrder order){
        return objectMapper.convertValue(order, ResponseOrderDTO.class);
    }

}
