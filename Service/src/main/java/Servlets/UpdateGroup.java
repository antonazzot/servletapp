package Servlets;

import DataBase.DataBaseInf;
import ThreadModel.Group;
import ThreadModel.Theams;
import Users.Trainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 This servlet  provides all  necessary information to JSP
 for further changes in created group
 **/
@WebServlet("/updateGroup")
public class UpdateGroup extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("groupid"));
        Group group = DataBaseInf.groupHashMap.get(id);
        req.setAttribute("id", id);
        req.setAttribute("groupst", group.getStudentMap());
        req.setAttribute("allst", DataBaseInf.studentHashMap);
        req.setAttribute("groupth", group.getTheamsSet());
        req.setAttribute("freeth", theamsSetcreater(group.getTheamsSet()));
        req.setAttribute("grouptr", group.getTrainer());
        req.setAttribute("freetr", getFreedomTrainer ());
        req.getRequestDispatcher("adminControl/actionchangegroup.jsp").forward(req, resp);
    }

    private ArrayList <Trainer> getFreedomTrainer () {
//        ArrayList <Trainer> freedoomTrainer = new ArrayList<>();
//        ArrayList <Trainer> trainers =
//               (ArrayList<Trainer>) DataBaseInf.groupHashMap.values().stream()
//                       .map(g -> g.getTrainer())
//                       .collect(Collectors.toList());
//
//        for (int i = 0; i < DataBaseInf.trainerHashMap.size(); i++) {
//            Trainer trainer = (Trainer) DataBaseInf.trainerHashMap.get(i);
//            for (Trainer tr:
//                    trainers) {
//            if (tr!=null &&  trainer!=null && trainer.getId()!=tr.getId())
//                freedoomTrainer.add(trainer);
//        }
//    }
//        return freedoomTrainer;
        return null;
    }
    private HashSet<Theams> theamsSetcreater(Set <Theams> grth) {
        HashSet<Theams> result = new HashSet<>();
//        ArrayList<Theams> allTh  = new ArrayList<>(List.of(Theams.values()));
//        for (Theams theams : allTh) {
//            for (int j = 0; j < grth.size(); j++) {
//                if (!grth.contains(theams)) {
//                    result.add(theams);
//                }
//            }
//        }
        return result;
    }
}




