package ServletsTest;

import Servlets.StudentServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StudentServletTest {

    @Test
    public void doGet() throws IOException {
//        final StudentServlet servlet = new StudentServlet();
//        final HttpServletResponse response = mock(HttpServletResponse.class);
//        final HttpServletRequest request = mock(HttpServletRequest.class);
//        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
//        HttpSession session = request.getSession();
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter writer = new PrintWriter(stringWriter);
//        when(response.getWriter()).thenReturn(writer);
//        servlet.doGet(request, response);
//
//        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
//        writer.flush(); // it may not have been flushed yet...
//        assertTrue(stringWriter.toString().contains("My expected string"));
    }
}