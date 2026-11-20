package com.example.SMS.Service.Impl;

import com.example.SMS.Exception.ResourceNotFoundException;
import com.example.SMS.Modal.Student;
import com.example.SMS.Repository.StudentRepository;
import com.example.SMS.Service.StudentService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        student.setId(null);
        if (student.getName() != null) {
            student.setName(student.getName().trim());
        }
        if (student.getEmail() != null) {
            student.setEmail(student.getEmail().trim().toLowerCase());
        }
        if (student.getCourse() != null) {
            student.setCourse(student.getCourse().trim());
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return findStudentOrThrow(id);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student existing = findStudentOrThrow(id);
        if (studentDetails.getName() != null) {
            existing.setName(studentDetails.getName().trim());
        }
        if (studentDetails.getEmail() != null) {
            existing.setEmail(studentDetails.getEmail().trim().toLowerCase());
        }
        existing.setAge(studentDetails.getAge());
        if (studentDetails.getCourse() != null) {
            existing.setCourse(studentDetails.getCourse().trim());
        }
        return studentRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student existing = findStudentOrThrow(id);
        studentRepository.delete(existing);
    }

    @Override
    public List<Student> getStudentsByCourse(String course) {
        String queryCourse = course != null ? course.trim() : "";
        return studentRepository.findByCourseIgnoreCase(queryCourse);
    }

    private Student findStudentOrThrow(Long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }
}
