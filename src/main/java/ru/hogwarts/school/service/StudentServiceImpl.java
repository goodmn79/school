package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.StudentAlreadyAddedException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static ru.hogwarts.school.mapper.FacultyMapper.mapToFaculty;
import static ru.hogwarts.school.mapper.FacultyMapper.mapToFacultyDTO;
import static ru.hogwarts.school.mapper.StudentMapper.*;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    private final FacultyService facultyService;

    @Override
    public Collection<StudentDTO> findByAgeBetween(int from, int to) {
        Collection<Student> students = repository.findByAgeBetween(from, to);
        if (students.isEmpty()) throw new StudentNotFoundException();
        return mapToCollectionStudentDTOs(students);
    }

    @Override
    public Collection<StudentDTO> findByAge(int age) {
        Collection<Student> students = repository.findByAge(age);
        if (students.isEmpty()) throw new StudentNotFoundException();
        return mapToCollectionStudentDTOs(students);
    }

    @Override
    public FacultyDTO getFacultyOfStudent(long id) {
        Student foundStudent = repository.findById(id).orElseThrow(StudentNotFoundException::new);
        Faculty faculty = foundStudent.getFaculty();
        return mapToFacultyDTO(faculty);
    }

    @Override
    public StudentDTO add(long facultyId, StudentDTO studentDTO) {
        Faculty faculty = mapToFaculty(facultyService.getById(facultyId));
        Student student = new Student()
                .setName(studentDTO.getName())
                .setAge(studentDTO.getAge())
                .setFaculty(faculty);
        return add(mapToStudentDTO(student));
    }

    @Override
    public StudentDTO add(StudentDTO studentDTO) {
        checkContains(studentDTO);
        Student addedStudent = repository.save(mapToStudent(studentDTO));
        return mapToStudentDTO(addedStudent);
    }

    @Override
    public Collection<StudentDTO> getAll() {
        Collection<Student> students = repository.findAll();
        if (students.isEmpty()) throw new StudentNotFoundException();
        return mapToCollectionStudentDTOs(students);
    }

    @Override
    public StudentDTO getById(long id) {
        Optional<Student> foundStudent = repository.findById(id);
        return foundStudent.map(StudentMapper::mapToStudentDTO).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public StudentDTO change(StudentDTO studentDTO) {
        Optional<Student> foundStudent = repository.findById(studentDTO.getId());
        foundStudent.map(s -> {
            s.setName(studentDTO.getName());
            s.setAge(studentDTO.getAge());
            repository.save(s);
            return s;
        });
        return mapToStudentDTO(foundStudent.orElseThrow(StudentNotFoundException::new));
    }

    @Override
    public StudentDTO deleteById(long id) {
        Optional<Student> foundStudent = repository.findById(id);
        foundStudent.ifPresent(repository::delete);
        return mapToStudentDTO(foundStudent.orElseThrow(StudentNotFoundException::new));
    }

    private void checkContains(StudentDTO studentDTO) {
        Optional<Student> student = repository.findByNameIgnoreCase(studentDTO.getName());
        if (student.isPresent()) throw new StudentAlreadyAddedException();
    }
}
