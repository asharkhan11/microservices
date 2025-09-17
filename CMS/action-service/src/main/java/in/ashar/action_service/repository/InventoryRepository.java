package in.ashar.action_service.repository;

import in.ashar.action_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByItemName(String itemName);

    boolean existsByItemName(String itemName);

    void deleteByItemName(String name);

}