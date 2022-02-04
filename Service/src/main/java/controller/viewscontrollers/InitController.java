package controller.viewscontrollers;

import controller.serviseforcontroller.viewsservises.StartService;
import controller.serviseforcontroller.viewsservises.StratagyForAutorithate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import repository.RepositoryFactory;
import users.UserImpl;

import javax.servlet.http.HttpSession;

@SessionAttributes("user")
@Slf4j
@Controller
@RequestMapping("/mvc")
public class InitController {

    @GetMapping("/hello")
    public String hello(HttpSession session, Model model) {
        if (session != null && session.getAttribute("user") != null) {
            return StratagyForAutorithate.authorizatUser(session, model);
        } else
            StartService.initAdmin();
        return "start";
    }

    @GetMapping("/str")
    public String hell() {
        log.info("Principal!!!");
        return "str";
    }

    @PostMapping("/checkUser")
    public String checkUser(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        UserImpl user = null;
        if (name != null && name != "") {
            log.info("auth name = {}", name);
            user = RepositoryFactory.getRepository().getUserByLogin(name);
            log.info("user by login! ={}", user.getInf());
            log.info("user autority! ={}", auth.getAuthorities().stream().findFirst().get().getAuthority());
            model.addAttribute("userbylogin", user);
            session.setAttribute("user1", user);
            return StratagyForAutorithate.authorizationStratagy(session, model);
        }

//        log.info("auth = {}", auth.getDetails(), auth.getName(), auth.getCredentials(), auth.getPrincipal());
//        if (session != null ) {
//            if (session.getAttribute("user")==null) {
//                return "exception";
//            }
//            else {
//                model.addAttribute("user", session.getAttribute("user"));
//                return StratagyForAutorithate.authorizationStratagy(session, model);
//            }
//        }
        return "redirect:/mvc/hello";
    }

    @GetMapping("/exception")
    public String exception() {
        return "exception";
    }

    @GetMapping("/logout")
    public String logOut(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/logout";
    }
}