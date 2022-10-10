package ua.com.kalinichev.microservices.lab1.services.interfaces;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import ua.com.kalinichev.microservices.lab1.models.Lesson;
import ua.com.kalinichev.microservices.lab1.models.Specialty;
import ua.com.kalinichev.microservices.lab1.models.Subject;


import java.util.List;

public interface SpecialtyService {

    Specialty addSpecialty(String name, int year);

    void deleteSpecialty(Long id);

    Specialty updateSpecialty(long id, String name, int year);

    Iterable<Specialty> getAll();

    Specialty getSpecialty(Long id);

    void deleteAll();

    Specialty addSpecialty(String name, int year, JSONArray subjects);

    Iterable<Subject> getSpecialtySubjects(Long specialtyId);

    List<Lesson> getSpecialtyLessons(Long id);
}
