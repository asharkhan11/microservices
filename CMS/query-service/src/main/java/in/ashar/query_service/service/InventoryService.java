package in.ashar.query_service.service;

import in.ashar.query_service.entity.Inventory;
import in.ashar.query_service.exception.NotFoundException;
import in.ashar.query_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventoryItemsByName(List<String> itemNames) {

        List<Inventory> items = inventoryRepository.findAllByItemNameIn(itemNames);

        if (items == null || items.isEmpty() || items.size()!= itemNames.size()){
            throw new NotFoundException("One or more Items not found");
        }
        return items;
    }

    public List<Inventory> getAllInventories() {

        List<Inventory> items = inventoryRepository.findAll();
        if (items.isEmpty()){
            throw new NotFoundException("Inventory is empty");
        }
        return items;

    }

    public List<Inventory> getOutOfStockInventoryItems() {
        List<Inventory> items = inventoryRepository.findAllByAvailableQuantity(0);
        if (items.isEmpty()){
            throw new NotFoundException("Inventory is empty");
        }
        return items;
    }

    public List<Inventory> getInventoryItemsByQuantity(int quantity) {
        List<Inventory> items = inventoryRepository.findAllByAvailableQuantity(quantity);
        if (items.isEmpty()){
            throw new NotFoundException("Inventory is empty");
        }
        return items;
    }

}
