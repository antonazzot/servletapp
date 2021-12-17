package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.RepositoryFactory;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/mvc")

public class MyController {

    @GetMapping("/start")
    public String start (Model model) {
        model.addAttribute("user", RepositoryFactory.getRepository().allUser().values().stream().findFirst());
        log.info("Repository->>>>>>>>>{}", RepositoryFactory.getRepository());


        return "mvc_start";
    }

    @GetMapping("/hello")
    public String hello () {

        return "mvc_hello";
    }

    @PostMapping ("/check")
    public String checkUser (@RequestParam("id") String id, @RequestParam ("password") String password, HttpSession session ) {

        log.info("model>>>>>>={}", id+password);
        if (session!=null ) {
        log.info("user={}", session.getAttribute("user").toString());}
            return "adminwiews/adminmain";
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
