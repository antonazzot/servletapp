package controller.serviseforcontroller;

import controller.serviseforcontroller.actadminstratagy.*;
import servlets.servletstratagy.watchservletstratagy.WatchServletChangeStratagyImpl;
import servlets.servletstratagy.watchservletstratagy.WatchServletCreateStratagyImpl;
import servlets.servletstratagy.watchservletstratagy.WatchServletDeleteStratagyImpl;
import servlets.servletstratagy.watchservletstratagy.WatchServletWatchStratagyImpl;

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
