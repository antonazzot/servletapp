package controller.serviseforcontroller.viewsservises;


import controller.serviseforcontroller.acttrainerstratagy.*;

import java.util.Map;

public class ChangeTrinerActStratagy {
    public static MVCTrainerActStratagy getStratagy(String act) {
        Map<String, MVCTrainerActStratagy> stratagyMap = Map.of(
                "create", new MVCTrainerActCreateStratagyImpl(),
                "delete", new MVCTrainerActDeleteStratagyImpl(),
                "change", new MVCTrainerActChangeStratagyImpl(),
                "watch", new MVCTrainerActWatchStratagyImpl());
        return stratagyMap.get(act);
    }
}

