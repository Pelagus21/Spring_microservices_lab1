package ua.com.kalinichev.microservices.core.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.kalinichev.microservices.core.exceptions.ElementExistsException;
import ua.com.kalinichev.microservices.core.models.Lesson;
import ua.com.kalinichev.microservices.core.models.Specialty;
import ua.com.kalinichev.microservices.core.models.Subject;
import ua.com.kalinichev.microservices.core.repositories.SpecialtyRepository;
import ua.com.kalinichev.microservices.core.services.interfaces.SpecialtyService;
import ua.com.kalinichev.microservices.core.services.interfaces.SubjectService;
import ua.com.kalinichev.microservices.core.utils.Utils;

import java.util.*;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private SpecialtyRepository specialtyRepository;

    @Autowired
    private SubjectService subjectService;

    private Utils processor;

    private static final String SPEC_NOT_EXISTS_MESSAGE_ID = "Specialty with such id does not exist: ";

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }

    @Autowired
    public void setSpecialtyRepository(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Specialty addSpecialty(String name, int year) {
        name = processor.processName(name);
        processor.checkName(name);
        processor.checkYear(year);
        if(specialtyRepository.existsByNameAndYear(name, year)) {
            throw new ElementExistsException("This specialty already exists");
        }
        return specialtyRepository.save(new Specialty(name,year));
    }


    private void deleteSubjects(Long spId) {
        Iterable<Subject> subjects = subjectService.getAll();
        for (Subject s: subjects) {
            if (s.hasOnlyOneSpecialty() && s.hasSpecialty(spId)) {
                subjectService.deleteSubject(s.getId());
            }
        }
    }

    @Transactional
    @Override
    public void deleteSpecialty(Long id) {
        if(specialtyRepository.existsById(id)) {
            specialtyRepository.deleteById(id);
            deleteSubjects(id);
        }
        else throw new NoSuchElementException(SPEC_NOT_EXISTS_MESSAGE_ID + id);
    }

    @Override
    public Specialty updateSpecialty(long id, String name, int year) {
        name = processor.processName(name);
        processor.checkName(name);
        processor.checkYear(year);
        if(specialtyRepository.existsByNameAndYearAndNotId(id,name,year)){

            throw new ElementExistsException("Such specialty already exists");
        }
        String finalName = name;
        return specialtyRepository.findById(id).map((specialty) -> {
            if(nothingChanged(specialty,finalName,year)) {
                return specialty;
            }
            specialty.setName(finalName);
            specialty.setYear(year);
            return specialtyRepository.save(specialty);
        }).orElseGet(() -> {
            return specialtyRepository.save(new Specialty(id,finalName,year));
        });
    }

    private boolean nothingChanged(Specialty specialty, String name, int year) {
        return specialty.getName().equals(name)&&specialty.getYear()==year;
    }

    @Override
    public Iterable<Specialty> getAll() {
        List<Specialty> specialties = (List<Specialty>)specialtyRepository.findAll();
        Collections.sort(specialties);
        return specialties;
    }

    @Override
    public Specialty getSpecialty(Long id) {
        return specialtyRepository.findById(id).orElseThrow(() -> new NoSuchElementException("" + id));
    }


    @Override
    public void deleteAll() {
        for (Specialty s : getAll()) {
            specialtyRepository.deleteById(s.getId());
            deleteSubjects(s.getId());
        }
    }

    @Override
    public Specialty addSpecialty(String name, int year, JSONArray subjectIds) {
        Set<Subject> subjects = new HashSet<>();
        for(int i = 0; i < subjectIds.length(); i++){
            try {
                subjects.add(subjectService.getSubjectById(subjectIds.getLong(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Specialty specialty = addSpecialty(name,year);
        subjects.forEach(s -> {s.addSpecialty(specialty);
        subjectService.updateSubjectNoCheck(s);});
        specialty.setSubjects(subjects);
        return specialty;
    }

    @Override
    public Iterable<Subject> getSpecialtySubjects(Long specialtyId) {
        return getSpecialty(specialtyId).getSubjects();
    }

    @Override
    public List<Lesson> getSpecialtyLessons(Long id) {
        List<Lesson> lessons = new ArrayList<Lesson>();
        for (Subject s : getSpecialtySubjects(id)) {
            lessons.addAll(s.getLessons());
        }
        return lessons;
    }

}
