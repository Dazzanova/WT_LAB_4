package com.result.demo.service;

import com.result.demo.entity.Marks;
import com.result.demo.entity.Student;
import com.result.demo.entity.Subject;
import com.result.demo.repository.MarksRepository;
import com.result.demo.repository.StudentRepository;
import com.result.demo.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final MarksRepository marksRepository;

    public ResultService(
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            MarksRepository marksRepository) {

        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.marksRepository = marksRepository;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Marks saveMarks(
            String prn,
            String subjectCode,
            double mse,
            double ese) {

        Student student = studentRepository
                .findByPrn(prn)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Subject subject = subjectRepository
                .findByCode(subjectCode)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        double finalMarks = mse + ese;

        Marks marks = new Marks();

        marks.setStudent(student);
        marks.setSubject(subject);
        marks.setMseMarks(mse);
        marks.setEseMarks(ese);
        marks.setFinalMarks(finalMarks);
        marks.setGrade(calculateGrade(finalMarks));

        return marksRepository.save(marks);
    }

    public Student getStudent(String prn) {

        return studentRepository
                .findByPrn(prn)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }

    public List<Marks> getStudentMarks(String prn) {

        Student student = getStudent(prn);

        return marksRepository.findByStudent(student);
    }

    private String calculateGrade(double marks) {

        if (marks >= 90)
            return "A+";
        else if (marks >= 80)
            return "A";
        else if (marks >= 70)
            return "B+";
        else if (marks >= 60)
            return "B";
        else if (marks >= 50)
            return "C";
        else if (marks >= 40)
            return "D";
        else
            return "F";
    }
}