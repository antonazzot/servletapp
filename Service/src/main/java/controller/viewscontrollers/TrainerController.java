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

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/mvc/trainer")
@Scope("session")
@SessionAttributes("user")
public class TrainerController {

    @PostMapping("/traineract")
    public String traineract(
            @RequestParam(required = false, value = "studentId") String studentId,
            @RequestParam(required = false, value = "thId") String thId,
            @RequestParam("act") String act,
            @RequestParam(required = false, value = "mark") String mark,
            @RequestParam("groupId") Integer groupId,
            Model model
    ) {

        if (studentId == null || studentId.equals("") || thId == null || thId.equals("")) {
            String massage = "field are not valid";
            model.addAttribute("massage", massage);
            return "exception";
        }

        MVCTrainerActStratagy stratagy = ChangeTrinerActStratagy.getStratagy(act);
        return stratagy.doAct(studentId, thId, mark, model, groupId);
    }

    @PostMapping("/dodeletemark")
    public String dodeletemark(
            @RequestParam(required = false, value = "markId") String[] markId,
            @RequestParam("th") String thId,
            @RequestParam("student") String studentId,
            @RequestParam("act") String act,
            @RequestParam("groupId") Integer groupId,
            Model model
    ) {
        try {
            ThreadRepositoryFactory.getRepository().deleteMarksById(
                    ParserStringToInt.parseArrayString(markId),
                    ParserStringToInt.simpleParserStringToInt(thId),
                    ParserStringToInt.simpleParserStringToInt(studentId));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            String massage = "data for delete are not valid";
            model.addAttribute("massage", massage);
            return "exception";
        }
        model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));
        return "TrainerControlPage/trainerstartpage";
    }

    @PostMapping("/dochangemark")
    public String dochangemark(

            @RequestParam("thId") String thId,
            @RequestParam("studentId") String studentId,
            @RequestParam(required = false, value = "markId") String[] markId,
            @RequestParam(required = false, value = "markValue") String[] markValue,
            @RequestParam("groupId") Integer groupId,
            Model model
    ) {
        log.info("change controller={}", studentId);
        try {
            ThreadRepositoryFactory.getRepository().changeMark(MarkIdMarkValueIntegration.doIntegration(markValue, markId),
                    ParserStringToInt.simpleParserStringToInt(studentId), ParserStringToInt.simpleParserStringToInt(thId));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            String massage = "mark for change not valid";
            model.addAttribute("massage", massage);
            return "exception";
        }
        model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));
        return "TrainerControlPage/trainerstartpage";
    }

    @GetMapping("/prepare")
    public String prepareMain(@RequestParam(required = false, value = "groupId") Integer groupId,
                              Model model,
                              HttpSession session) {
        Group group = null;
        if (groupId != null && groupId != 0)
            group = ThreadRepositoryFactory.getRepository().allGroup().get(groupId);
        else
            try {
                if (session != null)
                    group = (Group) session.getAttribute("groupT");
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                return "exception";
            }
        model.addAttribute("groupT", group);
        return "TrainerControlPage/trainerstartpage";
    }

}
