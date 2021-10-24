package Servlets;

import Action.IndividSetMap;
import Action.individTrainerMap;
import DataBase.DataBaseInf;
import DAO.DaoImp;
import Repository.RepositoryFactory;
import Repository.ThreadModelRep.ThreadRepositoryImpl;
import ThreadModel.Group;
import ThreadModel.Theams;
import Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 This servlet witch based on "hard-code"
 providing users with role Administrator
 choice made some action with user
 and entity page
 **/
@WebServlet(value = "/watchServlet")
public class WatchServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(WatchServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String user = req.getParameter("user");
        int id = 0;
        DaoImp daoImp = new DaoImp();


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
                    req.setAttribute("mapITe", ThreadRepositoryImpl.getInstance().freeTheams());
                    req.getRequestDispatcher("adminControl/theamscreatelist.jsp").forward(req, resp);
                }
                else
                {
                    req.getRequestDispatcher("adminControl/theamadd.jsp").forward(req, resp);
                }
            }
        } else if (act.equalsIgnoreCase("delete")) {
            // delete entity by id (user and group)
            if (req.getParameter("id") != null) {
                try {
                    id = Integer.parseInt(req.getParameter("id"));
                } catch (IllegalArgumentException e) {
                    req.getRequestDispatcher("exeception.jsp").forward(req, resp);
                }
            }
            log.info("Delete entity ={}", id);
            RepositoryFactory.getRepository().removeUser(id);
            req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);

        } else if (act.equalsIgnoreCase("change")) {
            // change entity
            if (!user.equals("group")) {
                log.info("Change entity ={}", user);
                req.setAttribute("map", mapToChange(user));
                req.getRequestDispatcher("adminControl/changeUser.jsp").forward(req, resp);
            } else {
                req.setAttribute("map", DataBaseInf.groupHashMap);
                req.getRequestDispatcher("adminControl/changeGroup.jsp").forward(req, resp);
            }

        } else if(act.equalsIgnoreCase("watch"))
            {
            if (!user.equals("group") && !user.equals("theam") ) {
            req.setAttribute("map", forDemonstratePage(user));
            req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);

            } else
            if (!user.equals("theam"))
            {
            log.info("Watch group ={}", user);
            req.setAttribute("map", groupStringHashMap());
            req.getRequestDispatcher("adminControl/demonstrategroup.jsp").forward(req, resp);
             }
            else
            {
                req.setAttribute("map", ThreadRepositoryImpl.getInstance().allTheams());
                req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);
            }
    }

}

    /**
    This method providing represent of information about
    user with @mapWithInf() method
   **/
    private HashMap <Integer, UserImpl> forDemonstratePage (String user) {
        HashMap  <Integer, UserImpl> result =  new HashMap<>();
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
     This method given data for future users change
     **/
    private  HashMap <Integer, UserImpl> mapToChange(String user)  {
        HashMap <Integer, UserImpl> result =  new HashMap<>();
        switch (user) {
            case "student":
                result = (HashMap<Integer, UserImpl>) DataBaseInf.studentHashMap;
                break;
            case "trainer":
                log.info("Watch student ={}", user);
                result = (HashMap<Integer, UserImpl>) DataBaseInf.trainerHashMap;
                break;
            case "administrator":
                result = (HashMap<Integer, UserImpl>) DataBaseInf.adminHashMap;
                break;
                  }
                  return result;
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
    /**
     This method  given  data for demonstrate  information about group
     **/
    private HashMap<Group, HashMap<ArrayList<Theams>, ArrayList<UserImpl>>> groupStringHashMap() {
        HashMap<Group, HashMap<ArrayList<Theams>, ArrayList<UserImpl>>> hashMap = new HashMap<>();
        for (Map.Entry<Integer, Group> entry : ThreadRepositoryImpl.getInstance().allGroup().entrySet()) {
            Group group = entry.getValue();
            ArrayList <Theams> theams = new ArrayList<>(ThreadRepositoryImpl.getInstance().theamFromGroup(group.getId()));
            ArrayList<UserImpl> students = RepositoryFactory.getRepository().studentFromGroup(group.getId());
            hashMap.put(group, new HashMap<>(Map.of(theams,students)));
        }
        return hashMap;
    }
}



