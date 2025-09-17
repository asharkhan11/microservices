package in.ashar.teacher_service.controller;

import in.ashar.teacher_service.DTO.StudentDto;
import in.ashar.teacher_service.DTO.TeacherDto;
import in.ashar.teacher_service.entity.Teacher;
import in.ashar.teacher_service.feignClient.StudentFeignClient;
import in.ashar.teacher_service.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentFeignClient feignClient;

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.createTeacher(teacherDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable int id, @RequestBody TeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    /// ///////////////////////// Student Apis //////////////////////////////////

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable int id) {
        return feignClient.getStudentById(id);
    }

    @GetMapping("/student")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return feignClient.getAllStudents();
    }

}