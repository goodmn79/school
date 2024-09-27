package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.mogel.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents(@RequestParam(value = "age", required = false) Integer age) {
        Collection<Student> students;
        if (age == null) {
            students = service.getAllStudents();
        } else {
            students = service.getAllStudentsWithAge(age);
        }
        if (students.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable long id) {
        Student student = service.getStudentById(id);
        if (student == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(service.addStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> changeStudentData(@RequestBody Student student) {
        Student foundStudent = service.changeStudentData(student);
        if (foundStudent == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        Student deletedStudent = service.deleteStudentById(id);
        if (deletedStudent == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(deletedStudent);
    }
}
