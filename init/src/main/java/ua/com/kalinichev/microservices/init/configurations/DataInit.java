package ua.com.kalinichev.microservices.init.configurations;

import ua.com.kalinichev.microservices.core.models.*;
import ua.com.kalinichev.microservices.core.repositories.LessonRepository;
import ua.com.kalinichev.microservices.core.repositories.SpecialtyRepository;
import ua.com.kalinichev.microservices.core.repositories.SubjectRepository;
import ua.com.kalinichev.microservices.core.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class DataInit implements ApplicationRunner {

    @Autowired
    private TeacherRepository teacherRepo;
    @Autowired
    private SpecialtyRepository specialtyRepo;
    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private LessonRepository lessonRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertSpecialties();
        insertSubjects();
        insertTeachers();
        insertLessons();
    }

    private void insertSubjects() {
        Subject s = new Subject("Subject 1", 3);
        Subject s1 = new Subject("Subject 2", 4);
        Subject s2 = new Subject("Subject 3", 7);

        Specialty sp1 = specialtyRepo.findByNameAndYear("IPZ", 3).iterator().next();
        Specialty sp2 = specialtyRepo.findByNameAndYear("IPZ", 4).iterator().next();
        Specialty sp3 = specialtyRepo.findByNameAndYear("KN", 3).iterator().next();

        s.addSpecialty(sp2);
        s.addSpecialty(sp1);
        s1.addSpecialty(sp2);
        s1.addSpecialty(sp3);
        s2.addSpecialty(sp3);

        subjectRepo.save(s);
        subjectRepo.save(s1);
        subjectRepo.save(s2);
    }

    private void insertSpecialties() {
        Specialty sp1 =  new Specialty("IPZ",3);
        Specialty sp2 =  new Specialty("IPZ",4);
        Specialty sp3 =  new Specialty("KN",3);

        specialtyRepo.save(sp1);
        specialtyRepo.save(sp2);
        specialtyRepo.save(sp3);
    }

    private void insertTeachers() {
        Teacher t = new Teacher("Teacher 1");
        Teacher t1 = new Teacher("Teacher 2");
        Teacher t2 = new Teacher("Teacher 3");

        Subject subject = subjectRepo.findByName("Subject 1").iterator().next();
        Subject subject1 = subjectRepo.findByName("Subject 2").iterator().next();

        t.addSubject(subject);
        t.addSubject(subject1);

        t1.addSubject(subject);
        t2.addSubject(subject1);

        teacherRepo.save(t);
        teacherRepo.save(t1);
        teacherRepo.save(t2);
    }

    private void insertLessons() {
        Subject s = subjectRepo.findByName("Subject 1").iterator().next();
        Subject s2 = subjectRepo.findByName("Subject 2").iterator().next();

        Teacher t1 = teacherRepo.findByName("Teacher 1").iterator().next();
        Teacher t2 = teacherRepo.findByName("Teacher 2").iterator().next();
        Teacher t3 = teacherRepo.findByName("Teacher 3").iterator().next();

        Lesson l1 = new Lesson(Lesson.Time.TIME1, s, t1, new SubjectType(0), "1-15", new Room("215"), DayOfWeek.MONDAY);
        Lesson l2 = new Lesson(Lesson.Time.TIME2, s, t2, new SubjectType(1), "1-15", new Room("216"), DayOfWeek.MONDAY);
        Lesson l3 = new Lesson(Lesson.Time.TIME3, s2, t3, new SubjectType(2), "1-15", new Room("216"), DayOfWeek.MONDAY);
        Lesson l4 = new Lesson(Lesson.Time.TIME3, s2, t1, new SubjectType(2), "5-15", new Room("303"), DayOfWeek.FRIDAY);

        lessonRepo.save(l1);
        lessonRepo.save(l2);
        lessonRepo.save(l3);
        lessonRepo.save(l4);
    }
}
