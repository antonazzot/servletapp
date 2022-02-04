package controller.viewscontrollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.UserImpl;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/mvc/student")
public class StudentController {

    @GetMapping
    public String getStudent(Model model, HttpSession session) {
        UserImpl user = null;
        try {
            user = (UserImpl) model.getAttribute("userbylogin");
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return "exception";
        }
        if (user != null) {
            Map<UserImpl, Map<Theams, List<Mark>>> userMapMap = ThreadRepositoryFactory.getRepository().studentTheamMark(user.getId());
            model.addAttribute("studentMapMark", userMapMap);
        }
        return "StudentPage/studentinf";
    }
}
