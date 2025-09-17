package in.ashar.action_service.DTO;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestOrderDTO {

    @NotEmpty(message = "Item names must be provided")
    private List<String> menuItemNames;
    @NotEmpty(message = "Quantities must be provided")
    private List<Integer> quantities;


    @AssertTrue(message = "menuItemNames and quantities must have the same size")
    public boolean isSameSize() {
        if (menuItemNames == null || quantities == null) return true;
        return menuItemNames.size() == quantities.size();
    }

}
