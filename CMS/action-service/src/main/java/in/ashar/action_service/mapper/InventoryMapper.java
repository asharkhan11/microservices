package in.ashar.action_service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.action_service.DTO.InventoryDTO;
import in.ashar.action_service.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class InventoryMapper {

    @Autowired
    private ObjectMapper mapper;

    public Inventory mapDtoToInventory(InventoryDTO dto){
        return mapper.convertValue(dto, Inventory.class);
    }

    public InventoryDTO mapInventoryToDto(Inventory menu){
        return mapper.convertValue(menu, InventoryDTO.class);
    }

    public List<Inventory> mapDtoListToInventory(List<InventoryDTO> menu){
        List<Inventory> items = Collections.synchronizedList(new ArrayList<>());
        List<Future<Inventory>> futureItems = new ArrayList<>();

        try (ExecutorService threads = Executors.newFixedThreadPool(4)){

            for (InventoryDTO item : menu) {
                Future<Inventory> future = threads.submit(() -> mapDtoToInventory(item));
                futureItems.add(future);
            }

            for (Future<Inventory> future : futureItems) {
                items.add(future.get());
            }

            threads.shutdown();
            return items;

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public List<InventoryDTO> mapInventoryListToDto(List<Inventory> menu){
        List<InventoryDTO> items = Collections.synchronizedList(new ArrayList<>());
        List<Future<InventoryDTO>> futureItems = new ArrayList<>();

        try (ExecutorService threads = Executors.newFixedThreadPool(4)){

            for (Inventory item : menu) {
                Future<InventoryDTO> future = threads.submit(() -> mapInventoryToDto(item));
                futureItems.add(future);
            }

            for (Future<InventoryDTO> future : futureItems) {
                items.add(future.get());
            }

            threads.shutdown();
            return items;

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
