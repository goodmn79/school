package ru.hogwarts.school.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.hogwarts.school.mogel.Faculty;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constant.ConstantsForFacultyServiceImplTest.*;

class FacultyServiceImplTest {
    private final FacultyService out = new FacultyServiceImpl();

    @ParameterizedTest
    @MethodSource("provideParams")
    void createFaculty_whenCreateFaculty_shouldReturnThisFaculty(Faculty creatingFaculty) {

        Faculty actual = out.createFaculty(creatingFaculty);

        assertEquals(creatingFaculty, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void getFacultyById_whenCorrectId_shouldReturnValueByThisId(long id) {
        init();

        Faculty actual = out.getFacultyById(id);

        assertEquals(id, actual.getId());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void changeFaculty_whenCorrectId_shouldChangeValueByThisIdAndReturnThis(long id) {
        init();
        Faculty replaceFaculty = out.getFacultyById(id);
        Faculty expected = new Faculty(id, "newName", "newColor");

        Faculty actual = out.changeFaculty(expected);

        assertNotEquals(replaceFaculty, actual);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {-5, 0, 5})
    void changeFaculty_whenInvalidId_shouldReturnNull(long id) {
        init();
        Faculty expected = new Faculty(id, "newName", "newColor");

        Faculty actual = out.changeFaculty(expected);

        assertNull(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4})
    void deleteFacultyById_whenCorrectId_shouldReturnValueByThisIdAndDeleteThis(long id) {
        init();
        Faculty expected = out.getFacultyById(id);

        Faculty actual = out.deleteFacultyById(id);

        assertNotNull(actual);
        assertEquals(expected, actual);
        assertNull(out.getFacultyById(id));

    }

    @ParameterizedTest
    @ValueSource(longs = {-5, 0, 5})
    void deleteFaculty_whenInvalidId_shouldReturnNull(long id) {
        init();

        Faculty actual = out.deleteFacultyById(id);

        assertNull(actual);
    }

    @ParameterizedTest
    @MethodSource("provideParams")
    void getAllFaculties_whenNotEmpty_shouldReturnCollectionFaculties(Faculty faculty) {
        init();

        Collection<Faculty> actual = out.getAllFaculties();

        assertTrue(actual.contains(faculty));
    }

    @ParameterizedTest
    @MethodSource("provideParams")
    void getAllFacultiesWithColor_whenCorrectValue_shouldReturnNotEmptyCollection(Faculty faculty) {
        init();
        String color = faculty.getColor();

        Collection<Faculty> actual = out.getAllFacultiesWithColor(color);

        assertFalse(actual.isEmpty());
        assertTrue(actual
                .stream()
                .anyMatch(f -> Objects.equals(color, f.getColor())));
    }

    private static Stream<Arguments> provideParams() {
        return Stream.of(
                Arguments.of(GRYFFINDOR),
                Arguments.of(SLYTHERIN),
                Arguments.of(RAVENCLAW),
                Arguments.of(HUFFLEPUFF));
    }

    private void init() {
        out.createFaculty(GRYFFINDOR);
        out.createFaculty(SLYTHERIN);
        out.createFaculty(RAVENCLAW);
        out.createFaculty(HUFFLEPUFF);
    }
}