package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.mogel.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.addFaculty(faculty));
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties(@RequestParam(required = false) String color) {
        Collection<Faculty> faculties;
        if (color == null) {
            faculties = service.getAllFaculties();
        } else {
            faculties = service.getAllFacultiesWithColor(color);
        }
        if (faculties.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable long id) {
        Optional<Faculty> foundFaculty = service.getFacultyById(id);
        return foundFaculty.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Faculty> changeFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(service.changeFaculty(faculty));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        Optional<Faculty> deletedFaculty = service.deleteFacultyById(id);
        return deletedFaculty.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
