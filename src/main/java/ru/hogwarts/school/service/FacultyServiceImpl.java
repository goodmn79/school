package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.mapper.SchoolMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

import static ru.hogwarts.school.mapper.SchoolMapper.toFacultyDTO;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public FacultyDTO addFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty(facultyDTO.getId(), facultyDTO.getName(), facultyDTO.getColor());
        repository.save(faculty);
        return facultyDTO;
    }

    @Override
    public Collection<FacultyDTO> getAllFaculties() {
        return repository.findAll()
                .stream()
                .map(SchoolMapper::toFacultyDTO)
                .toList();
    }

    @Override
    public Collection<FacultyDTO> getAllFacultiesWithColor(String color) {
        return repository.findAllByColorContains(color)
                .stream()
                .map(SchoolMapper::toFacultyDTO)
                .toList();
    }

    @Override
    public Optional<FacultyDTO> getFacultyById(long id) {
        return repository.findById(id)
                .map(SchoolMapper::toFacultyDTO);
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
