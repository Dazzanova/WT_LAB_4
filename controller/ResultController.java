package com.result.demo.controller;

import com.result.demo.entity.Marks;
import com.result.demo.entity.Student;
import com.result.demo.entity.Subject;
import com.result.demo.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return resultService.saveStudent(student);
    }

    @PostMapping("/subjects")
    public Subject addSubject(@RequestBody Subject subject) {
        return resultService.saveSubject(subject);
    }

    @PostMapping("/marks")
    public Marks addMarks(@RequestBody Map<String, Object> data) {

        String prn = (String) data.get("prn");
        String subjectCode = (String) data.get("subjectCode");

        double mse = ((Number) data.get("mseMarks")).doubleValue();
        double ese = ((Number) data.get("eseMarks")).doubleValue();

        return resultService.saveMarks(
                prn,
                subjectCode,
                mse,
                ese
        );
    }

    @GetMapping("/results/{prn}")
    public ResultResponse getResult(@PathVariable String prn) {

        Student student = resultService.getStudent(prn);

        List<Marks> marks =
                resultService.getStudentMarks(prn);

        return new ResultResponse(student, marks);
    }

    public record ResultResponse(
            Student student,
            List<Marks> marks
    ) {
    }
}