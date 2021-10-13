package Servlets;

import DataBase.DataBaseInf;
import Repository.DAO.DaoImp;
import ThreadModel.Group;
import Action.*;
import Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet(value = "/watchServlet")
public class WatchServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(WatchServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String user = req.getParameter("user");
        int id = 0;
        DaoImp daoImp = new DaoImp();
        Writer writer = resp.getWriter();

        if (act.equalsIgnoreCase("create")) {
            if (!user.equals("group")) {
                req.setAttribute("role", user);
                req.getRequestDispatcher("adminControl/adduserpage.jsp").forward(req, resp);
            } else {
                req.setAttribute("map", mapWithInf(Role.STUDENT));
                req.setAttribute("map1", individTrainerMap.theamsHashSet(mapWithInf(Role.TRAINER)));
                req.setAttribute("set", IndividSetMap.theamsHashSet());
                req.getRequestDispatcher("adminControl/theamscreatelist.jsp").forward(req, resp);
            }
        } else if (act.equalsIgnoreCase("delete")) {
            if (req.getParameter("id") != null) {
                try {
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (IllegalArgumentException e) {
                    resp.getWriter().write("Enter right id");
                }
            }
            daoImp.deleteUser(id);
            req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);
        } else if (act.equalsIgnoreCase("change")) {
            writer.write("Page not exist");
        } else if (act.equalsIgnoreCase("watch")) {
            if (!user.equals("group")) {
                switch (user) {
                    case "student":
                        req.setAttribute("map", mapWithInf(Role.STUDENT));
                        req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);
                        break;
                    case "trainer":
                        req.setAttribute("map", mapWithInf(Role.TRAINER));
                        req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);
                        break;
                    case "administrator":
                        req.setAttribute("map", mapWithInf(Role.ADMINISTRATOR));
                        req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);
                        break;
                }
            } else {
                req.setAttribute("map", groupStringHashMap());
                req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);
            }
        }
    }


    private HashMap<UserImpl, String> mapWithInf(Role role) {
        HashMap<UserImpl, String> hashMap = new HashMap<>();
        if (Role.STUDENT.equals(role)) {
            for (Map.Entry<Integer, UserImpl> entry : DataBaseInf.studentHashMap.entrySet()) {
                Student student = (Student) entry.getValue();
                String inf = student.getInf();
                hashMap.put(student, inf);
            }
        } else if (Role.TRAINER.equals(role)) {
            {
                for (Map.Entry<Integer, UserImpl> entry : DataBaseInf.trainerHashMap.entrySet()) {
                    Trainer trainer = (Trainer) entry.getValue();
                    String inf = trainer.getInf();
                    hashMap.put(trainer, inf);
                }
            }
        } else if (Role.ADMINISTRATOR.equals(role)) {
            for (Map.Entry<Integer, UserImpl> entry : DataBaseInf.adminHashMap.entrySet()) {
                Administrator administrator = (Administrator) entry.getValue();
                String inf = administrator.getInf();
                hashMap.put(administrator, inf);
            }
        }
        return hashMap;
    }

    private HashMap<Group, String> groupStringHashMap() {
        HashMap<Group, String> hashMap = new HashMap<>();
        for (Map.Entry<Integer, Group> entry : DataBaseInf.groupHashMap.entrySet()) {
            Group group = (Group) entry.getValue();
            String inf = group.getInf();
            hashMap.put(group, inf);
        }
        return hashMap;
    }
}



