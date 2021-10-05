package Servlets;

import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
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
import java.util.Map;

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
        try {
            if (req.getParameter("id")!=null)
           id = Integer.parseInt( req.getParameter("id"));
        }
        catch (IllegalArgumentException e) {
            resp.getWriter().write("Enter right id");
        }
        DaoImp daoImp = new DaoImp();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        Writer writer =resp.getWriter();


        if (act.equalsIgnoreCase("create")) {
            req.getRequestDispatcher("adminControl/adduserpage.jsp").forward(req, resp);
        }
        else if (act.equalsIgnoreCase("delete")){
                daoImp.deleteUser(id);
        }
        else if (act.equalsIgnoreCase("change")){
                        writer.write("changepage");
        }
        else if (act.equalsIgnoreCase("watch")) {
        switch (user)
           {
            case "student": writer.write (watch(DataBaseInf.studentHashMap));
            case "trainer": writer.write (watch(DataBaseInf.studentHashMap));
            case "administrator": writer.write (watch(DataBaseInf.studentHashMap));
            case "group": writer.write (watch(DataBaseInf.studentHashMap));
            }
            }
                  }


    private String watch(Map<Integer, UserImpl> map) {
        return map.values().toString();
    }


    }



