package controller;

import controller.serviseforcontroller.ChangeAdminActStratagy;
import controller.serviseforcontroller.StartService;
import controller.serviseforcontroller.actadminstratagy.MVCAdminActStratagy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Role;
import users.Student;
import users.UserImpl;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/mvc")

public class MyController {

    @GetMapping("/start")
    public String start(Model model) {
        model.addAttribute("user", RepositoryFactory.getRepository().allUser().values().stream().findFirst());
        log.info("Repository->>>>>>>>>{}", RepositoryFactory.getRepository());
        return "mvc_start";
    }

    @GetMapping("/hello")
    public String hello() {
        StartService.initAdmin();
        return "mvc_hello";
    }

    @PostMapping("/check")
    public String checkUser(@RequestParam("id") String id, @RequestParam("password") String password,
                            HttpSession session, Model model) {
        log.info("model>>>>>>={}", id + password);
        if (session != null && session.getAttribute("user") != null) {
            return authorizationStratagy(session, model);
        }
        return "mvc_hello";
    }
    @PostMapping("/adminact")
    public String adminAct (@RequestParam("entity") String entity, @RequestParam("act") String act,
                            Model model, @RequestParam(required = false) String id) {
        MVCAdminActStratagy strategy = ChangeAdminActStratagy.getStratagy(act);
        return strategy.watchEntity(entity, model, id);
    }
    @PostMapping("/saveuser")
    public String saveUser (@RequestParam("role") String role, @ModelAttribute("createduser") UserImpl user) {
        log.info("USER for SAVE INF ={}", "Role" + user.getRole() + "More about" + user.getInf());
       RepositoryFactory.getRepository().saveUser(user);
       return "adminviews/adminmain";
    }
    @PostMapping("/addgroup")
    public String addgroup (@RequestParam("theamID") Integer [] theamId, @RequestParam ("stId") Integer [] studentId, @RequestParam("tr") String trId) {
    log.info("TheamID={}", theamId.toString()+trId);
       return "adminviews/adminmain";
    }
    @GetMapping ("/addsalary")
    public String addSalary (Model model) {
        model.addAttribute("salmap", ThreadRepositoryFactory.getRepository().trainerSalary());
        log.info("Trainer={}", model.getAttribute("salmap").toString());
        return "adminviews/addsalary";
    }
    @GetMapping ("/addSalaryForTrainer")
    public  String addSalaryTrainer (
            @RequestParam ("trainerId") String trainerId, @RequestParam("sal") String salValue
    ) {
        ThreadRepositoryFactory.getRepository().addSalaryToTrainer(Integer.parseInt(trainerId), Integer.parseInt(salValue));
        return "adminviews/addsalary";
    }

    private String authorizationStratagy(HttpSession session, Model model) {

        UserImpl user = (UserImpl) session.getAttribute("user");
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            model.addAttribute("admin", user);
            return "adminviews/adminmain";
        } else if (Role.STUDENT.equals(user.getRole())) {
            model.addAttribute("student", user);
            return "StudentPage/studentstartpage";
        } else if (Role.TRAINER.equals(user.getRole())) {
            if (ThreadRepositoryFactory.getRepository().allGroup()
                    .values()
                    .stream()
                    .anyMatch(g -> g.getTrainer().getId() == user.getId())) {
                Group group = ThreadRepositoryFactory.getRepository().allGroup()
                        .values()
                        .stream()
                        .filter(g -> g.getTrainer().getId() == user.getId())
                        .findAny()
                        .get();

                Map<Integer, Student> studentHashMap = group.getStudentMap();
                Set<Theams> theams = group.getTheamsSet();
                model.addAttribute("group", group);
                model.addAttribute("trainer", user);
                model.addAttribute("set", theams);
                model.addAttribute("map", studentHashMap);
                return "TrainerControlPage/trainerActList";

            } else {
                return "TrainerControlPage/groupnotexist";
            }
        }

        return "mvc_hello";
    }
}
