package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.dto.FacultyDTO;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Random random = new Random();

    @Test
    void createFaculty() {
        FacultyDTO testFacultyDTO = new FacultyDTO()
                .setId(random.nextLong(10))
                .setName("test name")
                .setColor("testColor");

        FacultyDTO actualFacultyDTO = restTemplate.postForObject(apiUrl(port), testFacultyDTO, FacultyDTO.class);
        System.out.println(actualFacultyDTO);
        assertThat(actualFacultyDTO).isNotNull();
//        assertThat(actualFacultyDTO.getName()).isEqualTo(testFacultyDTO.getName());
//        assertThat(actualFacultyDTO.getColor()).isEqualTo(testFacultyDTO.getColor());
    }

    @Test
    void getAllFaculties() {
    }

    @Test
    void getFacultyById() {
    }

    @Test
    void getStudentsOfFaculty() {
    }

    @Test
    void changeFaculty() {
    }

    @Test
    void deleteFaculty() {
    }

    private final String apiUrl(int port) {
        return "http://localhost:" + port + "/faculties";
    }
}