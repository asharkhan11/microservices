package in.ashar.action_service.controller;

import in.ashar.action_service.DTO.InventoryDTO;
import in.ashar.action_service.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody @Valid InventoryDTO inventory) {

        service.createInventoryItem(inventory);
        return ResponseEntity.ok(inventory);

    }

    @PostMapping("/all")
    public ResponseEntity<List<InventoryDTO>> createAllInventoryItems(@RequestBody List< @Valid InventoryDTO> inventory) {

        service.createAllInventoryItems(inventory);
        return ResponseEntity.ok(inventory);

    }

    @PutMapping("/{name}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable String name, @RequestBody @Valid InventoryDTO inventory) {

        service.updateInventoryItem(name, inventory);
        return ResponseEntity.ok(inventory);

    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteInventory(@PathVariable String name) {

        service.deleteInventoryItem(name);
        return ResponseEntity.noContent().build();

    }
    
}
