package controller.viewscontrollers;

import controller.serviseforcontroller.senderservice.PrepareToSend;
import controller.serviseforcontroller.senderservice.SenderService;
import controller.serviseforcontroller.actadminstrategy.MVCAdminActStrategy;
import controller.serviseforcontroller.viewsservises.*;
import helperutils.myexceptionutils.AppValidException;
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
import java.util.List;

@SessionAttributes("user")
@Controller
@Slf4j
@Scope("session")
@RequiredArgsConstructor
@RequestMapping(path = "/mvc/views")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final SenderService senderService;
    private final ActWithTempUser actWithTempUser;

    @GetMapping("/mainadmin")
    public String helloadmin() {
        return "adminControl/adminActList";
    }

    @PostMapping("/adminact")
    public String adminAct(@RequestParam("entity") String entity,
                           @RequestParam("act") String act,
                           Model model) {
        MVCAdminActStrategy strategy = ChangeAdminActStratagy.getStratagy(act);
        return strategy.prepareEntity(entity, model);
    }

    @PostMapping("/saveuser")
    public String saveUser(@RequestParam("role") String role,
                           @RequestParam(required = false, name = "name") String name,
                           @RequestParam(required = false, name = "login") String login,
                           @RequestParam(required = false, name = "password") String password,
                           @RequestParam(required = false, name = "age") int age,
                           @RequestParam(required = false, name = "email") String email,
                           Model model
    ) {
        try {
            CheckTempUserParameters.checkParameters(name,login,password,age,email);
            RepositoryFactory.getRepository().saveUser(SaverService.userForSave(role, name, login, passwordEncoder.encode(password), age, email));
        }
        catch (AppValidException e) {
            String message = e.getMessage();
            log.error(message);
            model.addAttribute("message", message);
            return "exception";
        }
        return "adminControl/adminActList";
    }

    @GetMapping("/addgroup")
    public String addgroup(@RequestParam(required = false, name = "teamId") String[] theamId,
                           @RequestParam(required = false, name = "stId") String[] studentId,
                           @RequestParam("tr") Integer trId,
                           Model model) {
        try {
            ThreadRepositoryFactory.getRepository().addGroup(GroupAdderService.studentList(studentId), ParserStringToInt.parseArrayStringToListInteger(theamId), trId);
        } catch (AppValidException e) {
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
            String message = "salary must be upper then null";
            model.addAttribute("message", message);
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
        try {
            long avaragesalary = AvarageSalaryCalculate.avarageSalaryCalc(
                    RepositoryFactory.getRepository().getTrainerById(trainerId).getSalarylist(),
                    period);
            model.addAttribute("avarage", avaragesalary);
        } catch (AppValidException e) {
            String message = e.getMessage();
            log.error(message);
            model.addAttribute("message", message);
            return "exception";
        }
        return "adminControl/avaragesalarywatch";
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
        else return "exception";
        return "adminControl/adminActList";
    }

    @PostMapping("/resultchangeuser")
    public String resultChangeUser(@RequestParam("id") int id,
                                   @RequestParam("role") String role,
                                   @RequestParam(required = false, name = "name") String name,
                                   @RequestParam(required = false, name = "login") String login,
                                   @RequestParam(required = false, name = "password") String password,
                                   @RequestParam(required = false, name = "age") String age,
                                   @RequestParam(required = false, name = "email") String email,
                                   Model model
    ) {
        try {
            RepositoryFactory.getRepository().updateUser(ChangeUser.userForChange(id, name, login, password, age, role, email));
            return "adminControl/adminActList";
        } catch (AppValidException e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "exception";
        }
    }

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
        try {
            ThreadRepositoryFactory.getRepository().updateGroup(id, act, ParserStringToInt.parseArrayString(entytiIdforact));
        }
        catch (AppValidException e) {
            log.error(e.getMessage());
            return "exception";
        }
        return "adminControl/adminActList";
    }

    @PostMapping("/dodeleteentity")
    public String dodeleteentity(
            @RequestParam("entityId") Integer[] entityId,
            @RequestParam(required = false, name = "entity") String entity,
            Model model
    ) {
        RemoveEntityIdIterator.iterateAndDeleteEntity(entityId, entity);
        return "adminControl/adminActList";
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

    @GetMapping("/propose")
    public String proposeStudent(Model model) {
        model.addAttribute("list", RepositoryFactory.getRepository().findAllTempSstudent());
        return "adminControl/tempstudentact";
    }

    @PostMapping("/doactwithtempstudent")
    public String actWithTemp(
            @RequestParam("act") String act,
            @RequestParam("entityId") Integer[] id,
            Model model) {
        actWithTempUser.doAct(act, id);
        return "adminControl/adminActList";
    }

    @PostMapping("/messager")
    public String messager(
            @RequestParam("tousers") String tousers,
            @RequestParam(required = false, name = "theam") String theam,
            @RequestParam(required = false, name = "message") String message,
            Model model) {
        if (!tousers.equals("oneuser")){
            List<String> sendTo = PrepareToSend.getUserTo(tousers);
            for (String s : sendTo) {
                senderService.sendMail(s, theam, message);
            }
            return "adminControl/adminActList";
        }
        else {
            model.addAttribute("allAdmin", RepositoryFactory.getRepository().allAdmin());
            model.addAttribute("allTrainer", RepositoryFactory.getRepository().allTrainer());
            model.addAttribute("allStudent", RepositoryFactory.getRepository().allStudent());
            model.addAttribute("theam", theam);
            model.addAttribute("message", message);
            return "adminControl/choseUserForSend";
        }
    }

    @PostMapping("/individualsend")
    public String individualSend(
            @RequestParam(required = false , name = "id") int []  id,
            @RequestParam(required = false, name = "theam") String theam,
            @RequestParam(required = false, name = "message") String message,
            Model model) {
        for (int i : id) {
            String email = RepositoryFactory.getRepository().getUserById(i).getEmail();
            senderService.sendMail(email, theam, message);
        }
        return "adminControl/senderform";
    }

    @GetMapping("/sender")
    public String actWithTemp(
            Model model) {
        return "adminControl/senderform";
    }


}