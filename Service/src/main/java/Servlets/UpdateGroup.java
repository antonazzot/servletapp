package Servlets;

import DataBase.DataBaseInf;
import Repository.DAO.DaoImp;
import ThreadModel.Group;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        req.setAttribute("freetr", getFreedomTrener());
        req.getRequestDispatcher("adminControl/actionchangegroup.jsp").forward(req, resp);
    }

    private ArrayList <Trainer> getFreedomTrener () {
        ArrayList <Trainer> freedoomTrainer = new ArrayList<>();
        ArrayList <Trainer> trainers =
               (ArrayList<Trainer>) DataBaseInf.groupHashMap.values().stream()
                       .map(g -> g.getTrainer())
                       .collect(Collectors.toList());
        for (Trainer tr:
         trainers) {
        for (int i = 0; i < DataBaseInf.trainerHashMap.size(); i++) {
            Trainer trainer = (Trainer) DataBaseInf.trainerHashMap.get(i);
            if (!tr.equals(trainer))
                freedoomTrainer.add(tr);
        }
    }
        return freedoomTrainer;
    }
    private HashSet<Theams> theamsSetcreater(Set <Theams> grth) {
        HashSet<Theams> result = new HashSet<>();
        ArrayList<Theams> allTh  = new ArrayList<>(List.of(Theams.values()));
        for (int i = 0; i < allTh.size(); i++) {
            for (int j = 0; j < grth.size(); j++) {
                if (!grth.contains(allTh.get(i))) {
                    result.add(allTh.get(i));
                }
            }
        }
        return result;
    }
}




