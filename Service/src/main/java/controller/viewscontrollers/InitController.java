package controller.viewscontrollers;

import controller.serviseforcontroller.viewsservises.StartService;
import controller.serviseforcontroller.viewsservises.StratagyForAutorithate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.security.Principal;

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

    @GetMapping()
    public String hell (Principal principal) {

        log.info("Principal ={}" , principal.getName() );
        return "str";
    }

    @PostMapping("/checkUser")
    public String checkUser(HttpSession session, Model model) {
        if (session != null ) {
            if (session.getAttribute("user")==null) {
                return "exception";
            }
            else {
                model.addAttribute("user", session.getAttribute("user"));
                return StratagyForAutorithate.authorizationStratagy(session, model);
            }
        }
        return "redirect:/mvc/hello";
    }

    @GetMapping("/exception")
    public String exception() {
        return "exception";
    }

    @GetMapping("/logout")
    public String logOut(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/mvc/hello";
    }
}