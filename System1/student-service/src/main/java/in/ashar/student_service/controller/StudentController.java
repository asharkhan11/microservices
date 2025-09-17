package in.ashar.student_service.controller;

import in.ashar.student_service.DTO.StudentDto;
import in.ashar.student_service.DTO.TeacherDto;
import in.ashar.student_service.entity.Student;
import in.ashar.student_service.feignClient.TeacherFeignClient;
import in.ashar.student_service.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherFeignClient feignClient;

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto student) {
        System.out.println("inside student controller");
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable int id, @RequestBody StudentDto student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }


    /// ////////////////////////  Teacher Apis ///////////////////////////


    @GetMapping("teacher/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable int id) {
        return feignClient.getTeacherById(id);
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<TeacherDto>> getAllTeachers() {
        return feignClient.getAllTeachers();
    }


}