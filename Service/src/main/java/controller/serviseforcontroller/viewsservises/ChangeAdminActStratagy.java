package controller.serviseforcontroller.viewsservises;


import controller.serviseforcontroller.acttrainerstrategy.*;

import java.util.Map;

public class ChangeAdminActStratagy {
    public static MVCAdminActStratagy getStratagy (String act) {
        Map <String, MVCAdminActStratagy> stratagyMap = Map.of(
                "create", new MVCAdminActCreateStratagyImpl(),
                "delete", new MVCAdminActDeleteStratagyImpl(),
                "change", new MVCAdminActChangeStratagyImpl(),
                "watch", new MVCAdminActWatchStratagyImpl());
        return stratagyMap.get(act);
    }
}