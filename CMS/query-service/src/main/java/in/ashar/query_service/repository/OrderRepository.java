package in.ashar.query_service.repository;

import in.ashar.query_service.entity.MyOrder;
import in.ashar.query_service.utility.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<MyOrder, Integer> {

    @Query("SELECT o FROM MyOrder o WHERE o.tokenNumber = :tokenNumber " +
            "AND o.createdAt BETWEEN :startOfDay AND :endOfDay")
    Optional<MyOrder> findTodayOrderByToken(@Param("tokenNumber") int tokenNumber,
                                            @Param("startOfDay") LocalDateTime startOfDay,
                                            @Param("endOfDay") LocalDateTime endOfDay);

    List<MyOrder> findAllByTokenNumber(int tokenNumber);

    Page<MyOrder> findAllByOrderByCreatedAtDesc(Pageable pageable);


    @Query("SELECT o FROM MyOrder o " +
            "WHERE o.status = :status " +
            "AND o.createdAt BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY o.createdAt DESC")
    List<MyOrder> findTodayOrdersByStatus(
            @Param("status") Status status,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

}
