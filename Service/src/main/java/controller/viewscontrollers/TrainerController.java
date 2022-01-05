package controller.viewscontrollers;

import controller.serviseforcontroller.acttrainerstratagy.MVCTrainerActStratagy;
import controller.serviseforcontroller.viewsservises.ChangeTrinerActStratagy;
import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

@Slf4j
@Controller
@RequestMapping("/mvc/trainer")
@Scope("session")
@SessionAttributes("user")
public class TrainerController {

    @PostMapping ("/traineract")
    public String traineract (
            @RequestParam (required = false, value = "studentId") String studentId,
            @RequestParam (required = false, value = "thId") String thId,
            @RequestParam ("act") String act,
            @RequestParam (required = false, value = "mark") String mark,
            Model model
    ) {
        if (studentId.equals("") || thId.equals("")) {
            return "exception";
        }
        Group group = (Group) model.getAttribute("groupT");
        MVCTrainerActStratagy stratagy = ChangeTrinerActStratagy.getStratagy(act);
        return stratagy.doAct(group, studentId ,thId ,mark, model );
    }

    @DeleteMapping ("/dodeletemark")
    public String dodeletemark (
            @RequestParam (required = false, value = "markId") String [] markId,
            @RequestParam ("th") String thId,
            @RequestParam ("student") String studentId,
            @RequestParam ("act") String act
    ){
        ThreadRepositoryFactory.getRepository().deleteMarksById(
                ParserStringToInt.parseArrayString(markId),
                ParserStringToInt.simpleParserStringToInt(thId),
                ParserStringToInt.simpleParserStringToInt(studentId)
        );
        return "/mvc/hello";
    }

}
