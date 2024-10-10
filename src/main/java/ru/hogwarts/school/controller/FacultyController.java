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
    public ResponseEntity<FacultyDTO> createFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO createdFaculty = service.add(facultyDTO);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping
    public ResponseEntity<Collection<FacultyDTO>> getAllFaculties(@RequestParam(required = false) String search_term) {
        Collection<FacultyDTO> faculties;
        if (search_term == null) {
            faculties = service.getAll();
        } else {
            faculties = service.getAllFacultiesByNameOrColor(search_term);
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable long id) {
        FacultyDTO foundFaculty = service.getById(id);
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<StudentDTO>> getFacultyStudents(@PathVariable long id) {
        Collection<StudentDTO> facultyStudents = service.getFacultyStudents(id);
        return ResponseEntity.ok(facultyStudents);
    }

    @PutMapping
    public ResponseEntity<FacultyDTO> changeFaculty(@RequestBody FacultyDTO facultyDTO) {
        FacultyDTO changedFaculty = service.change(facultyDTO);
        return ResponseEntity.ok(changedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FacultyDTO> deleteFaculty(@PathVariable long id) {
        FacultyDTO deletedFaculty = service.deleteById(id);
        return ResponseEntity.ok(deletedFaculty);
    }
}
