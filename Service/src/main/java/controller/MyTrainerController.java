package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/mvc/trainer")
public class MyTrainerController {
    @GetMapping ("/changestrategy")
    String changeStrategy (@RequestParam("studentId") String studentId,
                           @RequestParam("theamId") String theamId,
                           @RequestParam (required = false,name = "markId") String markId,
                            @RequestParam ("act") String act) {

        return null;
    }
}
