package in.ashar.action_service.utility;

import in.ashar.action_service.entity.Inventory;
import in.ashar.action_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AsyncOrderHelper {

    @Autowired
    private InventoryRepository repository;

    @Async
    public void updateInventory(List<Inventory> inventoryItems) {
        repository.saveAll(inventoryItems);
    }
}
