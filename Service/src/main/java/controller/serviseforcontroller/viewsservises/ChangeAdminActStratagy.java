package controller.serviseforcontroller.viewsservises;


import controller.serviseforcontroller.actadminstrategy.*;

import java.util.Map;

public class ChangeAdminActStratagy {
    public static MVCAdminActStrategy getStratagy(String act) {
        Map<String, MVCAdminActStrategy> stratagyMap = Map.of(
                "create", new MVCAdminActCreateStrategyImpl(),
                "delete", new MVCAdminActDeleteStrategyImpl(),
                "change", new MVCAdminActChangeStrategyImpl(),
                "watch", new MVCAdminActWatchStrategyImpl());
        return stratagyMap.get(act);
    }
}