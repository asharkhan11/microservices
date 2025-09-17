package in.ashar.action_service.FeignClient;

import in.ashar.action_service.entity.Inventory;
import in.ashar.action_service.entity.MyOrder;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "query-service")
public interface CmsClient {

    @PostMapping("/inventory/name")
    List<Inventory> getAllInventoryItemsByName(@RequestBody List<String> itemNames);

    @GetMapping("/order/token/{token}")
    MyOrder getOrderOfTodayByToken(@PathVariable @Positive int token);

}
