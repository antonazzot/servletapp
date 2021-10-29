package Servlets;

import Repository.ThreadModelRep.ThreadRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet ("/updateTheam")
public class TheamUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       int theamId = Integer.parseInt(req.getParameter("thid"));
       String theamName = req.getParameter("thname");
        ThreadRepositoryImpl.getInstance().updateTheam(theamId, theamName);
        req.setAttribute("map", ThreadRepositoryImpl.getInstance().allTheams());
        req.getRequestDispatcher("adminControl/changeTheam.jsp").forward(req, resp);
    }
}
