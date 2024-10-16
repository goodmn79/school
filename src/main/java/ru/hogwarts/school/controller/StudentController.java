package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentServiceImpl studentService;
    private final AvatarService avatarService;

    @PostMapping
    public StudentDTO addStudent(@RequestParam(name = "faculty_id") long facultyId, @RequestBody StudentDTO student) {
        return studentService.add(facultyId, student);
    }

    @PostMapping(value = "{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.isEmpty())return ResponseEntity.badRequest().body("file is empty");
        if (avatar.getSize() >= 1024 * 300) return ResponseEntity.badRequest().body("file size is too large");
        avatarService.upload(studentId, avatar);
        return ResponseEntity.ok().body("avatar successfully added");
    }

    @GetMapping
    public Collection<StudentDTO> getCollectionStudents(@RequestParam(required = false) Integer from,
                                                        @RequestParam(required = false) Integer to,
                                                        @RequestParam(required = false) Integer age) {
        Collection<StudentDTO> students;
        if (from != null && to != null) {
            students = studentService.findByAgeBetween(from, to);
        } else if (age != null) {
            students = studentService.findByAge(age);
        } else {
            students = studentService.getAll();
        }
        return students;
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable long id) {
        return studentService.getById(id);
    }

    @GetMapping("{studentId}/avatar")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable long studentId) throws IOException {
        AvatarDTO avatarDTO = avatarService.getFromDir(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarDTO.getMediaType()));
        headers.setContentLength(avatarDTO.getFileSize());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatarDTO.getBytes());
    }

    @GetMapping("/{id}/faculty")
    public FacultyDTO getFacultyOfStudent(@PathVariable long id) {
        return studentService.getFacultyOfStudent(id);
    }

    @PutMapping
    public StudentDTO changeStudentData(@RequestBody StudentDTO studentDTO) {
        return studentService.change(studentDTO);
    }

    @DeleteMapping("/{id}")
    public StudentDTO deleteStudent(@PathVariable long id) {
        return studentService.deleteById(id);
    }

}
