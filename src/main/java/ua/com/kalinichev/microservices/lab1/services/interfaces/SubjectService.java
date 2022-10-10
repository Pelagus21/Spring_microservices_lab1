package ua.com.kalinichev.microservices.lab1.services.interfaces;


import ua.com.kalinichev.microservices.lab1.models.Specialty;
import ua.com.kalinichev.microservices.lab1.models.Subject;

import java.util.Set;

public interface SubjectService {

    Subject addSubject(String name, int quantOfGroups, Set<Specialty> specialties);

    Subject addSubject(Subject subject);

    void deleteSubject(Long id);

    Subject updateSubject(Long id, String name, int quantOfGroups, Set<Specialty> specialties);

    Subject updateSubject(Subject subject);

    Subject updateSubjectNoCheck(Subject subject);

    Iterable<Subject> getAll();

    Subject getSubjectByName(String name);

    Subject getSubjectById(Long id);

    boolean subjectExistsById(Long id);

    boolean subjectExistsByName(String name);

    Set<Integer> getLessonWeeks(Long id);

    Set<Integer> getLessonWeeks(Set<Long> ids);

}
