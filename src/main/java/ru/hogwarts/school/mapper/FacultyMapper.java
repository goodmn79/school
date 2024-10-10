package ru.hogwarts.school.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.model.Faculty;

import java.lang.reflect.Type;
import java.util.Collection;

public class FacultyMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public FacultyMapper() {
        mapper.addMappings(new PropertyMap<FacultyDTO, Faculty>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }

    public static Faculty mapToFaculty(FacultyDTO facultyDTO) {
        return mapper.map(facultyDTO, Faculty.class);
    }

    public static FacultyDTO mapToFacultyDTO(Faculty faculty) {
        return mapper.map(faculty, FacultyDTO.class);
    }

    public static Collection<FacultyDTO> mapToCollectionFacultyDTOs(Collection<Faculty> faculties) {
        Type collectionType = new TypeToken<Collection<FacultyDTO>>() {
        }.getType();

        return mapper.map(faculties, collectionType);
    }

    public static Collection<Faculty> mapToCollectionFaculties(Collection<FacultyDTO> facultyDTOS) {
        Type collectionType = new TypeToken<Collection<Faculty>>() {
        }.getType();
        return mapper.map(facultyDTOS, collectionType);
    }
}
