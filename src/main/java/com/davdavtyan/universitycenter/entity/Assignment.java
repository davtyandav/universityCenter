package com.davdavtyan.universitycenter.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; // ИЗМЕНЕНИЕ ЗДЕСЬ
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    // Подробное описание конкретной задачи
    private String description;

    // Срок сдачи (дедлайн) для этой конкретной задачи
    private LocalDateTime deadline;

    // Прикрепленный файл к этой задаче от ментора
    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    // === ИЗМЕНЕНИЕ ЗДЕСЬ ===
    // Многие задачи относятся к одному уроку
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    // Ответы студентов именно на эту задачу
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentSubmission> submissions = new ArrayList<>();
}