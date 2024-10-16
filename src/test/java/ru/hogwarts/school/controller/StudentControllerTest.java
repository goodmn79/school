package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.exception.StudentNotFoundException;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Random random = new Random();

    @Test
    void testAddStudent() {
        StudentDTO testStudentDTO = new StudentDTO()
                .setId(random.nextLong(10))
                .setName("test name")
                .setAge(random.nextInt(100));

        StudentDTO actualStudentDTO = restTemplate.postForObject(apiUrl(port), testStudentDTO, StudentDTO.class);

        assertThat(actualStudentDTO).isNotNull();
//        assertThat(actualStudentDTO.getName()).isEqualTo(testStudentDTO.getName());
//        assertThat(actualStudentDTO.getAge()).isEqualTo(testStudentDTO.getAge());
    }

    @Test
    void testUploadAvatar() {
        long id = random.nextLong();
        MultipartFile testAvatar = null;

        String responseString = restTemplate.postForObject(apiUrl(port) + "/" + id + "/avatar", testAvatar, String.class);
        assertThat(responseString).isNotEmpty();
    }

    @Test
    void testGetCollectionStudents() {
        StudentDTO[] testCollection = restTemplate.getForObject(apiUrl(port), StudentDTO[].class);

        assertThat(testCollection).isNotNull();
    }

    @Test
    void getStudentById() {
        StudentDTO testStudentDTO = new StudentDTO()
                .setId(random.nextLong())
                .setName("test name")
                .setAge(random.nextInt());

        StudentDTO actualStudentDTO = restTemplate.getForObject(
                apiUrl(port) + testStudentDTO.getId(),
                StudentDTO.class);
        assertThat(actualStudentDTO).isNotNull();

    }

    @Test
    void downloadAvatar() {
    }

    @Test
    void getFacultyOfStudent() {

    }

    @Test
    void changeStudentData() {
        StudentDTO testStudentDTO = restTemplate.postForObject(
                apiUrl(port),
                new StudentDTO()
                        .setId(random.nextLong())
                        .setName("test name")
                        .setAge(random.nextInt()),
                StudentDTO.class
        );
        long id = testStudentDTO.getId();
        restTemplate.postForObject(apiUrl(port), testStudentDTO, StudentDTO.class);

        StudentDTO changedStudentDTO = new StudentDTO()
                .setId(id)
                .setName("new name")
                .setAge(random.nextInt());
        restTemplate.put(apiUrl(port) + id, changedStudentDTO);

        StudentDTO actualStudentDTO = restTemplate.getForObject(apiUrl(port) + "/" + id, StudentDTO.class);
        assertThat(actualStudentDTO).isNotNull();
    }

    @Test
    void testDeleteStudent() {
        StudentDTO testStudentDTO = restTemplate.postForObject(
                apiUrl(port),
                new StudentDTO()
                        .setName("test name")
                        .setAge(random.nextInt()),
                StudentDTO.class
        );
        long id = testStudentDTO.getId();
        restTemplate.postForObject(apiUrl(port), testStudentDTO, StudentDTO.class);

        restTemplate.delete(apiUrl(port) + id, StudentDTO.class);

//        assertThatExceptionOfType(StudentNotFoundException.class).isThrownBy(()->{
//            restTemplate.getForObject(apiUrl(port) + "/" + id, StudentDTO.class);
//        });
    }

    private final String apiUrl(int port) {
        return "http://localhost:" + port + "/students";
    }
}
