package ua.com.kalinichev.microservices.lab1.services.interfaces;

import ua.com.kalinichev.microservices.lab1.models.Lesson;
import ua.com.kalinichev.microservices.lab1.models.SubjectType;

import java.time.DayOfWeek;
import java.util.NoSuchElementException;

public interface LessonService {

    Lesson getLessonById(Long id) throws NoSuchElementException;

    Iterable<Lesson> getAll();

    Lesson addLesson(Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek of) throws Exception;
    Lesson addLesson(Lesson lesson);

    Lesson updateLesson(Long id, Lesson.Time value, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek of) throws Exception;
    Lesson updateLesson(Lesson lesson) throws NoSuchElementException;

    void deleteLesson(Long id) throws NoSuchElementException;

    boolean lessonExistsById(Long id);

}
