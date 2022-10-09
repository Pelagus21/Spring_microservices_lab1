package ua.com.kalinichev.microservices.core.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.kalinichev.microservices.core.exceptions.InvalidArgumentsException;
import ua.com.kalinichev.microservices.core.models.*;
import ua.com.kalinichev.microservices.core.repositories.LessonRepository;
import ua.com.kalinichev.microservices.core.repositories.SubjectRepository;
import ua.com.kalinichev.microservices.core.repositories.TeacherRepository;
import ua.com.kalinichev.microservices.core.services.interfaces.LessonService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private static final String LESSON_WITH_ID_DOES_NOT_EXIST = "Lesson with such id does not exist: ";

    @Override
    public Lesson addLesson(Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek dayOfWeek) {
        Object[] res = verifyAndProcessData(subjId,teachId,weeks,room);
        return lessonRepository.save(new Lesson(time,(Subject) res[1],(Teacher) res[2],subjectType,weeks,(Room) res[0],dayOfWeek));
    }


    @Override
    public Lesson addLesson(Lesson lesson) {
        lesson.setId(-1L);
        return lessonRepository.save(lesson);
    }

    @Override
    public boolean lessonExistsById(Long id) {
        return lessonRepository.existsById(id);
    }


    @Transactional
    @Override
    public void deleteLesson(Long id) throws NoSuchElementException {
        if(!lessonExistsById(id)){
            throw new NoSuchElementException(LESSON_WITH_ID_DOES_NOT_EXIST + id);
        }
        lessonRepository.deleteById(id);
    }

    @Override
    public Lesson updateLesson(Long id, Lesson.Time time, Long subjId, Long teachId, SubjectType subjectType, String weeks, String room, DayOfWeek dayOfWeek) {
        Object[] res = verifyAndProcessData(subjId,teachId,weeks,room);
        return lessonRepository.save(new Lesson(id,time,(Subject) res[1],(Teacher) res[2],subjectType,weeks,(Room) res[0],dayOfWeek));
    }

   @Override
    public Lesson updateLesson(Lesson lesson) throws NoSuchElementException {
        if(!lessonExistsById(lesson.getId())) throw new NoSuchElementException(LESSON_WITH_ID_DOES_NOT_EXIST + lesson.getId());
        return lessonRepository.save(lesson);
    }

   @Override
    public Lesson getLessonById(Long id) throws NoSuchElementException{
        return lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException(LESSON_WITH_ID_DOES_NOT_EXIST + id));
    }

    @Override
    public Iterable<Lesson> getAll() {
        return lessonRepository.findAll();
    }


    private Object[] verifyAndProcessData(Long subjId, Long teachId, String weeks, String room){
        Room r;
        if(room.equals("remotely")) r = new Room();
        else r = new Room(room);

        if(weeks.isEmpty() || !weeks.matches("^([1-9][0-9]*(-[1-9][0-9]*)?)(,([1-9][0-9]*(-[1-9][0-9]*)?))*$") || !checkWeeksAscending(weeks)){
            throw new InvalidArgumentsException("Invalid weeks format");
        }

        Optional<Subject> s = subjectRepository.findById(subjId);
        Optional<Teacher> t = teacherRepository.findById(teachId);

        if(s.isEmpty()){
            throw new NoSuchElementException("Subject with id \""+subjId+"\" not found!");
        }
        if(t.isEmpty()){
            throw new NoSuchElementException("Teacher with id \""+subjId+"\" not found!");
        }
        return new Object[]{r,s.get(),t.get()};
    }

    private boolean checkWeeksAscending(String weeks){
        List<Integer> w = Stream.of(weeks.split("[,-]")).map(Integer::parseInt).collect(Collectors.toList());
        boolean ok = true;
        for (int i = 1; i < w.size(); i++) {
            if(w.get(i)<=w.get(i-1)) {
                ok = false;
                break;
            }
        }
        return ok;
    }

}
