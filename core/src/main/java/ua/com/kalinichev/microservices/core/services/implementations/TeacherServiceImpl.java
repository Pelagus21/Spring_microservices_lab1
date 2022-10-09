package ua.com.kalinichev.microservices.core.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.kalinichev.microservices.core.models.Subject;
import ua.com.kalinichev.microservices.core.models.Teacher;
import ua.com.kalinichev.microservices.core.repositories.TeacherRepository;
import ua.com.kalinichev.microservices.core.services.interfaces.TeacherService;
import ua.com.kalinichev.microservices.core.utils.Utils;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;

    private static final String TEACHER_WITH_ID_DOES_NOT_EXIST = "Teacher with such id does not exist: ";
    //private static Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

    @Autowired
    private Utils processor;

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher addTeacher(String name, Set<Subject> subjects) {
        name = processor.processName(name);
        processor.checkTeacherName(name);
//        processor.checkTeachersSubjects(subjects);
        return teacherRepository.save(new Teacher(name, subjects));
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        return addTeacher(teacher.getName(), teacher.getSubjects());
    }

    @Override
    public boolean teacherExistsById(Long id) {
        return teacherRepository.existsById(id);
    }

    @Override
    public boolean teacherExistsByName(String name) {
        return teacherRepository.existsByName(name);
    }

    @Transactional
    @Override
    public boolean deleteTeacher(Long id) throws NoSuchElementException {
        if(!teacherExistsById(id)) throw new NoSuchElementException(TEACHER_WITH_ID_DOES_NOT_EXIST + id);
        teacherRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateTeacher(Long id, String name) {
        teacherRepository.save(new Teacher(id, name));
        return true;
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) throws NoSuchElementException {
        if(!teacherExistsById(teacher.getId())) throw new NoSuchElementException(TEACHER_WITH_ID_DOES_NOT_EXIST + teacher.getId());
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacherNoCheck(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Long id) throws Exception{
        return teacherRepository.findById(id).orElseThrow(() -> new NoSuchElementException(TEACHER_WITH_ID_DOES_NOT_EXIST + id));
    }

    @Override
    public Iterable<Teacher> getTeacherByPartName(String name) throws Exception {
        return teacherRepository.findByPartName(name);
    }


    @Override
    public Iterable<Teacher> getAll() {
        return teacherRepository.findAll();
    }

}
