package Servlets.Filters;
import Users.Role;
import Users.UserImpl;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.net.http.HttpRequest;
@WebFilter( urlPatterns = {"/watchServlet", "/GroupCreaterServlet" })
public class UserFilter extends AbstractFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            HttpSession session = request.getSession(false);
            UserImpl user = (UserImpl) session.getAttribute("user");
            if (session.getAttribute("user") == null || (user == null) || (!Role.ADMINISTRATOR.equals(user.getRole())))
               response.sendRedirect("/web/hello");
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
          response.sendRedirect("/web/hello");
            filterChain.doFilter(request, response);
        }


        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }
}
