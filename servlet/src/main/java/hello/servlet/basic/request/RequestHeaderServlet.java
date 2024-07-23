package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name="requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        printHeader(req);
        printStartLine(req);
    }

    void printStartLine(HttpServletRequest req)
    {
        System.out.println(req.getMethod());
        System.out.println(req.getProtocol());
    }

    void printHeader(HttpServletRequest req)
    {
        Enumeration<String> headerNames = req.getHeaderNames();
        while(headerNames.hasMoreElements())
        {
            String headerName = headerNames.nextElement();
            System.out.println("headerName = " + headerName);
        }
    }
}
