package Servlets.Filters;
import Users.Role;
import Users.UserImpl;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.net.http.HttpRequest;
@WebServlet ( "/WatchServlet")
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        UserImpl user = (UserImpl) session.getAttribute("user");
        if ((session == null) || (user == null ) || (Role.TRAINER.equals(user.getRole()) || Role.STUDENT.equals(user.getRole())) ) {
            Writer wt  = response.getWriter();
            wt.write("YEDDFG");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
