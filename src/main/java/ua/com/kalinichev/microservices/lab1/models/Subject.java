package ua.com.kalinichev.microservices.lab1.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.com.kalinichev.microservices.lab1.utils.EntityIdResolver;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@ToString
public class Subject implements Comparable<Subject> {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotBlank(message = "Field shouldn't be empty!")
    @NotNull(message = "Mandatory field!")
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    @Min(1)
    @Max(30)
    @NotNull(message = "Mandatory field!")
    private int quantOfGroups;

    @Getter
    @Setter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Teacher.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER)
    private Set<Teacher> teachers;


    @Getter
    @Setter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Specialty.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subjects_specialties",
            joinColumns = @JoinColumn(name = "subject_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "specialty_id", nullable = false)
    )
    @NotNull(message = "Mandatory field!")
    private Set<Specialty> specialties;


    @Getter
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Lesson.class,
            resolver = EntityIdResolver.class)
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    public Subject() {}

    public Subject(String name, int quantOfGroups) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = new HashSet<>();
    }

    public Subject(String name, int quantOfGroups, Set<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = specialties;
    }

    public Subject(long id, String name, int quantOfGroups, Set<Specialty> specialties) {
        this.name = name;
        this.quantOfGroups = quantOfGroups;
        this.teachers = new HashSet<>();
        this.specialties = specialties;
    }

    public void addTeacher(Teacher t) {
        teachers.add(t);
    }
    public void addSpecialty(Specialty sp) {
        specialties.add(sp);
    }

    public boolean hasSpecialty(Specialty sp) {
        return specialties.contains(sp);
    }

    public boolean hasSpecialty(Long spId) {
        for (Specialty s: specialties) {
            if (s.getId().equals(spId))
                return true;
        }
        return false;
    }

    public boolean hasOnlyOneSpecialty() {
        return specialties.size() == 1;
    }

    @Override
    public int compareTo(Subject o) {
        int cmp = getName().compareTo(o.getName());
        if (cmp == 0) {
            return getQuantOfGroups() - o.getQuantOfGroups();
        }
        return cmp;
    }
}
