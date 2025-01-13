package com.davdavtyan.universitycenter.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "lesson-descriptor")
public class LessonDescriptor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime data;

    @Enumerated(value = EnumType.STRING)
    private LessonType type;

    @Enumerated(value = EnumType.STRING)
    private LessonDayType dayType;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @OneToMany(mappedBy = "lessonDescriptor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

}