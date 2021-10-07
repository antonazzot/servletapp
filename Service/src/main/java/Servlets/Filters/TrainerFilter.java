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
//@WebFilter(urlPatterns = {"TrainerToStartServlet" , "/TrainerToStartServlet/*", "/TrainerToStartServlet"})
public class TrainerFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!request.getSession(false).isNew()) {
            try {
                HttpSession session = request.getSession(false);
                UserImpl user = (UserImpl) session.getAttribute("user");
                if (session.getAttribute("user") == null || (user == null) || (!Role.TRAINER.equals(user.getRole())))
                    response.sendRedirect("/web/hello");
                filterChain.doFilter(request, response);
            } catch (IllegalArgumentException e) {
                response.sendRedirect("/web/hello");;
                filterChain.doFilter(request, response);
            }
        }
        filterChain.doFilter(request, response);
    }
}
