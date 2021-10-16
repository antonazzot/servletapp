package Servlets;

import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 It's LogOut servlet
 witch providing session invalidation
 when user logout
 **/
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LogOutServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserImpl user = (UserImpl) session.getAttribute("user");
        log.info("UserOut = {}", user.getId());
        session.invalidate();
        resp.sendRedirect("/web/hello");
    }
}
