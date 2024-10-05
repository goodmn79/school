package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.hogwarts.school.mapper.FacultyMapper.*;
import static ru.hogwarts.school.mapper.StudentMapper.toCollectionStudentDTOs;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public FacultyDTO addFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = toFaculty(facultyDTO);
        repository.save(faculty);
        return facultyDTO;
    }

    @Override
    public Collection<FacultyDTO> getAllFaculties() {
        Collection<Faculty> faculties = repository.findAll();
        return toCollectionFacultyDTOs(faculties);
    }

    @Override
    public Collection<FacultyDTO> getAllFacultiesWithNameOrColor(String searchTerm) {
        Collection<Faculty> filteredByName = repository.findByNameContainsIgnoreCase(searchTerm);
        Collection<Faculty> filteredByColor = repository.findByColorContainsIgnoreCase(searchTerm);
        Collection<Faculty> foundFaculties = Stream
                .concat(filteredByName.stream(), filteredByColor.stream())
                .collect(Collectors.toSet());
        return toCollectionFacultyDTOs(foundFaculties);
    }

    @Override
    public Optional<FacultyDTO> getFacultyById(long id) {
        return repository.findById(id)
                .map(FacultyMapper::toFacultyDTO);
    }

    @Override
    public Collection<StudentDTO> getFacultyStudents(long id) {
        Optional<Faculty> faculty = repository.findById(id);
        if (faculty.isEmpty()) return null;
        Collection<Student> facultyStudents = faculty.get().getStudents();
        return toCollectionStudentDTOs(facultyStudents);
    }

    @Override
    public FacultyDTO changeFaculty(FacultyDTO facultyDTO) {
        Optional<Faculty> faculty = repository.findById(facultyDTO.getId());
        if (faculty.isEmpty()) return null;
        faculty.map(f -> {
            f.setName(facultyDTO.getName());
            f.setColor(facultyDTO.getColor());
            return f;
        });
        Faculty changedFaculty = faculty.get();
        repository.save(changedFaculty);
        return toFacultyDTO(changedFaculty);
    }

    @Override
    public FacultyDTO deleteFacultyById(long id) {
        Optional<Faculty> faculty = repository.findById(id);
        if (faculty.isEmpty()) return null;
        Faculty deletedFaculty = faculty.get();
        FacultyDTO deletedFacultyDTO = toFacultyDTO(deletedFaculty);
        repository.delete(deletedFaculty);
        return deletedFacultyDTO;
    }

}
