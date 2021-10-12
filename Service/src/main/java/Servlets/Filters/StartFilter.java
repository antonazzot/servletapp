package Servlets.Filters;
import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import Users.UserImpl;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/checkUser")
public class StartFilter extends AbstractFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserImpl user;
        int id  = 0;
        String login =null;
        boolean doesItLogin = false;
        try {
            id = Integer.parseInt( request.getParameter("id"));
        }
        catch (IllegalArgumentException e) {
            login = request.getParameter("id");
            doesItLogin = true;
        }
        String password = request.getParameter("password");

        if (doesItLogin) {
            user = checkUser(login, password);
        }
        else
            user = checkUser(id, password);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        request.getRequestDispatcher("/checkUser").forward(request, response);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private UserImpl checkUser (int id, String password) {
        DaoImp daoImp = new DaoImp();
        UserImpl user;
        user = itRightCondition(daoImp.getUsersMapById(id), id, password );
        return  user;
    }

    private UserImpl checkUser (String login, String password) {
        UserImpl user = itRightCondition(login, password);
        return  user;
    }

    private UserImpl itRightCondition (Map<Integer, UserImpl> map, int id, String password)  {
        if (map.get(id).getPassword().equalsIgnoreCase(password))
            return map.get(id);
        return null;
    }

    private UserImpl itRightCondition ( String login, String password)  {
        UserImpl user;
        Map<Integer, UserImpl> allUser = new HashMap<>();
        allUser.putAll(DataBaseInf.adminHashMap);
        allUser.putAll(DataBaseInf.studentHashMap);
        allUser.putAll(DataBaseInf.trainerHashMap);
        for (Map.Entry<Integer, UserImpl> entry: allUser.entrySet()) {
            if (entry.getValue().getLogin().equalsIgnoreCase(login) &&
                    entry.getValue().getPassword().equals(password))
            {
                user = entry.getValue();
                return user;
            }
        }
        return null;
    }

}