package repository.modelrepository.modelservices.crudrepositorislikeservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import users.Role;
import users.Student;
import users.User;
import users.UserImpl;

import java.util.List;
import java.util.Optional;

public interface StudentCrudRepository  extends CrudRepository <Student, Integer> {

    List<Student> findByrole(Role role);

    Optional <Student> findById (Integer id);


    void delete(UserImpl entity);
    @Modifying
    @Query("update Student s set s.name = ?2 where s.id = ?1")
    void setUserByIdWithName  (Integer Id, String Name);

//    @Query("select s from Student s where s.id = ?1")
//    List<Student> findByGroupId (Integer GroupId);
}
