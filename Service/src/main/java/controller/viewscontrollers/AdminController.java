package controller.viewscontrollers;

import controller.serviseforcontroller.viewsservises.ChangeUser;
import controller.serviseforcontroller.acttrainerstrategy.MVCAdminActStratagy;
import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import controller.serviseforcontroller.viewsservises.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.RepositoryFactory;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import users.Role;
import users.Trainer;
import users.UserImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@Slf4j
@RequestMapping(path = "/mvc/views")
public class AdminController {

    @GetMapping("/hello")
    public String hello() {
        StartService.initAdmin();
        return "start";
    }

    @PostMapping("/checkUser")
    public String checkUser(@RequestParam("id") String id, @RequestParam("password") String password,
                            HttpSession session, Model model) {
        log.info("Session??????>>>>>>={}","id: " + id + " pass: " + password + " Sesssion:  " + session + " user ");

        if (session != null && session.getAttribute("user") != null) {
            return StratagyForAutorithate.authorizationStratagy(session, model);
        }
        return "hello";
    }

    @PostMapping("/adminact")
    public String adminAct (@RequestParam("entity") String entity, @RequestParam("act") String act,
                            Model model, @RequestParam(required = false) String id) {
        MVCAdminActStratagy strategy = ChangeAdminActStratagy.getStratagy(act);
        return strategy.watchEntity(entity, model, id);
    }

    @PostMapping("/saveuser")
    public String saveUser (@RequestParam("role") String role,
                            @RequestParam("name") String name,
                            @RequestParam("login") String login,
                            @RequestParam("password") String passsword,
                            @RequestParam(required = false, name = "age") int age
                            ) {
        UserImpl user = new UserImpl().withRole(Role.valueOf(role))
                .withName(name)
                .withLogin(login)
                .withPassword(passsword)
                .withAge(age);
        RepositoryFactory.getRepository().saveUser(user);
        log.info("USER for SAVE INF ={}", "Role" + role + "More about" + user.getInf() );
        return "adminControl/adminActList";
    }

    @PostMapping("/addgroup")
    public String addgroup (@RequestParam("theamID") String [] theamId, @RequestParam ("stId") Integer [] studentId, @RequestParam("tr") Integer trId) {
        ThreadRepositoryFactory.getRepository().addGroup(GroupAdderService.studentList(studentId),  ParserStringToInt.parseArrayStringToListInteger(theamId), trId);
        return "adminControl/adminActList";
    }
    @GetMapping ("/addsalary")
    public String addSalary (Model model) {
        model.addAttribute("salmap", ThreadRepositoryFactory.getRepository().trainerSalary());
        return "adminControl/addsalarypage";
    }
    @PostMapping ("/addSalaryForTrainer")
    public  String addSalaryTrainer (
            @RequestParam ("trainerId") String trainerId, @RequestParam("sal") String salValue
    ) {
        ThreadRepositoryFactory.getRepository().addSalaryToTrainer(Integer.parseInt(trainerId), Integer.parseInt(salValue));
        return "adminControl/adminActList";
    }

    @GetMapping("/groupforwatch")
    public  String groupForWatch (@RequestParam("groupid") String groupid, Model model ) {
        model.addAttribute("map", ThreadRepositoryFactory.getRepository().allGroup().get(Integer.parseInt(groupid)));
        return "adminControl/demonstrategroup";
    }
    @GetMapping("/avarageSalary")
    public  String avarageSalary (Model model) {
        model.addAttribute("allTrainer", ThreadRepositoryFactory.getRepository().trainerSalary());
        return "adminControl/averagesalary";
    }
    @GetMapping("/avarageCalc")
    public  String avarageSalary (@RequestParam("trId")int trainerId, @RequestParam("period") int period, Model model) {
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(trainerId);
        long avaragesalary = AvarageSalaryCalculate.avarageSalaryCalc(
                trainer.getSalarylist(),
                period
        );
        model.addAttribute("avarage", avaragesalary);
        return "adminControl/avaragesalarywatch";
    }

    @PostMapping("/updateUser")
    public  String forChangeUser (@RequestParam("userid")int userId, Model model) {
        model.addAttribute("userForChange", RepositoryFactory.getRepository().getUserById(userId));
        return "adminControl/changeuserform";
    }

    @PostMapping("/savetheam")
    public String saveTheam ( @RequestParam("theam") String theamName) {
        ThreadRepositoryFactory.getRepository().addTheam(theamName);
        return "adminControl/adminActList";
    }
    @PostMapping ("/resultchangeuser")
    public String resultChangeUser ( @RequestParam("id") int id,
                                     @RequestParam(required = false, name = "name" ) String name,
                                     @RequestParam(required = false, name ="login") String login,
                                     @RequestParam(required = false, name ="password") String password,
                                     @RequestParam(required = false, name ="age") String age
    ) {
        RepositoryFactory.getRepository().updateUser(ChangeUser.userForChange(id, name,login,password,age));
        return "adminControl/adminActList";
    }
//
    @PostMapping  ("/changetheam")
    public String changeTheam ( @RequestParam("theamid") int id,
                                @RequestParam(required = false, name = "newName" ) String name
    ) {
        ThreadRepositoryFactory.getRepository().updateTheam(id, name);
        return "adminControl/adminActList";
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
        return "adminControl/groupchangeform";
    }
    @GetMapping ("/resultchangegroup")
    public String resultChangeGroup ( @RequestParam("id") int id,
                                      @RequestParam("act") String act,
                                      @RequestParam("entytiIdforact") String [] entytiIdforact
    ) {
        ThreadRepositoryFactory.getRepository().updateGroup(id, act, ParserStringToInt.parseArrayString(entytiIdforact) );
        return "adminviews/adminmain";
    }
    @GetMapping ("/logout")
    public String logOut () {
        return "start";
    }
}
