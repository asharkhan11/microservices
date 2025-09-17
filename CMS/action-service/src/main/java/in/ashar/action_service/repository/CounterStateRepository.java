package in.ashar.action_service.repository;

import in.ashar.action_service.entity.CounterState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterStateRepository extends JpaRepository<CounterState, Integer> {}
