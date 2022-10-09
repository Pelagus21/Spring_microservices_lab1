package ua.com.kalinichev.microservices.core.utils;

import ua.com.kalinichev.microservices.core.models.Specialty;
import ua.com.kalinichev.microservices.core.models.Subject;

import java.util.Set;

public interface Utils {

    String processName(String name);

    boolean isInvalidName(String name);

    void checkName(String name);

    void checkSubjectName(String name);

    void checkTeacherName(String name);

    public Long getUniqueId();

    void checkYear(int year);

    void checkQuantOfGroups(int quantOfGroups);

    void checkQuantOfSpecialties(int quantOfSpecialties);

    void checkSpecialties(Iterable<Subject> subjects, Set<Specialty> specialties);

}
