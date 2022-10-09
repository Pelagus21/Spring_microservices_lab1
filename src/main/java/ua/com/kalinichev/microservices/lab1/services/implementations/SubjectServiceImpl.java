package ua.com.kalinichev.microservices.lab1.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.kalinichev.microservices.lab1.models.Lesson;
import ua.com.kalinichev.microservices.lab1.models.Specialty;
import ua.com.kalinichev.microservices.lab1.models.Subject;
import ua.com.kalinichev.microservices.lab1.repositories.SubjectRepository;
import ua.com.kalinichev.microservices.lab1.services.interfaces.SubjectService;
import ua.com.kalinichev.microservices.lab1.utils.Utils;

import java.util.*;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    private Utils processor;

    private static final String SUBJECT_WITH_ID_DOES_NOT_EXIST = "Subject with such id does not exist: ";
    //private static Logger logger = LogManager.getLogger(SubjectController.class);

    @Autowired
    public void setProcessor(Utils processor) {
        this.processor = processor;
    }


    @Override
    public Subject addSubject(String name, int quantOfGroups, Set<Specialty> specialties) {
        name = processor.processName(name);
        processor.checkSubjectName(name);
        processor.checkQuantOfGroups(quantOfGroups);
        processor.checkQuantOfSpecialties(specialties == null? 0 : specialties.size());
        Iterable<Subject> subjectsWithSuchName = subjectRepository.findByName(name);
        processor.checkSpecialties(subjectsWithSuchName, specialties);
        return subjectRepository.save(new Subject(name, quantOfGroups, specialties));
    }

    @Override
    public Subject addSubject(Subject subject) {
        subject.setId(-1L);
        return addSubject(subject.getName(), subject.getQuantOfGroups(), subject.getSpecialties());
    }

    @Transactional
    @Override
    public void deleteSubject(Long id) {
        if(!subjectExistsById(id)) throw new NoSuchElementException(SUBJECT_WITH_ID_DOES_NOT_EXIST + id);
        subjectRepository.deleteById(id);
    }

    @Override
    public Subject updateSubject(Long id, String name, int quantOfGroups, Set<Specialty> specialties) {
        name = processor.processName(name);
        processor.checkSubjectName(name);
        processor.checkQuantOfGroups(quantOfGroups);
        processor.checkQuantOfSpecialties(specialties == null? 0 : specialties.size());
        Iterable<Subject> subjectsWithSuchName = subjectRepository.findByNameAndNotId(id, name);
        processor.checkSpecialties(subjectsWithSuchName, specialties);
        String finalName = name;
        return subjectRepository.findById(id).map((subject) -> {
            if (nothingChanged(subject, finalName, quantOfGroups, specialties))
                return subject;
            subject.setName(finalName);
            subject.setQuantOfGroups(quantOfGroups);
            subject.setSpecialties(specialties);
            return subjectRepository.save(subject);
        }).orElseGet(() -> {
            return subjectRepository.save(new Subject(id, finalName, quantOfGroups, specialties));
        });
    }

    @Override
    public Subject updateSubject(Subject subject) {
        return updateSubject(subject.getId(), subject.getName(), subject.getQuantOfGroups(), subject.getSpecialties());
    }

    @Override
    public Subject updateSubjectNoCheck(Subject subject) {
        return subjectRepository.save(subject);
    }


    @Override
    public Iterable<Subject> getAll() {
        List<Subject> subjects = (List<Subject>)subjectRepository.findAll();
        Collections.sort(subjects);
        return subjects;
    }

    @Override
    public Subject getSubjectByName(String name) {
        Iterable<Subject> res = subjectRepository.findByName(name);
        if (!res.iterator().hasNext()) throw new NoSuchElementException("Subject with name '"+ name +"' has not been found!");
        return res.iterator().next();
    }

    @Override
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Subject with id '"+ id +"' has not been found!"));
    }

    @Override
    public boolean subjectExistsById(Long id) {
        return subjectRepository.existsById(id);
    }

    @Override
    public boolean subjectExistsByName(String name) {
        return subjectRepository.existsByName(name);
    }

    private boolean nothingChanged(Subject subject, String name, int quantOfGroups, Set<Specialty> specialties) {
        return subject.getName().equals(name) && subject.getQuantOfGroups() == quantOfGroups
                && subject.getSpecialties().equals(specialties);
    }

    @Override
    public Set<Integer> getLessonWeeks(Long id) {
        Subject sbj = this.getSubjectById(id);
        SortedSet<Integer> set = new TreeSet<Integer>();
        for(Lesson less : sbj.getLessons())
            set.addAll(less.getIntWeeks());
        return set;
    }

    @Override
    public Set<Integer> getLessonWeeks(Set<Long> ids) {
        SortedSet<Integer> set = new TreeSet<>();
        for(Long id : ids)
            set.addAll(this.getLessonWeeks(id));
        return set;
    }

}
