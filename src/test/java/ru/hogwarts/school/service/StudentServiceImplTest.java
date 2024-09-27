package ru.hogwarts.school.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.hogwarts.school.mogel.Student;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constant.ConstantsForStudentServiceImplTest.*;

class StudentServiceImplTest {
    private final StudentService out = new StudentServiceImpl();

    @ParameterizedTest
    @MethodSource("provideParams")
    void addStudent_whenAddStudent_shouldReturnThisStudent(Student newStudent) {

        Student actual = out.addStudent(newStudent);

        assertEquals(newStudent, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void getStudentById_whenCorrectId_shouldReturnValueByThisId(long id) {
        init();

        Student actual = out.getStudentById(id);

        assertEquals(id, actual.getId());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void changeStudent_whenCorrectId_shouldChangeValueByThisIdAndReturnThis(long id) {
        init();
        Student replaceStudent = out.getStudentById(id);
        Student expected = new Student(id, "newName", 5);

        Student actual = out.changeStudentData(expected);

        assertNotEquals(replaceStudent, actual);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {-5, 0, 5})
    void changeStudent_whenInvalidId_shouldReturnNull(long id) {
        init();
        Student expected = new Student(id, "newName", 5);

        Student actual = out.changeStudentData(expected);

        assertNull(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void deleteStudentById_whenCorrectId_shouldReturnValueByThisIdAndDeleteThis(long id) {
        init();
        Student expected = out.getStudentById(id);

        Student actual = out.deleteStudentById(id);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertNull(out.getStudentById(id));

    }

    @ParameterizedTest
    @ValueSource(longs = {-5, 0, 5})
    void deleteStudent_whenInvalidId_shouldReturnNull(long id) {
        init();

        Student actual = out.deleteStudentById(id);

        assertNull(actual);
    }

    @ParameterizedTest
    @MethodSource("provideParams")
    void getAllStudents_whenNotEmpty_shouldReturnCollectionStudents(Student student) {
        init();

        Collection<Student> actual = out.getAllStudents();

        assertTrue(actual.contains(student));
    }

    @ParameterizedTest
    @MethodSource("provideParams")
    void getAllStudentsWithAge_whenCorrectValue_shouldReturnNotEmptyCollection(Student student) {
        init();
        int age = student.getAge();

        Collection<Student> actual = out.getAllStudentsWithAge(age);

        assertFalse(actual.isEmpty());
        assertTrue(actual
                .stream()
                .anyMatch(f -> Objects.equals(age, f.getAge())));
    }

    private static Stream<Arguments> provideParams() {
        return Stream.of(
                Arguments.of(POTTER),
                Arguments.of(GRANGER),
                Arguments.of(DIGGORY),
                Arguments.of(PERKS));
    }

    private void init() {
        out.addStudent(POTTER);
        out.addStudent(GRANGER);
        out.addStudent(DIGGORY);
        out.addStudent(PERKS);
    }
}