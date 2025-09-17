package in.ashar.query_service.controller;

import in.ashar.query_service.entity.Inventory;
import in.ashar.query_service.service.InventoryService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/name")
    public List<Inventory> getAllInventoryItemsByName(@RequestBody List<String> itemNames){
        List<Inventory> items = inventoryService.getAllInventoryItemsByName(itemNames);
        return items;
    }

    @GetMapping
    public List<Inventory> getAllInventoryItems(){
        List<Inventory> items = inventoryService.getAllInventories();
        return items;
    }

    @GetMapping("/outOfStock")
    public List<Inventory> getOutOfStockInventoryItems(){
        List<Inventory> items =inventoryService.getOutOfStockInventoryItems();
        return items;
    }

    @GetMapping("/quantity/{quantity}")
    public List<Inventory> getInventoryItemsByQuantity(@PathVariable @PositiveOrZero int quantity){
        List<Inventory> items =inventoryService.getInventoryItemsByQuantity(quantity);
        return items;
    }

}
