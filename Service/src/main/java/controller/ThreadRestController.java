
package controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryFactory;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Role;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/mvc/rest/thread")

public class ThreadRestController {

    @GetMapping(path = "/groups", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Map<Integer, Group> group () {
        log.info("in rest {}", "-------->rest");
        return ThreadRepositoryFactory.getRepository().allGroup();
    }

    @GetMapping(path = "/theams", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Map<Integer, Theams> theams () {
        log.info("in rest {}", "-------->rest");
        return ThreadRepositoryFactory.getRepository().allTheams();
    }

    @GetMapping(path = "/group/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public ResponseEntity <Group> groupById (@PathVariable int id) {
        return ResponseEntity.ok(ThreadRepositoryFactory.getRepository().allGroup().get(id));
    }

    @GetMapping(path = "/theam/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public ResponseEntity <Theams> theamById (@PathVariable int id) {
        return ResponseEntity.ok(ThreadRepositoryFactory.getRepository().theamById(id));

    }

    @GetMapping(path = "/mark/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public ResponseEntity <Map<Integer, Mark>> markMap (@PathVariable int id, @RequestBody Theams theams) {
        return ResponseEntity.ok(ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(theams, id));

    }

    @PostMapping ("/theamadd/{theamName}")
    @ResponseBody
    public ResponseEntity<Theams> createTheam  (@PathVariable String theamName) {
        ThreadRepositoryFactory.getRepository().addTheam(theamName);
    return ResponseEntity.ok(ThreadRepositoryFactory.getRepository().allTheams().values()
            .stream()
            .filter(theams -> theams.getTheamName().equalsIgnoreCase(theamName))
            .findFirst().get());
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Optional<?>> deleteEntity (@RequestParam ("id") int id, @RequestParam ("entity") String entity) {
      return ResponseEntity.ok(RepositoryFactory.getRepository().removeUser(id, entity));
    }

    @PatchMapping (path = "/updatetheam", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Theams>  updateTheam (@RequestBody Theams theams) {
        Theams theamsForUpDate = new Theams().withId(theams.getId());
        theamsForUpDate = theams.getTheamName()==null || theamsForUpDate.getTheamName().equals("") ? theamsForUpDate.withValue(ThreadRepositoryFactory.getRepository().theamById(theams.getId()).getTheamName()) : theamsForUpDate.withValue(theams.getTheamName());
        ThreadRepositoryFactory.getRepository().updateTheam(theams.getId(), theams.getTheamName());
        return ResponseEntity.ok(ThreadRepositoryFactory.getRepository().allTheams().values()
                .stream()
                .filter(theam -> theam.getTheamName().equalsIgnoreCase(theams.getTheamName()))
                .findFirst().get());
    }

    @PatchMapping (path = "/updateGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void update (@RequestParam ("groupId") int groupId, @RequestParam ("act") String act, @RequestParam ("entytiIdforact") int [] entytiIdforact) {
        ThreadRepositoryFactory.getRepository().updateGroup(groupId, act, entytiIdforact);
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
