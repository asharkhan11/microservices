package in.ashar.action_service.utility;

import in.ashar.action_service.entity.CounterState;
import in.ashar.action_service.repository.CounterStateRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DailyCounter {

    private final CounterStateRepository counterRepo;

    @Value("${token-reset.hours}")
    private int hours;

    @Value("${token-reset.mins}")
    private int mins;

    private LocalTime resetTime;

    @PostConstruct
    public void init() {
        this.resetTime = LocalTime.of(hours, mins);
    }

    public synchronized int getNextNumber() {
        CounterState state = counterRepo.findById(1)
                .orElseGet(() -> {
                    CounterState cs = new CounterState();
                    cs.setCounter(0);
                    cs.setLastResetDate(LocalDate.now().minusDays(1));
                    return counterRepo.save(cs);
                });

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayReset = LocalDateTime.of(now.toLocalDate(), resetTime);

        if (now.isAfter(todayReset) && !state.getLastResetDate().equals(now.toLocalDate())) {
            state.setCounter(0);
            state.setLastResetDate(now.toLocalDate());
        }

        state.setCounter(state.getCounter() + 1);
        counterRepo.save(state);

        return state.getCounter();
    }
}
