package in.ashar.action_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterState {
    @Id
    private int id = 1; // always 1 (singleton row)

    private int counter;
    private LocalDate lastResetDate;
}
