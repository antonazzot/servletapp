package controller.viewscontrollers;

import controller.serviseforcontroller.acttrainerstrategy.MVCAdminActStratagy;
import controller.serviseforcontroller.viewsservises.AvarageSalaryCalculate;
import controller.serviseforcontroller.viewsservises.ChangeAdminActStratagy;
import controller.serviseforcontroller.viewsservises.StartService;
import controller.serviseforcontroller.viewsservises.StratagyForAutorithate;
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
import users.Trainer;
import users.UserImpl;

import javax.servlet.http.HttpSession;
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
        UserImpl user = new UserImpl().withRole(RoleIDParametrCheker.getRoleByString(role))
                .withName(name)
                .withLogin(login)
                .withPassword(passsword)
                .withAge(age);
        RepositoryFactory.getRepository().saveUser(user);
        log.info("USER for SAVE INF ={}", "Role" + role + "More about" + user.getInf() );
        return "adminControl/adminActList";
    }

    @GetMapping("/addgroup")
    public String addgroup (@RequestParam("theamID") Integer [] theamId, @RequestParam ("stId") Integer [] studentId, @RequestParam("tr") Integer trId) {
//        ThreadRepositoryFactory.getRepository().addGroup(GroupAdderService.studentList(studentId), Arrays.asList(theamId), trId);
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
        model.addAttribute("group", ThreadRepositoryFactory.getRepository().allGroup().get(Integer.parseInt(groupid)));
        return "adminviews/demonstrategroup";
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

//    @GetMapping("/forchangeuser")
//    public  String forChangeUser (@RequestParam("userid")int userId, Model model) {
//        model.addAttribute("userForChange", RepositoryFactory.getRepository().getUserById(userId));
//        return "adminviews/changeuserform";
//    }
//
//    @PostMapping("/savetheam")
//    public String saveTheam ( @ModelAttribute("theam") Theams theam) {
//        log.info("Theam ={}", "Role" + theam.getTheamName());
//        ThreadRepositoryFactory.getRepository().addTheam(theam.getTheamName());
//        return "adminviews/adminmain";
//    }
//    @PostMapping ("/resultchangeuser")
//    public String resultChangeUser ( @RequestParam("id") int id,
//                                     @RequestParam(required = false, name = "name" ) String name,
//                                     @RequestParam(required = false, name ="login") String login,
//                                     @RequestParam(required = false, name ="password") String password,
//                                     @RequestParam(required = false, name ="age") String age
//    ) {
//        RepositoryFactory.getRepository().updateUser(ChangeUser.userForChange(id, name,login,password,age));
//        return "adminviews/adminmain";
//    }
//
//    @GetMapping  ("/changetheam")
//    public String changeTheam ( @RequestParam("theamid") int id,
//                                @RequestParam(required = false, name = "newName" ) String name
//    ) {
//        ThreadRepositoryFactory.getRepository().updateTheam(id, name);
//        return "adminviews/adminmain";
//    }
//    @GetMapping ("/groupforchange")
//    public String groupForChange ( @RequestParam("groupid") int id,
//                                   Model model
//    ) {
//        Group group = ThreadRepositoryFactory.getRepository().allGroup().get(id);
//
//        model.addAttribute("groupForChange",group);
//        model.addAttribute("freetheam", ThreadRepositoryFactory.getRepository().freeTheams());
//        model.addAttribute("freetrainer", RepositoryFactory.getRepository().freeTrainer());
//        model.addAttribute("freestudent", FreeStudentExtract.freeStudent(new ArrayList<>(group.getStudentMap().values())));
//        return "adminviews/groupchangeform";
//    }
//    @GetMapping ("/resultchangegroup")
//    public String resultChangeGroup ( @RequestParam("id") int id,
//                                      @RequestParam("act") String act,
//                                      @RequestParam("entytiIdforact") String [] entytiIdforact
//    ) {
//        ThreadRepositoryFactory.getRepository().updateGroup(id, act, ParserStringToInt.parseArrayString(entytiIdforact) );
//        return "adminviews/adminmain";
//    }
}
