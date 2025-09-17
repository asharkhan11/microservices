package in.ashar.student_service.feignClient;


import in.ashar.student_service.DTO.TeacherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "teacher-service")
public interface TeacherFeignClient {

    @GetMapping("/teacher/{id}")
    ResponseEntity<TeacherDto> getTeacherById(@PathVariable int id);

    @GetMapping("/teacher")
    ResponseEntity<List<TeacherDto>> getAllTeachers();

}
