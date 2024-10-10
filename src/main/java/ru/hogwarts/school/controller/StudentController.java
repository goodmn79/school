package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO student) {
        return ResponseEntity.ok(service.add(student));
    }

    @GetMapping
    public ResponseEntity<Collection<StudentDTO>> getCollectionStudents(@RequestParam(required = false) Integer from,
                                                                        @RequestParam(required = false) Integer to,
                                                                        @RequestParam(required = false) Integer age) {
        Collection<StudentDTO> students;
        if (from != null && to != null) {
            students = service.findByAgeBetween(from, to);
        } else if (age != null) {
            students = service.findByAge(age);
        } else {
            students = service.getAll();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable long id) {
        StudentDTO studentDTO = service.getById(id);
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<FacultyDTO> getStudentFaculty(@PathVariable long id) {
        FacultyDTO studentFaculty = service.getStudentFaculty(id);
        return ResponseEntity.ok(studentFaculty);
    }

    @PutMapping
    public ResponseEntity<StudentDTO> changeStudentData(@RequestBody StudentDTO studentDTO) {
        StudentDTO changedStudent = service.change(studentDTO);
        return ResponseEntity.ok(changedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable long id) {
        StudentDTO deletedStudent = service.deleteById(id);
        return ResponseEntity.ok(deletedStudent);
    }

}
