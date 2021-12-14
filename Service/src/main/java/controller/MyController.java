package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/mvc/start")
    public String start () {
        return "mvc_start";
    }
}
