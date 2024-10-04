package ru.hogwarts.school.mapper;

import org.modelmapper.ModelMapper;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public class SchoolMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public static Faculty toFaculty(FacultyDTO facultyDTO) {
        return mapper.map(facultyDTO, Faculty.class);
    }

    public static FacultyDTO toFacultyDTO(Faculty faculty) {
        return mapper.map(faculty, FacultyDTO.class);
    }

    public static Student toStudent(StudentDTO studentDTO) {
        return mapper.map(studentDTO, Student.class);
    }

    public static StudentDTO toStudentDTO(Student student) {
        return mapper.map(student, StudentDTO.class);
    }
}
