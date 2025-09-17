package in.ashar.action_service.entity;

import in.ashar.action_service.utility.ItemType;
import in.ashar.action_service.utility.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(columnList = "itemName", unique = true))
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventoryId;
    @Column(unique = true)
    private String itemName;
    private int availableQuantity;
    private int itemPrice;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.LOW;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ItemType type = ItemType.RAW;

}
