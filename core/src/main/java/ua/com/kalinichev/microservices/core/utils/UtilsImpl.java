package ua.com.kalinichev.microservices.core.utils;

import ua.com.kalinichev.microservices.core.models.Specialty;
import ua.com.kalinichev.microservices.core.models.Subject;

import java.util.Random;
import java.util.Set;

public class UtilsImpl  implements Utils {

    private static final int MIN_YEAR = 1;
    private static final int MAX_YEAR = 4;
    private static final int MIN_QUANT_OF_GROUPS = 1;
    private static final int MAX_QUANT_OF_GROUPS = 10;
    private static final int MIN_QUANT_OF_SPECIALTIES_ON_SUBJECT = 1;

    @Override
    public String processName(String name) {
        return name.replaceAll("\\s+", " ").trim();
    }

    @Override
    public boolean isInvalidName(String name) {
        return name.isEmpty();
    }

    @Override
    public void checkName(String name) {
        if (isInvalidName(name)) {
            throw new RuntimeException("Invalid specialty name");
        }
    }

    @Override
    public void checkSubjectName(String name) {
        if (isInvalidName(name)) {
            throw new RuntimeException("Invalid subject name");
        }
    }

    @Override
    public void checkTeacherName(String name) {
        // check
    }

    @Override
    public Long getUniqueId() {
        return new Random().nextLong() + System.currentTimeMillis();
    }

    @Override
    public void checkYear(int year) {
        if (year > MAX_YEAR || year < MIN_YEAR) {
            throw new RuntimeException("Specialty year = " + year + " is out of bounds " + MIN_YEAR + " - " + MAX_YEAR);
        }
    }

    @Override
    public void checkQuantOfGroups(int quantOfGroups) {
        if (quantOfGroups > MAX_QUANT_OF_GROUPS || quantOfGroups < MIN_QUANT_OF_GROUPS) {
            throw new RuntimeException("Subject quantity of groups = " + quantOfGroups + " is out of bounds " + MIN_QUANT_OF_GROUPS + " - " + MAX_QUANT_OF_GROUPS);
        }
    }

    @Override
    public void checkQuantOfSpecialties(int quantOfSpecialties) {
        if (quantOfSpecialties < MIN_QUANT_OF_SPECIALTIES_ON_SUBJECT) {
            throw new RuntimeException("Subject quantity of specialties = " + quantOfSpecialties + " is incorrect - less than " + MIN_QUANT_OF_GROUPS);
        }
    }

    @Override
    public void checkSpecialties(Iterable<Subject> subjects, Set<Specialty> specialties) {
        for (Subject subject : subjects) {
            for (Specialty specialty : subject.getSpecialties()) {
                if (specialties.contains(specialty))
                    throw new RuntimeException("Subject with such name already exists on specialty " + specialty);
            }
        }
    }
}
