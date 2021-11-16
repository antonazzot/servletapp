package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet witch based on "hard-code"
 * providing users with role Administrator
 * choice made some action with user
 * and entity page
 **/
@WebServlet(value = "/watchServlet")
public class WatchServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(WatchServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String user = req.getParameter("user");
        int entityId = 0;


        if (act.equalsIgnoreCase("create")) {
            if (!user.equals("group") && !user.equals("theam")) {
                // add user
                log.info("Create user ={}", user);
                req.setAttribute("role", user);
                req.getRequestDispatcher("adminControl/adduserpage.jsp").forward(req, resp);
            } else {
                if (!user.equals("theam")) {
                    // add group
                    log.info("Create group ={}", user);
                    req.setAttribute("mapIS", RepositoryFactory.getRepository().allStudent());
                    req.setAttribute("mapITr", RepositoryFactory.getRepository().freeTrainer());
                    req.setAttribute("mapITe", ThreadRepositoryFactory.getRepository().freeTheams());
                    req.getRequestDispatcher("adminControl/theamscreatelist.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("adminControl/theamadd.jsp").forward(req, resp);
                }
            }
        } else if (act.equalsIgnoreCase("delete")) {
            try {
                entityId = Integer.parseInt(req.getParameter("id"));
            } catch (IllegalArgumentException e) {
                req.getRequestDispatcher("exeception.jsp").forward(req, resp);
            }
            RepositoryFactory.getRepository().removeUser(entityId, user);
            req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);

        } else if (act.equalsIgnoreCase("change")) {
            // change entity
            if (!user.equals("group") && !user.equals("theam")) {
                log.info("Change entity ={}", user);
                req.setAttribute("map", mapToChange(user));
                req.getRequestDispatcher("adminControl/changeUser.jsp").forward(req, resp);
            } else if (user.equals("theam")) {
                req.setAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
                req.getRequestDispatcher("adminControl/changeTheam.jsp").forward(req, resp);
            }
            if (user.equals("group")) {
                req.setAttribute("map", ThreadRepositoryFactory.getRepository().allGroup());
                req.getRequestDispatcher("adminControl/changeGroup.jsp").forward(req, resp);
            }


        } else if (act.equalsIgnoreCase("watch")) {
            if (!user.equals("group") && !user.equals("theam")) {
                req.setAttribute("map", forDemonstratePage(user));
                req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);

            } else if (!user.equals("theam")) {
                log.info("Watch group ={}", user);
                req.setAttribute("map", groupStringHashMap());
                req.getRequestDispatcher("adminControl/demonstrategroup.jsp").forward(req, resp);
            } else {
                req.setAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
                req.getRequestDispatcher("adminControl/theamdemonstrate.jsp").forward(req, resp);
            }
        }

    }

    /**
     * This method providing represent of information about
     * user with @mapWithInf() method
     **/
    private HashMap<Integer, UserImpl> forDemonstratePage(String user) {
        HashMap<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }

    /**
     * This method given data for future users change
     **/
    private HashMap<Integer, UserImpl> mapToChange(String user) {
        HashMap<Integer, UserImpl> result = new HashMap<>();
        switch (user) {
            case "student":
                result = RepositoryFactory.getRepository().allStudent();
                break;
            case "trainer":
                log.info("Watch student ={}", user);
                result = RepositoryFactory.getRepository().allTrainer();
                break;
            case "administrator":
                result = RepositoryFactory.getRepository().allAdmin();
                break;
        }
        return result;
    }

    /**
     * This method  given  data for demonstrate  information about group
     **/
    private HashMap<Group, HashMap<ArrayList<Theams>, ArrayList<Student>>> groupStringHashMap() {
        HashMap<Group, HashMap<ArrayList<Theams>, ArrayList<Student>>> hashMap = new HashMap<>();
        for (Map.Entry<Integer, Group> entry : ThreadRepositoryFactory.getRepository().allGroup().entrySet()) {
            Group group = entry.getValue();
            ArrayList<Theams> theams = new ArrayList<>(ThreadRepositoryFactory.getRepository().theamFromGroup(group.getId()));
            ArrayList<Student> students = RepositoryFactory.getRepository().studentFromGroup(group.getId());
            hashMap.put(group, new HashMap<>(Map.of(theams, students)));
        }
        return hashMap;
    }
}



