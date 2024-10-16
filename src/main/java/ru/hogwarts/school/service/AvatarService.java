package ru.hogwarts.school.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.write;
import static ru.hogwarts.school.mapper.AvatarMapper.mapToAvatarDTO;

@Service
@Transactional
@RequiredArgsConstructor
public class AvatarService {
    @Value("${avatars.folder.path}")
    private String avatarDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final EntityManager entityManager;

    public void upload(long studentId, MultipartFile multipartFile) throws IOException {
        if (studentService.getById(studentId) == null) throw new StudentNotFoundException();
        final Student student = entityManager.getReference(Student.class, studentId);
        final String file = avatarDir + getFileName(student, Objects.requireNonNull(multipartFile.getOriginalFilename()));
        final Path path = Path.of(file);

        Avatar avatar = avatarRepository.findByStudentId(studentId)
                .orElse(new Avatar())
                .setMediaType(multipartFile.getContentType())
                .setBytes(multipartFile.getBytes())
                .setStudent(student)
                .setFilePath(file)
                .setFileSize(multipartFile.getSize());
        avatarRepository.save(avatar);

        if (Files.notExists(path.getParent())) createDirectory(path);
        write(path, multipartFile.getBytes());
    }

    public AvatarDTO getFromBD(long studentId) {
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseThrow(AvatarNotFoundException::new);
        return mapToAvatarDTO(avatar);
    }

    public AvatarDTO getFromDir(long studentId) {
        final Student student = entityManager.getReference(Student.class, studentId);
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseThrow(AvatarNotFoundException::new);
        return mapToAvatarDTO(avatar);
    }

    private String getFileName(Student student, String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return "student_id_" + student.getId() + extension;
    }
}
