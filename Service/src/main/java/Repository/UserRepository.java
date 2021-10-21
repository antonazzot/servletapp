package Repository;

import Users.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List <User> allUser ();
    List <Trainer> allTrainer ();
    List <Student>  allStudent ();
    List <Administrator> allAdmin ();
    Optional <UserImpl> getUserById (Integer id);
    UserImpl saveUser (UserImpl user);
    Optional <UserImpl> removeUser (Integer id);
 }
