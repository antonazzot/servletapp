package Servlets;

import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import Users.Role;
import Users.Student;
import Users.UserImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

@WebServlet(value = "/adminControl/watchServlet")
public class WatchServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String user = req.getParameter("user");
        int id = Integer.parseInt(req.getParameter("id"));

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        Writer writer =resp.getWriter();
        if (user.equalsIgnoreCase("student")) {
            if (act.equalsIgnoreCase("watch")) {

                writer.write("Student list: " + "<p>" + "</p>");
                Collection<UserImpl> studentList = DataBaseInf.studentHashMap.values();
                studentList.stream().map(u -> (Student) u).forEach(s -> {
                    try {
                        writer.write
                                ("<p>" + " Student id: " + s.getId() + " " + '\n' + s.aboutStudent() + "</p>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
            else  if (act.equalsIgnoreCase("create")) {
                req.getRequestDispatcher("addstudentpage.jsp").forward(req,resp);
            }
            else if (act.equalsIgnoreCase("delete")) {

                DaoImp daoImp = new DaoImp();
                daoImp.deleteUser(id);
                req.getRequestDispatcher("addstudentpage.jsp").forward(req,resp);
            }
            else  if (act.equalsIgnoreCase("change")) {
                req.getRequestDispatcher("changestudent.jsp");
            }
        }
    }

}
