package com.davdavtyan.universitycenter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "assignment_submissions")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Текст ответа студента (комментарий или ссылка на репозиторий)
    private String studentComment;

    // Загруженный файл с решением (архив с кодом, документ и т.д.)
    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity studentFile;

    // Время, когда студент отправил работу
    private LocalDateTime submittedAt;

    // Оценка ментора (например, числовая или символьная)
    private String grade;

    // Фидбек/комментарий от ментора по поводу работы
    private String mentorFeedback;
}