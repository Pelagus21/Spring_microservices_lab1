package ua.com.kalinichev.microservices.lab1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher implements Comparable<Teacher>{

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotNull
    private String name;

    @Getter
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "subject_id", nullable = false))
    @NotNull
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<Lesson> lessons;

    public Teacher(String name) {
        this.name = name;
        subjects = new HashSet<>();
    }

    public Teacher() {}

    public Teacher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher(String name, Set<Subject> subjects) {
        this.subjects = subjects;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    @Override
    public int compareTo(Teacher that) {
        return this.name.compareTo(that.name);
    }
}
