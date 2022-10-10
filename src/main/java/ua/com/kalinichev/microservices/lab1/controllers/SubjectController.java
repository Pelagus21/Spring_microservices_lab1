package ua.com.kalinichev.microservices.lab1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.kalinichev.microservices.lab1.models.Subject;
import ua.com.kalinichev.microservices.lab1.services.interfaces.SubjectService;

import javax.validation.Valid;

@RestController
@RequestMapping("/core/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public Iterable<Subject> getSubjects(){
        return subjectService.getAll();
    }

    @GetMapping("/byName/{name}")
    public Subject getSubject(@PathVariable("name") String name){
        return subjectService.getSubjectByName(name);
    }

    @GetMapping("/{id}")
    public Subject getSubject(@PathVariable("id") Long id) {
        return subjectService.getSubjectById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubject(id);
    }

    @PostMapping("/add")
    public Subject addSubject(@Valid @RequestBody Subject subject){
        return subjectService.addSubject(subject);
    }

    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable("id") Long id, @Valid @RequestBody Subject subject) {
        subject.setId(id);
        return subjectService.updateSubject(subject);
    }

}
