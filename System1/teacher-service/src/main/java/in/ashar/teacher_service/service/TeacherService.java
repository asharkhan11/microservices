package in.ashar.teacher_service.service;

import in.ashar.teacher_service.DTO.TeacherDto;
import in.ashar.teacher_service.entity.Teacher;
import in.ashar.teacher_service.exception.NotFoundException;
import in.ashar.teacher_service.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherDto createTeacher(TeacherDto teacherDto) {
        teacherRepository.save(Teacher.builder().teacherName(teacherDto.getTeacherName()).build());
        return teacherDto;
    }

    public TeacherDto getTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with id: " + id));
        return new TeacherDto(teacher.getTeacherName());
    }

    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(t-> new TeacherDto(t.getTeacherName())).toList();
    }

    public TeacherDto updateTeacher(int id, TeacherDto teacherDto) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found with id: " + id));
        String teacherName = teacherDto.getTeacherName();
        if(teacherName != null && !teacherName.isBlank()){
            existing.setTeacherName(teacherName);
        }
        teacherRepository.save(existing);
        return teacherDto;
    }

    public void deleteTeacher(int id) {
        teacherRepository.deleteById(id);
    }
}