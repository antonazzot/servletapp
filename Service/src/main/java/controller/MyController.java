package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Administrator;
import users.Role;
import users.Student;
import users.UserImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Slf4j
@Controller
public class MyController {

    @GetMapping("/mvc/start")
    public String start () {
        return "mvc_start";
    }
    @PostMapping("/mvc/hello")
    public String hello (HttpServletRequest req) {
        {
            Administrator administrator = (Administrator) new Administrator()
                    .withRole(Role.ADMINISTRATOR)
                    .withName("Anton")
                    .withLogin("Admin ")
                    .withPassword("passs")
                    .withAge(34);

            if (RepositoryFactory.getRepository().allUser().values().stream()
                    .noneMatch(u -> u.getLogin().equals(administrator.getLogin()) &&
                            u.getPassword().equals(administrator.getPassword()) &&
                            u.getName().equals(administrator.getName()) &&
                            u.getAge() == administrator.getAge())
            )
                RepositoryFactory.getRepository().saveUser(administrator);
            log.info("Admin = {}", administrator.getInf());
        }
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            return "/mvc/checkUser";
        } else {
            return "hello";
        }
    }

    @GetMapping("/mvc/checkUser")
    public String checkUser (HttpServletRequest req) {
        UserImpl user = (UserImpl) req.getSession().getAttribute("user");
        HttpSession session = req.getSession();
        //authorization block
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            return "adminwiews/adminmain";
        } else
            return "hello";
//            if (Role.STUDENT.equals(user.getRole())) {
//            logger.info("UserRole ={}", user.getRole());
//            req.getRequestDispatcher("StudentPage/studentstartpage.jsp").forward(req, resp);
//        } else if (Role.TRAINER.equals(user.getRole())) {
//            logger.info("UserRole ={}", user.getRole());
//            if (ThreadRepositoryFactory.getRepository().allGroup()
//                    .values()
//                    .stream()
//                    .anyMatch(g -> g.getTrainer().getId() == user.getId())) {
//                Group group = ThreadRepositoryFactory.getRepository().allGroup()
//                        .values()
//                        .stream()
//                        .filter(g -> g.getTrainer().getId() == user.getId())
//                        .findAny()
//                        .get();
//
//                Map<Integer, Student> studentHashMap = group.getStudentMap();
//                Set<Theams> theams = group.getTheamsSet();
//                session.setAttribute("group", group);
//                req.setAttribute("set", theams);
//                req.setAttribute("map", studentHashMap);
//                req.getRequestDispatcher("TrainerControlPage/trainerActList.jsp").forward(req, resp);
//            } else {
//                req.getRequestDispatcher("TrainerControlPage/groupnotexist.jsp").forward(req, resp);
//            }
//        }

    }



}
