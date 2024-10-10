package ru.hogwarts.school.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Student;

import java.lang.reflect.Type;
import java.util.Collection;

public class StudentMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public StudentMapper() {
        mapper.addMappings(new PropertyMap<StudentDTO, Student>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
    }

    public static Student mapToStudent(StudentDTO studentDTO) {
        return mapper.map(studentDTO, Student.class);
    }

    public static StudentDTO mapToStudentDTO(Student student) {
        return mapper.map(student, StudentDTO.class);
    }

    public static Collection<StudentDTO> mapToCollectionStudentDTOs(Collection<Student> students) {
        Type collectionType = new TypeToken<Collection<StudentDTO>>() {
        }.getType();

        return mapper.map(students, collectionType);
    }

    public static Collection<Student> mapToCollectionStudents(Collection<StudentDTO> studentDTOs) {
        Type collectionType = new TypeToken<Collection<Student>>() {
        }.getType();
        return mapper.map(studentDTOs, collectionType);
    }
}
