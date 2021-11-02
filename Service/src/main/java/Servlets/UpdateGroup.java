package Servlets;

import DataBase.DataBaseInf;
import Repository.RepositoryFactory;
import Repository.ThreadModelRep.ThreadRepositoryFactory;
import Repository.ThreadModelRep.ThreadRepositoryImpl;
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
        Group group = ThreadRepositoryFactory.getRepository().allGroup().get(id);
        req.setAttribute("id", id);
        req.setAttribute("thisgroup", group);
        req.setAttribute("groupst", RepositoryFactory.getRepository().studentFromGroup(id));
        req.setAttribute("allst", RepositoryFactory.getRepository().allStudent());
        req.setAttribute("groupth", ThreadRepositoryFactory.getRepository().theamFromGroup(id));
        req.setAttribute("freeth", ThreadRepositoryFactory.getRepository().freeTheams());
        req.setAttribute("freetr", RepositoryFactory.getRepository().freeTrainer());
        req.getRequestDispatcher("adminControl/actionchangegroup.jsp").forward(req, resp);
    }

}




