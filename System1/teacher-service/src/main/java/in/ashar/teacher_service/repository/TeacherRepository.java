package in.ashar.teacher_service.repository;

import in.ashar.teacher_service.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

//    // READ
//    Optional<Teacher> findByName(String name);
//
//    List<Teacher> findAllByName(String name);
//
//    // UPDATE (custom implementations go in service or @Query if needed)
//
//    // DELETE
//    void deleteByName(String name);
//
//    // EXISTS
//    boolean existsByName(String name);
//
//    // COUNT
//    long countByName(String name);
}