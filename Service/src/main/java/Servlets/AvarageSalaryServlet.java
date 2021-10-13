package Servlets;

import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import Servlets.Filters.StartFilter;
import ThreadModel.Salary;
import Users.Trainer;
import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/avarageSalary")
public class AvarageSalaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("map", salaryHashMap());
        req.getRequestDispatcher("adminControl/avaragesalary.jsp").forward(req, resp);
    }

    private HashMap<Trainer, ArrayList<Salary>> salaryHashMap() {
        DaoImp daoImp = new DaoImp();
        HashMap<Trainer, ArrayList<Salary>> result = new HashMap<>();
        for (Map.Entry<Integer, UserImpl> entry : DataBaseInf.trainerHashMap.entrySet()) {
            Trainer trainer = (Trainer) daoImp.getUser(entry.getKey());
            result.put(trainer, (ArrayList<Salary>) trainer.getSalarylist());
        }
        return result;
    }

}



