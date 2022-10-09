package ua.com.kalinichev.microservices.lab1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.kalinichev.microservices.lab1.models.Lesson;

@Repository
public interface LessonRepository extends CrudRepository<Lesson,Long> {
}
