package repository.modelrepository.modelservices.crudrepositorislikeservice;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import users.Role;
import users.Student;
import users.Trainer;

import java.util.List;

public interface TrainerCrudRepository extends CrudRepository <Trainer, Integer> {

    List<Trainer> findByrole(Role role);
    @Modifying
    Trainer save (Trainer trainer);
}
