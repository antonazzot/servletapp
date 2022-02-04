package repository.threadmodelrep.threadservices.crudthreadservices;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;

import java.util.List;

public interface MarkCrudRepository extends CrudRepository <Mark, Integer> {

    List<Mark> findByTheamsAndStudent(Theams theam, Student student);
    @Modifying
    @Query("update Mark m set m.valuesOfMark = ?2 where m.id = ?1")
    void setMarkById(Integer Id, Integer valuesOfMark);
}
