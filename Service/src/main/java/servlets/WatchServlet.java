package servlets;

import lombok.extern.slf4j.Slf4j;
import servlets.servletstratagy.watchservletstratagy.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
@Slf4j
@WebServlet(value = "/watchServlet")
public class WatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String entity = req.getParameter("user");
        WatchServletStratagy servletStratagy = changeStratagy(act);
        req.getRequestDispatcher(servletStratagy.watchEntity(entity, req)).forward(req, resp);

    }

    private WatchServletStratagy changeStratagy(String act) {
        log.info("Change stratagy in watch servlet = {}", "Act: " +  act );
        Map<String, WatchServletStratagy> stratagyMap = Map.of(
                "create", new WatchServletCreateStratagyImpl(),
                "delete", new WatchServletDeleteStratagyImpl(),
                "change", new WatchServletChangeStratagyImpl(),
                "watch", new WatchServletWatchStratagyImpl());
        return stratagyMap.get(act);
    }
}



