package servlets.filters;

import users.Role;
import users.UserImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(
//        urlPatterns = {"/watchServlet", "/GroupCreaterServlet",
//        "/avarageSalary", "/calculateAvarageSalary",
//        "/addSalaryForTrainer", "/addSalary", "/changeUserServlet", "/calculateAvarageSalary",
//        "/updateUser", "/updateGroup", "/UserActionServlet"}
//        urlPatterns = "/mvc/views/*"
)
public class UserFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null)
            response.sendRedirect(request.getContextPath()+"/mvc/hello");
        else {
            UserImpl user = (UserImpl) session.getAttribute("user");
            if ((user == null) || (!Role.ADMINISTRATOR.equals(user.getRole())))
                response.sendRedirect(request.getContextPath()+"/mvc/hello");
            else if ((Role.ADMINISTRATOR.equals(user.getRole()))) {
                filterChain.doFilter(request, response);
            }
        }
    }
}
