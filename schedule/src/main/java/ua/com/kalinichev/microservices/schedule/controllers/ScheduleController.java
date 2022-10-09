package ua.com.kalinichev.microservices.schedule.controllers;

import ua.com.kalinichev.microservices.core.models.Lesson;
import ua.com.kalinichev.microservices.core.models.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.kalinichev.microservices.schedule.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService service;

    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @GetMapping("/teacher/{id}")
    public List<Lesson> getTeacherSchedule(@PathVariable(value = "id") Long id) throws Throwable {
        return this.service.getTeacherLessons(id);
    }

    @GetMapping("/specialty/{id}")
    public List<Lesson> getSpecialtySchedule(@PathVariable(value = "id") Long id) throws Throwable {
        return this.service.getSpecialtyLessons(id);
    }

    @GetMapping("/subject/{id}")
    public Iterable<Lesson> getSubjectSchedule(@PathVariable(value = "id") Long id) {
        return this.service.getSubjectLessons(id);
    }

    @GetMapping("/teacherSubjects/{id}")
    public Iterable<Subject> getTeacherSubjects(@PathVariable(value = "id") Long id) throws Throwable {
        return this.service.getTeacherSubjects(id);
    }

    @GetMapping("/specialtySubjects/{id}")
    public Iterable<Subject> getSpecialtySubjects(@PathVariable(value = "id") Long id) throws Throwable {
        return this.service.getSpecialtySubjects(id);
    }
}
