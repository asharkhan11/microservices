package in.ashar.query_service.DTO;

import in.ashar.query_service.entity.MenuItem;
import in.ashar.query_service.utility.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseOrderDTO {

    private int tokenNumber;
    private List<MenuItem> menuItems;
    private int totalPrice;
    private Status status;
    private LocalDate createdAt;

}
