package controller.viewscontrollers;

import controller.serviseforcontroller.actadminstrategy.MVCAdminActStratagy;
import controller.serviseforcontroller.viewsservises.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import users.UserImpl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@SessionAttributes("user")
@Controller
@Slf4j
@Scope("session")
@RequiredArgsConstructor
@RequestMapping(path = "/mvc/views")
public class AdminController {

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/mainadmin")
    public String helloadmin() {
        return "adminControl/adminActList";
    }

    @PostMapping("/adminact")
    public String adminAct(@RequestParam("entity") String entity,
                           @RequestParam("act") String act,
                           @RequestParam(required = false) String id,
                           Model model) {
        MVCAdminActStratagy strategy = ChangeAdminActStratagy.getStratagy(act);
        return strategy.watchEntity(entity, model, id);
    }

    @PostMapping("/saveuser")
    public String saveUser(@RequestParam("role") String role,
                           @RequestParam(required = false, name = "name") String name,
                           @RequestParam(required = false, name = "login") String login,
                           @RequestParam(required = false, name = "password") String password,
                           @RequestParam(required = false, name = "age") int age,
                           Model model
    ) {
        if (ContentInParamChecker.checkParam(name, login, password)) {
            String massage = "not right condition for user save";
            model.addAttribute("message", massage);
            return "exception";
        } else
            RepositoryFactory.getRepository().saveUser(SaverService.userForSave(role, name, login, passwordEncoder.encode(password), age));
        return "adminControl/adminActList";
    }

    @GetMapping("/addgroup")
    public String addgroup(@RequestParam(required = false, name = "teamId") String[] theamId,
                           @RequestParam(required = false, name = "stId") String[] studentId,
                           @RequestParam("tr") Integer trId,
                           Model model) {
        try {
            ThreadRepositoryFactory.getRepository()
                    .addGroup(GroupAdderService.studentList(studentId),
                            ParserStringToInt.parseArrayStringToListInteger(theamId),
                            trId);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            String massage = "not right condition for add group, please try again";
            model.addAttribute("message", massage);
            return "exception";
        }

        return "adminControl/adminActList";
    }

    @GetMapping("/addsalary")
    public String addSalary(Model model) throws Exception {
        model.addAttribute("salmap", ThreadRepositoryFactory.getRepository().trainerSalary());
        return "adminControl/addsalarypage";
    }

    @PostMapping("/addSalaryForTrainer")
    public String addSalaryTrainer(
            @RequestParam("trainerId") String trainerId,
            @RequestParam("sal") String salValue,
            Model model
    ) {
        if (ParserStringToInt.simpleParserStringToInt(salValue) > 0) {
            ThreadRepositoryFactory.getRepository().addSalaryToTrainer(Integer.parseInt(trainerId), Integer.parseInt(salValue));
            return "adminControl/adminActList";
        } else {
            String massage = "salary must be upper then null";
            model.addAttribute("massage", massage);
            return "exception";
        }
    }

    @GetMapping("/groupforwatch")
    public String groupForWatch(@RequestParam("groupid") String groupid, Model model) {
        model.addAttribute("map", ThreadRepositoryFactory.getRepository().allGroup().get(Integer.parseInt(groupid)));
        return "adminControl/demonstrategroup";
    }

    @GetMapping("/avarageSalary")
    public String avarageSalary(Model model) {
        model.addAttribute("allTrainer", ThreadRepositoryFactory.getRepository().trainerSalary());
        return "adminControl/averagesalary";
    }

    @GetMapping("/avarageCalc")
    public String avarageSalary(@RequestParam("trId") int trainerId,
                                @RequestParam("period") int period,
                                Model model) {
        if (period > 0) {
            long avaragesalary = AvarageSalaryCalculate.avarageSalaryCalc(
                    RepositoryFactory.getRepository().getTrainerById(trainerId).getSalarylist(),
                    period);
            model.addAttribute("avarage", avaragesalary);
            return "adminControl/avaragesalarywatch";
        } else {
            String massage = "period must be upper then null";
            model.addAttribute("message", massage);
            return "exception";
        }

    }

    @PostMapping("/updateUser")
    public String forChangeUser(@RequestParam("userid") int userId, Model model) {
        model.addAttribute("userForChange", RepositoryFactory.getRepository().getUserById(userId));
        return "adminControl/changeuserform";
    }

    @PostMapping("/savetheam")
    public String saveTheam(@RequestParam("theam") String theamName) {
        if (theamName != null && !theamName.equals(""))
            ThreadRepositoryFactory.getRepository().addTheam(theamName);
        return "adminControl/adminActList";
    }

    @PostMapping("/resultchangeuser")
    public String resultChangeUser(@RequestParam("id") int id,
                                   @RequestParam(required = false, name = "name") String name,
                                   @RequestParam(required = false, name = "login") String login,
                                   @RequestParam(required = false, name = "password") String password,
                                   @RequestParam(required = false, name = "age") String age
    ) {
        RepositoryFactory.getRepository().updateUser(ChangeUser.userForChange(id, name, login, password, age));
        return "adminControl/adminActList";
    }

    //
    @PostMapping("/changetheam")
    public String changeTheam(@RequestParam("theamid") int id,
                              @RequestParam(required = false, name = "newName") String name
    ) {
        ThreadRepositoryFactory.getRepository().updateTheam(id, name);
        return "adminControl/adminActList";
    }

    @PostMapping("/groupforchange")
    public String groupForChange(@RequestParam("groupid") int id,
                                 Model model
    ) {
        Group group = ThreadRepositoryFactory.getRepository().allGroup().get(id);
        model.addAttribute("groupForChange", group);
        model.addAttribute("freetheam", ThreadRepositoryFactory.getRepository().freeTheams());
        model.addAttribute("freetrainer", RepositoryFactory.getRepository().freeTrainer());
        model.addAttribute("freestudent", FreeStudentExtract.freeStudent(new ArrayList<>(group.getStudentMap().values())));
        return "adminControl/groupchangeform";
    }

    @PostMapping("/resultchangegroup")
    public String resultChangeGroup(@RequestParam("id") int id,
                                    @RequestParam("act") String act,
                                    @RequestParam(required = false, value = "entytiIdforact") String[] entytiIdforact
    ) {
        ThreadRepositoryFactory.getRepository().updateGroup(id, act, ParserStringToInt.parseArrayString(entytiIdforact));
        return "adminControl/adminActList";
    }

    @GetMapping("/dodeleteentity")
    public String dodeleteentity(
            @RequestParam("entityId") int entityId,
            @RequestParam(required = false, name = "entity") String entity,
            Model model
    ) {
        RepositoryFactory.getRepository().removeUser(entityId, entity);
        return adminAct(entity, "delete", String.valueOf(entityId), model);
    }

    @GetMapping("/prepare")
    public String prepareAdmin(Model model, HttpSession session) {
        UserImpl user = null;
        try {
            if (session != null && session.getAttribute("user") != null)
                user = (UserImpl) session.getAttribute("user");
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return "exception";
        }
        model.addAttribute("admin", user);
        return "adminControl/adminActList";

    }
}