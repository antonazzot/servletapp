package servlets;

import repository.threadmodelrep.ThreadRepositoryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateTheam")
public class TheamUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int theamId = Integer.parseInt(req.getParameter("thid"));
        String theamName = req.getParameter("thname");
        ThreadRepositoryFactory.getRepository().updateTheam(theamId, theamName);
        req.setAttribute("map", ThreadRepositoryFactory.getRepository().allTheams());
        req.getRequestDispatcher("adminControl/changetheam.jsp").forward(req, resp);
    }
}
