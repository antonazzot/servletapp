package Servlets;

import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import Users.Student;
import Users.UserImpl;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(value = "/watchServlet")
public class WatchServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String user = req.getParameter("user");
        int id =0;
        DaoImp daoImp = new DaoImp();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        Writer writer =resp.getWriter();


        if (act.equalsIgnoreCase("create")) {
            req.getRequestDispatcher("adminControl/adduserpage.jsp").forward(req, resp);
        }
        else if (act.equalsIgnoreCase("delete")){
            if (req.getParameter("id")!=null ) {
                try {
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (IllegalArgumentException e) {
                    resp.getWriter().write("Enter right id");
                }
            }
                daoImp.deleteUser(id);
        }
        else if (act.equalsIgnoreCase("change")){
                        writer.write("changepage");
        }
        else if (act.equalsIgnoreCase("watch")) {
        switch (user)
           {
            case "student":
              writer.write(DataBaseInf.studentHashMap.values().stream().collect(Collectors.toList()).
                      stream().map(s->(Student)s).map(s->s.getInf()).toString());



                req.getRequestDispatcher("demonstrate.jsp").forward(req,resp);

            break;
            case "trainer":
                req.setAttribute("map", DataBaseInf.trainerHashMap);
                req.getRequestDispatcher("demonstrate.jsp").forward(req,resp);

            break;
            case "administrator":
                req.setAttribute("map", DataBaseInf.adminHashMap);
                req.getRequestDispatcher("demonstrate.jsp").forward(req,resp);

            break;
            case "group":
                req.setAttribute("map", DataBaseInf.groupHashMap);
                req.getRequestDispatcher("demonstrate.jsp").forward(req,resp);
            break;
            }
            }
                  }

    }



