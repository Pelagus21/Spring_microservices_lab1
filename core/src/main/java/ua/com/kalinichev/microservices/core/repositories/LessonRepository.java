package ua.com.kalinichev.microservices.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.kalinichev.microservices.core.models.Lesson;

@Repository
public interface LessonRepository extends CrudRepository<Lesson,Long> {
}
