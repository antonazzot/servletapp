
package controller.restcontrollers;

import controller.serviseforcontroller.restsservice.ChangeUserToAgregateMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryFactory;
import users.Role;
import users.Student;
import users.UserImpl;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/mvc/rest/user")

public class UsersRestController {

    @GetMapping(path = "/users/{entity}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public Map<Integer, UserImpl> users(@PathVariable String entity) {
        return ChangeUserToAgregateMap.mapToChange(entity);
    }

    @GetMapping(path = "/trainer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserImpl> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(RepositoryFactory.getRepository().getUserById(id));
    }

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserImpl> createUser(@RequestBody UserImpl user) {
        UserImpl userForSave = new Student()
                .withName(user.getName())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withRole(Role.valueOf(user.getRole().name()))
                .withAge(user.getAge());

        RepositoryFactory.getRepository().saveUser(userForSave);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Optional<UserImpl>> deleteUser(@RequestParam("id") int id,
                                                         @RequestParam("entity") String entity) {
        Optional<UserImpl> user = RepositoryFactory.getRepository().removeUser(id, entity);
        return ResponseEntity.ok(user);
    }

    @PatchMapping(path = "patch", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<UserImpl> updateUser(@RequestBody UserImpl user) {
        UserImpl userForUpdate = new UserImpl().withId(user.getId());

        userForUpdate.withRole(Role.valueOf(user.getRole().name()));
        userForUpdate = user.getName() == null || user.getName().equals("") ? userForUpdate.withName(RepositoryFactory.getRepository().getUserById(user.getId()).getName()) : userForUpdate.withName(user.getName());
        userForUpdate = user.getLogin() == null || user.getLogin().equals("") ? userForUpdate.withLogin(RepositoryFactory.getRepository().getUserById(user.getId()).getLogin()) : userForUpdate.withLogin(user.getLogin());
        userForUpdate = user.getPassword() == null || user.getPassword().equals("") ? userForUpdate.withPassword(RepositoryFactory.getRepository().getUserById(user.getId()).getPassword()) : userForUpdate.withPassword(user.getPassword());
        userForUpdate = user.getAge() == 0 ? userForUpdate.withAge(RepositoryFactory.getRepository().getUserById(user.getId()).getAge()) : userForUpdate.withAge(user.getAge());

        return ResponseEntity.ok(RepositoryFactory.getRepository().updateUser(userForUpdate));
    }

}
