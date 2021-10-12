package Servlets;
import Servlets.DAO.DaoImp;
import ThreadModel.Salary;
import Users.Trainer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;



@WebServlet ("/addSalaryForTrainer")
public class SalaryAdderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trainerId = req.getParameter("trainer");
        String sal = req.getParameter("sal");
        req.getRequestDispatcher(salaryresultadder(trainerId, sal)).forward(req, resp);
    }


    private  String salaryresultadder (String tr, String sal) {
        String result = null;
        DaoImp daoImp = new DaoImp();

           if (tr!=null && sal !=null) {
                   try {int id =  Integer.parseInt(tr);
                   int salary = Integer.parseInt(sal);
                   Trainer trainer = (Trainer) daoImp.getUser(id);
                   trainer.getSalarylist().add(new Salary(new BigDecimal(salary)));
                   result ="adminActList.jsp";
               }
               catch (IllegalArgumentException e) {
                       result = "exeception.jsp";
               }

           }
           return  result;


       }

}



