package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/*
1. 파라미터 전송기능
http://localhost:8080/request-param?username=hello&age=20
 */
@WebServlet(name="requestParamServlet",urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("전체 파라미터 조회");
        request.getParameterNames().asIterator().forEachRemaining(paramName-> System.out.println(paramName+"="+request.getParameter(paramName)));
        System.out.println("전체 파라미터 조회");
        System.out.println();

        System.out.println("파라미터 하나 가져오기");
        System.out.println("age ="+ request.getParameter("age"));
        System.out.println("username ="+ request.getParameter("username"));
        System.out.println();

        System.out.println("같은 이름을 가진 파라미터 조회");
        String[] usernames = request.getParameterValues("username");
        for (String username : usernames) {
            System.out.println("username="+username);
        }
        response.getWriter().write("ok");
    }

}