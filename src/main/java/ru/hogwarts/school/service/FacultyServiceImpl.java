package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.mogel.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

    @Override
    public Collection<Faculty> getAllFacultiesWithColor(String color) {
        return repository.findAllByColorContaining(color);
    }

    @Override
    public Optional<Faculty> getFacultyById(long id) {
        return repository.findById(id);
    }

    @Override
    public Faculty changeFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Optional<Faculty> deleteFacultyById(long id) {
        Optional<Faculty> deletedFaculty = repository.findById(id);
        deletedFaculty.ifPresent(repository::delete);
        return deletedFaculty;
    }

}
