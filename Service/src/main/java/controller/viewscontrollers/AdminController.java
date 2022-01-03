package controller.viewscontrollers;

import controller.serviseforcontroller.acttrainerstrategy.MVCAdminActStratagy;
import controller.serviseforcontroller.viewsservises.ChangeAdminActStratagy;
import controller.serviseforcontroller.viewsservises.StartService;
import controller.serviseforcontroller.viewsservises.StratagyForAutorithate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping(path = "/mvc/views")
public class AdminController {

    @GetMapping("/hello")
    public String hello() {
        StartService.initAdmin();
        return "start";
    }

    @PostMapping("/checkUser")
    public String checkUser(@RequestParam("id") String id, @RequestParam("password") String password,
                            HttpSession session, Model model) {
        log.info("model>>>>>>={}", id + password);
        if (session != null && session.getAttribute("user") != null) {
            return StratagyForAutorithate.authorizationStratagy(session, model);
        }
        return "hello";
    }

    @PostMapping("/adminact")
    public String adminAct (@RequestParam("entity") String entity, @RequestParam("act") String act,
                            Model model, @RequestParam(required = false) String id) {
        MVCAdminActStratagy strategy = ChangeAdminActStratagy.getStratagy(act);
        return strategy.watchEntity(entity, model, id);
    }
}
