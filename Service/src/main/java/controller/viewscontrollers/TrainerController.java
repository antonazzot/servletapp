package controller.viewscontrollers;

import controller.serviseforcontroller.acttrainerstratagy.MVCTrainerActStratagy;
import controller.serviseforcontroller.senderservice.SenderService;
import controller.serviseforcontroller.viewsservises.ChangeTrinerActStrategy;
import controller.serviseforcontroller.viewsservises.MarkIdMarkValueIntegration;
import controller.serviseforcontroller.viewsservises.ParserStringToInt;
import helperutils.myexceptionutils.AppValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/mvc/trainer")
@Scope("session")
@SessionAttributes("user")
public class TrainerController {

    private final SenderService senderService;

    @PostMapping("/traineract")
    public String traineract(
            @RequestParam(required = false, value = "studentId") String studentId,
            @RequestParam(required = false, value = "thId") String thId,
            @RequestParam("act") String act,
            @RequestParam(required = false, value = "mark") String mark,
            @RequestParam("groupId") Integer groupId,
            Model model )
    {
        try {
            MVCTrainerActStratagy strategy = ChangeTrinerActStrategy.getStrategy(act);
            return strategy.doAct(studentId, thId, mark, model, groupId, senderService);
        }
        catch (AppValidException e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "exception";
        }
    }

    @PostMapping("/dodeletemark")
    public String dodeletemark(
            @RequestParam(required = false, value = "markId") String[] markId,
            @RequestParam("th") String thId,
            @RequestParam("student") String studentId,
            @RequestParam("act") String act,
            @RequestParam("groupId") Integer groupId,
            Model model )
    {
        try {
            ThreadRepositoryFactory.getRepository().deleteMarksById(
                    ParserStringToInt.parseArrayString(markId),
                    ParserStringToInt.simpleParserStringToInt(thId),
                    ParserStringToInt.simpleParserStringToInt(studentId));

            model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));
            return "TrainerControlPage/trainerstartpage";
        } catch (AppValidException e) {
            String message = e.getMessage();
            log.error(message);
            model.addAttribute("message", message);
            return "exception";
        }
    }

    @PostMapping("/dochangemark")
    public String dochangemark(
            @RequestParam("thId") String thId,
            @RequestParam("studentId") String studentId,
            @RequestParam(required = false, value = "markId") String[] markId,
            @RequestParam(required = false, value = "markValue") String[] markValue,
            @RequestParam("groupId") Integer groupId,
            Model model )
    {
        try {
            ThreadRepositoryFactory.getRepository().changeMark(MarkIdMarkValueIntegration.doIntegration(markValue, markId),
                    ParserStringToInt.simpleParserStringToInt(studentId), ParserStringToInt.simpleParserStringToInt(thId));
            model.addAttribute("groupT", ThreadRepositoryFactory.getRepository().allGroup().get(groupId));
            return "TrainerControlPage/trainerstartpage";
        } catch (AppValidException e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "exception";
        }

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
