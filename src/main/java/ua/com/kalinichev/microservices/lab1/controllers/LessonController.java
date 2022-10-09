package ua.com.kalinichev.microservices.lab1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.com.kalinichev.microservices.lab1.models.Lesson;
import ua.com.kalinichev.microservices.lab1.services.interfaces.LessonService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> getAllLessons() {
        return (List<Lesson>) lessonService.getAll();
    }


    @GetMapping("/{id}")
    public Lesson getLessonById(@PathVariable(value = "id") Long id) throws Exception {
        return lessonService.getLessonById(id);
    }

    @PostMapping("/add")
    public Lesson addLesson(@Valid @RequestBody Lesson lesson){
        return lessonService.addLesson(lesson);
    }

    @PutMapping("/{id}")
    public Lesson updateLesson(@PathVariable(value = "id") Long id, @Valid @RequestBody Lesson lesson) throws NoSuchElementException {
        lesson.setId(id);
        return lessonService.updateLesson(lesson);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteLesson(@PathVariable(value = "id") Long id) throws NoSuchElementException {
        lessonService.deleteLesson(id);
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted",true);
        return result;
    }

}
