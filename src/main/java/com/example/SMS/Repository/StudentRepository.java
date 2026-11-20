package com.example.SMS.Repository;

import com.example.SMS.Modal.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByCourseIgnoreCase(String course);
}
