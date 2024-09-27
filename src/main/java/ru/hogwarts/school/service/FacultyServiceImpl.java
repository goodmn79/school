package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.mogel.Faculty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private static final Map<Long, Faculty> storage = new HashMap<>();
    private long idGenerator;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++idGenerator);
        storage.put(idGenerator, faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public Faculty changeFaculty(Faculty faculty) {
        if (storage.containsKey(faculty.getId())) {
            storage.put(faculty.getId(), faculty);
            return faculty;
        }
        return null;
    }

    @Override
    public Faculty deleteFacultyById(long id) {
        if (storage.containsKey(id)) {
            return storage.remove(id);
        }
        return null;
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return Collections.unmodifiableCollection(storage.values());
    }

    @Override
    public Collection<Faculty> getAllFacultiesWithColor(String color) {
        return this.getAllFaculties()
                .stream()
                .filter(f -> f.getColor().equals(color))
                .toList();
    }
}
