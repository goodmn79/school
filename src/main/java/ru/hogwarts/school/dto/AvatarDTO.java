package ru.hogwarts.school.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AvatarDTO {
    private long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] bytes;
}
