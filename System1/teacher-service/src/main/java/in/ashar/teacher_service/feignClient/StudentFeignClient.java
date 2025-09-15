package in.ashar.teacher_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "student-service")
public interface StudentFeignClient {


    @PostMapping("/student")
    ResponseEntity<Student> create(@RequestBody Student student);

}
