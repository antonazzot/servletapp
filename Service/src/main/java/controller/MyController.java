package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

@RequestMapping("/mvc")
public class MyController {

    @GetMapping("/start")
    public String start () {

        log.info("Repository->>>>>>>>>{}", RepositoryFactory.getRepository());

        return "mvc_start";
    }

    @GetMapping("/hello")
    public String hello () {

        return "mvc_hello";
    }

    @GetMapping ("/checkUser")
    public String checkUser (@SessionAttribute(name = "user", required = false) UserImpl user) {

        //authorization block
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            return "adminwiews/adminmain";
        } else
            return "mvc_hello";
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
