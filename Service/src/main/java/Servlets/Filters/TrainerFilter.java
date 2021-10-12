package Servlets.Filters;

import Users.Role;
import Users.Trainer;
import Users.UserImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/traineract", "/changeandcreatemark"})
public class TrainerFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/web/hello");
        } else {
            UserImpl user = (UserImpl) session.getAttribute("user");
            if (user == null || (!Role.TRAINER.equals(user.getRole())))
                response.sendRedirect("/web/hello");
            else if ((Role.TRAINER.equals(user.getRole()))) {
                filterChain.doFilter(request, response);
            }
        }

    }
}
