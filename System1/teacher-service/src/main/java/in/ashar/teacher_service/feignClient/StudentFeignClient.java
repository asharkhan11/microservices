package in.ashar.teacher_service.feignClient;

import in.ashar.teacher_service.DTO.StudentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentFeignClient {


    @GetMapping("/student/{id}")
    ResponseEntity<StudentDto> getStudentById(@PathVariable int id);


    @GetMapping("/student")
    ResponseEntity<List<StudentDto>> getAllStudents();

}
