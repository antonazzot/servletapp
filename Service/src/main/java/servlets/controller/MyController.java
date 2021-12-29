

        package controller;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import repository.RepositoryFactory;
        import repository.threadmodelrep.ThreadRepositoryFactory;
        import threadmodel.Group;
        import users.Student;
        import users.UserImpl;

        import java.util.Map;

        @Slf4j
@RestController
@RequestMapping(path = "/mvc")

public class MyController {


    @GetMapping(path = "/students", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Map<Integer, UserImpl> students () {
        log.info("in rest {}", "-------->rest");
        return  RepositoryFactory.getRepository().allStudent();
    }

    @GetMapping(path = "/group", produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public Map<Integer, Group> group () {
        log.info("in rest {}", "-------->rest");
        return ThreadRepositoryFactory.getRepository().allGroup();
    }

    @GetMapping(path = "/trainer/{id}" )
    public ResponseEntity<UserImpl> getTrainerById (@PathVariable int id) {
       return ResponseEntity.ok(RepositoryFactory.getRepository().getTrainerById(id));
    }

    @PostMapping
    public ResponseEntity<UserImpl> createUser  (@RequestBody UserImpl user) {
        log.info("in rest {}", "-------->rest");
         RepositoryFactory.getRepository().saveUser(user);
        return ResponseEntity.ok(user);
    }
}
