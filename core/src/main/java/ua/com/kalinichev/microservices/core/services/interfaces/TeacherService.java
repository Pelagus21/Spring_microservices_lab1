package ua.com.kalinichev.microservices.core.services.interfaces;

import ua.com.kalinichev.microservices.core.models.Subject;
import ua.com.kalinichev.microservices.core.models.Teacher;

import java.util.NoSuchElementException;
import java.util.Set;

public interface TeacherService {

    Teacher addTeacher(String name, Set<Subject> subjects);

    Teacher addTeacher(Teacher teacher);

    boolean teacherExistsById(Long id);

    boolean teacherExistsByName(String name);

    boolean deleteTeacher(Long id) throws NoSuchElementException;

    boolean updateTeacher(Long id, String name);

    Teacher updateTeacher(Teacher teacher) throws NoSuchElementException;

    Teacher updateTeacherNoCheck(Teacher teacher);

    Teacher getTeacherById(Long id) throws Exception;

    Iterable<Teacher> getTeacherByPartName(String name) throws Exception;

    Iterable<Teacher> getAll();
}
