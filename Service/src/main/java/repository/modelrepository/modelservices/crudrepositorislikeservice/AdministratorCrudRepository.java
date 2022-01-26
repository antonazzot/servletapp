package repository.modelrepository.modelservices.crudrepositorislikeservice;

import org.springframework.data.repository.CrudRepository;
import users.Administrator;
import users.Role;
import users.Student;

import java.util.List;

public interface AdministratorCrudRepository extends CrudRepository <Administrator, Integer> {

    List<Administrator> findByrole(Role role);
}
