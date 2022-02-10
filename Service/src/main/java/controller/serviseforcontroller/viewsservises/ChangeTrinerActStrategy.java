package controller.serviseforcontroller.viewsservises;


import controller.serviseforcontroller.acttrainerstratagy.*;
import controller.serviseforcontroller.senderservice.SenderService;

import java.util.Map;

public class ChangeTrinerActStrategy {
    public static MVCTrainerActStratagy getStrategy(String act) {
        Map<String, MVCTrainerActStratagy> stratagyMap = Map.of(
                "create", new MVCTrainerActCreateStratagyImpl(),
                "delete", new MVCTrainerActDeleteStratagyImpl(),
                "change", new MVCTrainerActChangeStratagyImpl(),
                "watch", new MVCTrainerActWatchStratagyImpl());
        return stratagyMap.get(act);
    }
}

