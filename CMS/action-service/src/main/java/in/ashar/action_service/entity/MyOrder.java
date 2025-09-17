package in.ashar.action_service.entity;

import in.ashar.action_service.utility.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private int tokenNumber;

//    @ManyToMany
//    @JoinTable(
//            name = "order_menu",
//            joinColumns = @JoinColumn(name = "menuId"),
//            inverseJoinColumns = @JoinColumn(name = "orderId")
//    )
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuItem> menuItems = new ArrayList<>();

    private int totalPrice;
    private Status status;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


}
