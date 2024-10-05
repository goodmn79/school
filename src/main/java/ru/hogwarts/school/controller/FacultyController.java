package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
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
    public ResponseEntity<FacultyDTO> createFaculty(@RequestBody FacultyDTO faculty) {
        return ResponseEntity.ok(service.addFaculty(faculty));
    }

    @GetMapping
    public ResponseEntity<Collection<FacultyDTO>> getAllFaculties(@RequestParam(required = false) String search_term) {
        Collection<FacultyDTO> faculties;
        if (search_term == null) {
            faculties = service.getAllFaculties();
        } else {
            faculties = service.getAllFacultiesWithNameOrColor(search_term);
        }
        if (faculties.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable long id) {
        Optional<FacultyDTO> foundFaculty = service.getFacultyById(id);
        return foundFaculty.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<StudentDTO>> getFacultyStudents(@PathVariable long id) {
        Collection<StudentDTO> facultyStudents = service.getFacultyStudents(id);
        return facultyStudents == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(facultyStudents);
    }

    @PutMapping
    public ResponseEntity<FacultyDTO> changeFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO changedFaculty = service.changeFaculty(facultyDTO);
        return changedFaculty == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(changedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FacultyDTO> deleteFaculty(@PathVariable long id) {
        FacultyDTO deletedFaculty = service.deleteFacultyById(id);
        return deletedFaculty == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedFaculty);
    }
}
