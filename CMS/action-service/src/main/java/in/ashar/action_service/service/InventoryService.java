package in.ashar.action_service.service;

import in.ashar.action_service.DTO.InventoryDTO;
import in.ashar.action_service.entity.Inventory;
import in.ashar.action_service.exception.AlreadyExistsException;
import in.ashar.action_service.mapper.InventoryMapper;
import in.ashar.action_service.repository.InventoryRepository;
import in.ashar.action_service.utility.Priority;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryMapper mapper;

    public Inventory createInventoryItem(InventoryDTO inventoryDTO) {
        Inventory inventory = mapper.mapDtoToInventory(inventoryDTO);
        return inventoryRepository.save(inventory);

    }

    public List<Inventory> createAllInventoryItems(List<@Valid InventoryDTO> inventory) {
        List<Inventory> items = mapper.mapDtoListToInventory(inventory);
        return inventoryRepository.saveAll(items);
    }

    public Inventory updateInventoryItem(String name, InventoryDTO inventoryDTO) {

        String newItemName = inventoryDTO.getItemName();
        int newItemQuantity = inventoryDTO.getAvailableQuantity();
        Priority newPriority = inventoryDTO.getPriority();

        Inventory existing = inventoryRepository.findByItemName(name)
                .orElseThrow(() -> new RuntimeException("Item not found with name: " + name));

        if(inventoryRepository.existsByItemName(newItemName)){
            throw new AlreadyExistsException("Item already exists with name : "+ newItemName);
        }

        existing.setItemName(newItemName);
        existing.setAvailableQuantity(newItemQuantity);
        existing.setPriority(newPriority);

        return inventoryRepository.save(existing);

    }

    public void deleteInventoryItem(String name) {
        inventoryRepository.deleteByItemName(name);
    }


}