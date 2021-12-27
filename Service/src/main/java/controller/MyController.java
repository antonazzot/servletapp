package controller;

import controller.serviseforcontroller.*;
import controller.serviseforcontroller.actadminstratagy.MVCAdminActStratagy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Trainer;
import users.UserImpl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

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
            return StratagyForAvtorithate.authorizationStratagy(session, model);
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
    @GetMapping("/addgroup")
    public String addgroup (@RequestParam("theamID") Integer [] theamId, @RequestParam ("stId") Integer [] studentId, @RequestParam("tr") Integer trId) {
        ThreadRepositoryFactory.getRepository().addGroup(GroupAdderService.studentList(studentId), Arrays.asList(theamId), trId);
       return "adminviews/adminmain";
    }
    @GetMapping ("/addsalary")
    public String addSalary (Model model) {
        model.addAttribute("salmap", ThreadRepositoryFactory.getRepository().trainerSalary());
        return "adminviews/addsalary";
    }
    @GetMapping ("/addSalaryForTrainer")
    public  String addSalaryTrainer (
            @RequestParam ("trainerId") String trainerId, @RequestParam("sal") String salValue
    ) {
        ThreadRepositoryFactory.getRepository().addSalaryToTrainer(Integer.parseInt(trainerId), Integer.parseInt(salValue));
        return "adminviews/addsalary";
    }

    @GetMapping("/groupforwatch")
    public  String groupForWatch (@RequestParam("groupid") String groupid, Model model ) {
        model.addAttribute("group", ThreadRepositoryFactory.getRepository().allGroup().get(Integer.parseInt(groupid)));
        return "adminviews/demonstrategroup";
    }
    @GetMapping("/avarageSalary")
    public  String avarageSalary (Model model) {
        model.addAttribute("allTrainer", RepositoryFactory.getRepository().allTrainer().values());
        return "adminviews/averagesalarychange";
    }
    @GetMapping("/avarageCalc")
    public  String avarageSalary (@RequestParam("trId")int trainerId, @RequestParam("period") int period, Model model) {
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(trainerId);
        long avaragesalary = AvarageSalaryCalculate.avarageSalaryCalc(
                trainer.getSalarylist(),
                period
        );
        return "adminviews/adminmain";
    }
        @GetMapping("/forchangeuser")
        public  String forChangeUser (@RequestParam("userid")int userId, Model model) {
        model.addAttribute("userForChange", RepositoryFactory.getRepository().getUserById(userId));
        return "adminviews/changeuserform";
    }
    @PostMapping("/savetheam")
    public String saveTheam ( @ModelAttribute("theam") Theams theam) {
        log.info("Theam ={}", "Role" + theam.getTheamName());
        ThreadRepositoryFactory.getRepository().addTheam(theam.getTheamName());
        return "adminviews/adminmain";
    }
    @PostMapping ("/resultchangeuser")
    public String resultChangeUser ( @RequestParam("id") int id,
                                    @RequestParam(required = false, name = "name" ) String name,
                                     @RequestParam(required = false, name ="login") String login,
                                     @RequestParam(required = false, name ="password") String password,
                                     @RequestParam(required = false, name ="age") String age
                                     ) {
        RepositoryFactory.getRepository().updateUser(ChangeUser.userForChange(id, name,login,password,age));
        return "adminviews/adminmain";
    }

    @GetMapping  ("/changetheam")
    public String changeTheam ( @RequestParam("theamid") int id,
                                    @RequestParam(required = false, name = "newName" ) String name
                                     ) {
        ThreadRepositoryFactory.getRepository().updateTheam(id, name);
        return "adminviews/adminmain";
    }
    @GetMapping ("/groupforchange")
    public String groupForChange ( @RequestParam("groupid") int id,
                                    Model model
                                     ) {
        Group group = ThreadRepositoryFactory.getRepository().allGroup().get(id);

        model.addAttribute("groupForChange",group);
        model.addAttribute("freetheam", ThreadRepositoryFactory.getRepository().freeTheams());
        model.addAttribute("freetrainer", RepositoryFactory.getRepository().freeTrainer());
        model.addAttribute("freestudent", FreeStudentExtract.freeStudent(new ArrayList<>(group.getStudentMap().values())));
        return "adminviews/groupchangeform";
    }
    @GetMapping ("/resultchangegroup")
    public String resultChangeGroup ( @RequestParam("id") int id,
                                    @RequestParam("act") String act,
                                    @RequestParam("entytiIdforact") String [] entytiIdforact
                                     ) {
        ThreadRepositoryFactory.getRepository().updateGroup(id, act, ParserStringToInt.parseArrayString(entytiIdforact) );
        return "adminviews/adminmain";
    }

}
