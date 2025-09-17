package in.ashar.query_service.repository;

import in.ashar.query_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findAllByItemNameIn(List<String> itemNames);

    List<Inventory> findAllByAvailableQuantity(int quantity);
}
