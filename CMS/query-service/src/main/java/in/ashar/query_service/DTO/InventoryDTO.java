package in.ashar.query_service.DTO;

import in.ashar.query_service.utility.ItemType;
import in.ashar.query_service.utility.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {

    @NotBlank(message = "Item Name must be provided")
    private String itemName;
    @PositiveOrZero(message = "Item Quantity must be provided")
    private int availableQuantity;
    @Positive(message = "Item price must be positive")
    private int itemPrice;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.LOW;
    @Enumerated(EnumType.STRING)
    private ItemType type;

}
