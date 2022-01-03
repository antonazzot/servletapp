
package controller.restcontrollers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryFactory;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import users.Role;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/mvc/rest/user")

public class UsersRestController {

    @GetMapping(path = "/users/{entity}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Map<Integer, UserImpl> users (@PathVariable String entity) {
        log.info("in res1t {}", "-------->rest");
        return  mapToChange(entity);
    }

    @GetMapping(path = "/trainer/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public ResponseEntity <UserImpl> getUserById (@PathVariable int id) {
        return ResponseEntity.ok(RepositoryFactory.getRepository().getUserById(id));
    }

    @PostMapping (path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserImpl> createUser  (@RequestBody UserImpl user) {
        log.info("in rest {}", "-------->rest");
        log.info("in rest savwe {}", "user for save" + user.getInf());
        log.info("in rest USER ROLE IN begin {}", "ROLE" + user.getRole());
        log.info("in rest USER ROLE afterChekker {}", "ROLE"  + RoleIDParametrCheker.getRoleByString(user.getRole().name()));
        UserImpl userForSave = new Student()
                .withName(user.getName())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withRole(Role.valueOf(user.getRole().name()))
                .withAge(user.getAge());
        log.info("in rest savwe {}", "user for save" + userForSave.getInf());
        RepositoryFactory.getRepository().saveUser(userForSave);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Optional<UserImpl>> deleteUser (@RequestParam ("id") int id, @RequestParam ("entity") String entity) {
        log.info("Delete param ={}", "id: " + id + " entity " + entity);
        Optional<UserImpl> user = RepositoryFactory.getRepository().removeUser(id, entity);
        return  ResponseEntity.ok(user);
    }

    @PatchMapping (path = "patch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserImpl> updateUser (@RequestBody UserImpl user) {
        UserImpl userForUpdate = new UserImpl().withId(user.getId());
        log.info("Userfor update before ={}", user.getInf());
        userForUpdate.withRole(Role.valueOf(user.getRole().name()));
        userForUpdate = user.getName()==null || user.getName().equals("") ? userForUpdate.withName(RepositoryFactory.getRepository().getUserById(user.getId()).getName()) : userForUpdate.withName(user.getName());
        userForUpdate = user.getLogin()==null || user.getLogin().equals("") ? userForUpdate.withLogin(RepositoryFactory.getRepository().getUserById(user.getId()).getLogin()) : userForUpdate.withLogin(user.getLogin());
        userForUpdate = user.getPassword()==null || user.getPassword().equals("") ? userForUpdate.withPassword(RepositoryFactory.getRepository().getUserById(user.getId()).getPassword()) : userForUpdate.withPassword(user.getPassword());
        userForUpdate = user.getAge()==0 ? userForUpdate.withAge(RepositoryFactory.getRepository().getUserById(user.getId()).getAge()) : userForUpdate.withAge(user.getAge());
        log.info("Userfor update after ={}", userForUpdate.getInf());
        return ResponseEntity.ok(RepositoryFactory.getRepository().updateUser(user));
    }

    private Map<Integer, UserImpl> mapToChange(String user) {
        Map<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                log.info("Watch student ={}", user);
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }

}
