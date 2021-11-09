package servlets;

import repository.threadmodelrep.ThreadRepositoryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/theamAdd")
public class TheamAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String theamName = req.getParameter("theam");
        ThreadRepositoryFactory.getRepository().addTheam(theamName);
        req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);
    }
}
