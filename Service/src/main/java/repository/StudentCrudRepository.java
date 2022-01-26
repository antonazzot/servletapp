package repository;

import org.springframework.data.repository.CrudRepository;
import users.Student;
import users.UserImpl;

import java.util.List;

public interface StudentCrudRepository extends CrudRepository<Student, Integer> {
    List<Student> findAll ();
}
