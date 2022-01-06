package controller.viewscontrollers;

import controller.serviseforcontroller.acttrainerstratagy.MVCTrainerActStratagy;
import controller.serviseforcontroller.viewsservises.ChangeTrinerActStratagy;
import controller.serviseforcontroller.viewsservises.MarkIdMarkValueIntegration;
import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

import java.util.HashMap;

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
        if ( studentId==null || studentId.equals("") || thId==null || thId.equals("")) {
            return "exception";
        }
        Group group = (Group) model.getAttribute("groupT");
        MVCTrainerActStratagy stratagy = ChangeTrinerActStratagy.getStratagy(act);
        return stratagy.doAct(group, studentId ,thId ,mark, model );
    }

    @PostMapping ("/dodeletemark")
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
        return "redirect:/mvc/hello";
    }

    @PostMapping ("/dochangemark")
    public String dochangemark (

            @RequestParam ("thId") String thId,
            @RequestParam ("studentId") String studentId,
            @RequestParam (required = false, value = "markId") String [] markId,
            @RequestParam (required = false, value = "markValue") String [] markValue
    )
    {
        ThreadRepositoryFactory.getRepository().changeMark(MarkIdMarkValueIntegration.doIntegration(markValue, markId),
                ParserStringToInt.simpleParserStringToInt(studentId), ParserStringToInt.simpleParserStringToInt(thId));

        return "redirect:/web/mvc/hello"; }

}
