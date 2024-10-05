package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity
@Table(name = "faculty")
@Data
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Collection<Student> students;

}
