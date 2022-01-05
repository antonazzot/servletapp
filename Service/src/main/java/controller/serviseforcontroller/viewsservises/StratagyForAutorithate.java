package controller.serviseforcontroller.viewsservises;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
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
public class StratagyForAutorithate {
    public static String authorizationStratagy(HttpSession session, Model model) {
        String result = "exception";

        UserImpl user = (UserImpl) session.getAttribute("user");
        log.info("user Autorithate ={}", "--->>>" + user.getInf());
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            model.addAttribute("admin", user);
           result = "adminControl/adminActList";
        } else if (Role.STUDENT.equals(user.getRole())) {
            model.addAttribute("student", user);
            log.info("user Autorithate student ={}", "--->>>" + user.getInf());
            result = "StudentPage/studentstartpage";
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
                log.info("user Autorithate trainer ={}", "--->>>" + user.getInf());

                model.addAttribute("groupT", group);
                model.addAttribute("trainer", user);
                result = "TrainerControlPage/trainerstartpage";

            } else {
                return "TrainerControlPage/groupnotexist";
            }
        }
        log.info("just lost = {}", "lost");
        log.info("Init result = {}", result);
        return result;
    }

    public static String authorizatUser(HttpSession session, Model model) {
        UserImpl user = (UserImpl) session.getAttribute("user");
        if (Role.ADMINISTRATOR.equals(user.getRole())) {

            return "adminControl/adminActList";
        } else if (Role.STUDENT.equals(user.getRole())) {

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

                return "trainerpage/trainerstartpage";

            } else {
                return "TrainerControlPage/groupnotexist";
            }
        }

        return "mvc_hello";
    }
}