package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.mogel.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = service.getAllFaculties();
        if (faculties.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable long id) {
        Faculty faculty = service.getFacultyById(id);
        if (faculty == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.createFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> changeFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = service.changeFaculty(faculty);
        if (foundFaculty == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        Faculty deletedFaculty = service.deleteFacultyById(id);
        if (deletedFaculty == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deletedFaculty);
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getFacultiesWithColor(@RequestParam(value = "color", required = false) String color) {
        Collection<Faculty> faculties = service.getAllFacultiesWithColor(color);
        if (faculties.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(faculties);
    }
}
