package in.ashar.student_service.service;

import feign.FeignException;
import in.ashar.student_service.DTO.StudentDto;
import in.ashar.student_service.entity.Student;
import in.ashar.student_service.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDto createStudent(StudentDto studentDto) {
        studentRepository.save(Student.builder().studentName(studentDto.getStudentName()).build());
        return studentDto;
    }

    public StudentDto getStudentById(int id) {
        Student student= studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return new StudentDto(student.getStudentName());
    }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        return students.stream().map(s -> new StudentDto(s.getStudentName())).toList();
    }

    public StudentDto updateStudent(int id, StudentDto studentDto) {

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if(studentDto.getStudentName() != null && !studentDto.getStudentName().isBlank()){
            existing.setStudentName(studentDto.getStudentName());
        }

        studentRepository.save(existing);

        return studentDto;
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }
}