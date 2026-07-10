package com.davdavtyan.universitycenter.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Entity(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isCompleted;

    // dasi orer@
    private LocalDateTime data;

    // dsi skselu jam@
    private LocalDateTime startDate;

    //dasi avartvelu jam@
    private LocalDateTime endDate;

    // Тема конкретного урока (например: "Введение в Spring Boot")
    private String title;

    // === ИЗМЕНЕНИЕ ЗДЕСЬ ===
    // Теперь у одного урока может быть МНОГО задач
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "lesson_descriptor_id")
    private LessonDescriptor lessonDescriptor;

    // Связь с посещаемостью этого урока
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();

}
