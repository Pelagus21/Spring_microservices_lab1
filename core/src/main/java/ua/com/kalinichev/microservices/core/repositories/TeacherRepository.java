package ua.com.kalinichev.microservices.core.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.kalinichev.microservices.core.models.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    Iterable<Teacher> findByName(String s);

    @Query("select case when count(t)> 0 then true else false end from Teacher t where lower(t.name) like lower(concat('%', :name,'%'))")
    boolean existsByName(@Param("name") String name);

    @Query("select t from Teacher t where lower(t.name) like lower(concat('%', :name,'%'))")
    Iterable<Teacher> findByPartName(@Param("name") String name);
}
