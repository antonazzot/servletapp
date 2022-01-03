package controller.serviseforcontroller.viewsservises;

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

public class StratagyForAutorithate {
    public static String authorizationStratagy(HttpSession session, Model model) {

        UserImpl user = (UserImpl) session.getAttribute("user");
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            model.addAttribute("admin", user);
            return "adminControl/adminActList";
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
                return "trainerpage/trainerstartpage";

            } else {
                return "TrainerControlPage/groupnotexist";
            }
        }

        return "mvc_hello";
    }
}