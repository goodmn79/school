package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.FacultyAlreadyAddedException;
import ru.hogwarts.school.exception.FacultyHasStudentException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.hogwarts.school.mapper.FacultyMapper.*;
import static ru.hogwarts.school.mapper.StudentMapper.mapToCollectionStudentDTOs;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    @Override
    public Collection<FacultyDTO> getAllFacultiesByNameOrColor(String searchTerm) {
        Collection<Faculty> filteredByNameFaculty = facultyRepository.findByNameContainsIgnoreCase(searchTerm);
        Collection<Faculty> filteredByColorFaculty = facultyRepository.findByColorContainsIgnoreCase(searchTerm);
        Collection<Faculty> filteredFaculty = Stream.concat(
                        filteredByNameFaculty.stream(),
                        filteredByColorFaculty.stream())
                .collect(Collectors.toSet());
        if (filteredFaculty.isEmpty()) throw new FacultyNotFoundException();
        return mapToCollectionFacultyDTOs(filteredFaculty);
    }

    @Override
    public Collection<StudentDTO> getStudentsOfFaculty(long id) {
        Collection<Student> facultyStudents = studentRepository.findAllByFacultyId(id);
        return mapToCollectionStudentDTOs(facultyStudents);
    }

    @Override
    public FacultyDTO add(FacultyDTO facultyDTO) {
        Optional<Faculty> faculty = facultyRepository.findByNameIgnoreCase(facultyDTO.getName());
        if (faculty.isPresent()) throw new FacultyAlreadyAddedException();
        Faculty savedFaculty = facultyRepository.save(mapToFaculty(facultyDTO));
        return mapToFacultyDTO(savedFaculty);
    }

    @Override
    public Collection<FacultyDTO> getAll() {
        Collection<Faculty> faculties = facultyRepository.findAll();
        if (faculties.isEmpty()) throw new FacultyNotFoundException();
        return mapToCollectionFacultyDTOs(faculties);
    }

    @Override
    public FacultyDTO getById(long id) {
        Optional<Faculty> foundFaculty = facultyRepository.findById(id);
        return foundFaculty.map(FacultyMapper::mapToFacultyDTO).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public FacultyDTO change(FacultyDTO facultyDTO) {
        Optional<Faculty> changedFaculty = facultyRepository.findById(facultyDTO.getId());
        if (changedFaculty.isEmpty()) throw new FacultyNotFoundException();
        changedFaculty.map(f -> {
            f.setName(facultyDTO.getName());
            f.setColor(facultyDTO.getColor());
            facultyRepository.save(f);
            return f;
        });
        return changedFaculty.map(FacultyMapper::mapToFacultyDTO).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public FacultyDTO deleteById(long id) {
        if (!getStudentsOfFaculty(id).isEmpty()) throw new FacultyHasStudentException();
        Optional<Faculty> deletedFaculty = facultyRepository.findById(id);
        if (deletedFaculty.isEmpty()) throw new FacultyNotFoundException();
        facultyRepository.delete(deletedFaculty.get());
        return deletedFaculty.map(FacultyMapper::mapToFacultyDTO).orElseThrow(FacultyNotFoundException::new);
    }
}
