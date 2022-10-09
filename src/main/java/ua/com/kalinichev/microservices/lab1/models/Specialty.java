package ua.com.kalinichev.microservices.lab1.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Specialty implements Comparable<Specialty> {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotBlank(message = "Specialty name must not be blank")
    private String name;

    @Getter
    @Setter
    @Column(name="SPEC_YEAR", nullable = false)
    @Min(1)
    @Max(6)
    private int year;

    @Getter
    @Setter
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "specialties", fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();

    public Specialty() { }

    public Specialty(Long id, String name, int year) {
        this.name = name;
        this.id = id;
        this.year = year;
    }


    public Specialty(String name, int year) {
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + '-' + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specialty)) return false;
        Specialty specialty = (Specialty) o;
        return getYear() == specialty.getYear() &&
                getId().equals(specialty.getId()) &&
                getName().equals(specialty.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getYear());
    }


    @Override
    public int compareTo(Specialty o) {
        int cmp = getName().compareTo(o.getName());
        if (cmp == 0) return getYear()-o.getYear();
        return cmp;
    }

}
