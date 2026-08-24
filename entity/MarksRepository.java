package com.result.demo.repository;

import com.result.demo.entity.Marks;
import com.result.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {

    List<Marks> findByStudent(Student student);
}