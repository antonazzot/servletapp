package controller.viewscontrollers;

import controller.serviseforcontroller.senderservice.SenderService;
import controller.serviseforcontroller.viewsservises.*;
import helperutils.myexceptionutils.AppValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import repository.RepositoryFactory;
import users.TempStudent;
import users.UserImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SessionAttributes("user")
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mvc")
public class InitController {

    private final PasswordEncoder passwordEncoder;
    private final SenderService senderService;

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
            user = RepositoryFactory.getRepository().getUserByLogin(name);
            model.addAttribute("userbylogin", user);
            session.setAttribute("user1", user);
            return StratagyForAutorithate.authorizationStratagy(session, model);
        }
        return "redirect:/mvc/hello";
    }

    @GetMapping ("/registrate")
    public String registrte() {
        return "regitrteform";
    }

    @GetMapping ("/resetpassword")
    public String resetPassword() {
        return "resetPasswordForm";
    }

    @PostMapping ("/tempstudent")
    public String tempstudentRegistrate (
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "login") String login,
            @RequestParam(required = false, name = "password") String password,
            @RequestParam(required = false, name = "age") int age,
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "remember_me") String rememberMe,
            Model model,
            HttpServletRequest request
    ) {
        try {
            CheckTempUserParameters.checkParameters(name, login, password, age, email);
            TempStudent tempStudent = TempStudent.builder()
                    .name(name)
                    .login(login)
                    .password(passwordEncoder.encode(password))
                    .age(age)
                    .gmail(email)
                    .build();
        RepositoryFactory.getRepository().saveTempStudent(tempStudent);
        if (rememberMe!=null && !rememberMe.equals("")) {
            request.getCookies();
        }
        }
        catch (AppValidException e) {
            String message = e.getMessage();
            log.error(message);
            model.addAttribute("message", message);
            return "exception";
        }
        return "str";
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

    @PostMapping("/resetpassword")
    public String resetPassword(
            @RequestParam(required = false, name = "login") String login,
            @RequestParam(required = false, name = "password") String password,
            @RequestParam(required = false, name = "repeatpassword") String repassword,
            @RequestParam(required = false, name = "email") String email,
            Model model
    ) {
        try {
            CheckParametrForResetPassword.checkParameters(repassword,login, password, email);
            UserImpl userByLogin = RepositoryFactory.getRepository().getUserByLogin(login);
            CheckParametrForResetPassword.checkUserParameters(userByLogin.getEmail(), userByLogin.getLogin(), login, email);
            RepositoryFactory.getRepository().updateUser(ChangeUser.userForChange(userByLogin.getId(), userByLogin.getName(), login, passwordEncoder.encode(password), Integer.toString(userByLogin.getAge()), userByLogin.getRole().name(), email));
            senderService.sendMail(email, "Password change", "Your password was sucsesfull change. New password: " +password);
        }
        catch (AppValidException e) {
            String message = e.getMessage();
            log.error(message);
            model.addAttribute("message", message);
            return "exception";
        }
        return "redirect:/mvc/str";
    }
}