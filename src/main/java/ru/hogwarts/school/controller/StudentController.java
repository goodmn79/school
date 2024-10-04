package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@RequestBody StudentDTO student) {
        return ResponseEntity.ok(service.addStudent(student));
    }

    @GetMapping
    public ResponseEntity<Collection<StudentDTO>> getAllStudents(@RequestParam(required = false) Integer age) {
        Collection<StudentDTO> students;
        if (age == null) {
            students = service.getAllStudents();
        } else {
            students = service.findStudentsByAge(age);
        }
        if (students.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable long id) {
        Optional<StudentDTO> foundStudent = service.getStudentById(id);
        return foundStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<StudentDTO> changeStudentData(@RequestBody StudentDTO studentDTO) {
        StudentDTO changedStudent = service.changeStudentData(studentDTO);
        return changedStudent == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(changedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable long id) {
        StudentDTO deletedStudent = service.deleteStudentById(id);
        return deletedStudent == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedStudent);
    }

}
