package controller.serviseforcontroller.viewsservises;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Role;
import users.UserImpl;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
public class StratagyForAutorithate {
    public static String authorizationStratagy(HttpSession session, Model model) {
        String result = "exception";
        UserImpl user = (UserImpl) model.getAttribute("userbylogin");
        log.info("user Autorithate ={}", "--->>>" + user.getInf());
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            model.addAttribute("admin", user);
            result = "adminControl/adminActList";
        } else if (Role.STUDENT.equals(user.getRole())) {
            Map<UserImpl, Map<Theams, List<Mark>>> userMapMap = ThreadRepositoryFactory.getRepository().studentTheamMark(user.getId());
            model.addAttribute("studentMapMark", userMapMap);
            result = "StudentPage/studentinf";
        } else if (Role.TRAINER.equals(user.getRole())) {
            try {
                if (ThreadRepositoryFactory.getRepository()
                        .allGroup()
                        .values()
                        .stream()
                        .anyMatch(g -> g.getTrainer().getId() == user.getId())) {
                    Group group = ThreadRepositoryFactory.getRepository().allGroup()
                            .values()
                            .stream()
                            .filter(g -> g.getTrainer().getId() == user.getId())
                            .findFirst()
                            .get();
                    log.info("user Autorithate trainer ={}", "--->>>" + user.getInf());
                    model.addAttribute("groupT", group);
                    model.addAttribute("trainer", user);
                    result = "TrainerControlPage/trainerstartpage";
                }
                else
                result = "TrainerControlPage/groupnotexist";
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                result = "TrainerControlPage/groupnotexist";
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
            Map<UserImpl, Map<Theams, List<Mark>>> userMapMap = ThreadRepositoryFactory.getRepository().studentTheamMark(user.getId());
            model.addAttribute("studentMapMark", userMapMap);
            return "StudentPage/studentinf";
        } else if (Role.TRAINER.equals(user.getRole())) {
            try {
                Group group = ThreadRepositoryFactory.getRepository().allGroup()
                        .values()
                        .stream()
                        .filter(g -> g.getTrainer().getId() == user.getId())
                        .findAny()
                        .get();
                log.info("user Autorithate trainer ={}", "--->>>" + user.getInf());

                model.addAttribute("groupT", group);
                model.addAttribute("trainer", user);
                return "TrainerControlPage/trainerstartpage";
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                return "TrainerControlPage/groupnotexist";
            }
        }
        return "mvc_hello";
    }
}