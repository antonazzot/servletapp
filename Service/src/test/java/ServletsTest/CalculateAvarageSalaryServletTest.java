package ServletsTest;

import DataBase.DataBaseInf;
import Servlets.CalculateAvarageSalaryServlet;
import ThreadModel.Salary;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalculateAvarageSalaryServletTest {
//    final String path = "avaragesalarywatch.jsp";
//    @Test
//    public void doGet() throws ServletException, IOException {
//        final CalculateAvarageSalaryServlet servlet = new CalculateAvarageSalaryServlet();
//
//        final HttpServletResponse response = mock(HttpServletResponse.class);
//        final HttpServletRequest request = mock(HttpServletRequest.class);
//        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
//
////        int actual = DataBaseInf.trainerHashMap.values().size();
////        int expected = 1;
////        assertEquals(actual, expected);
//
//        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
//    //    servlet.doGet (request, response);
//         verify(request, times(1)).getRequestDispatcher("path");
//         verify(request, never()).getSession();
//         verify(dispatcher).forward(request, response);
//
//    }

}