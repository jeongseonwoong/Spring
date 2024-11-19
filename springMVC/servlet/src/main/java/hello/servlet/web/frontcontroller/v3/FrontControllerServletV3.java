package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String,ControllerV3> controllers = new HashMap<String,ControllerV3>();

    FrontControllerServletV3() {
        controllers.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllers.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllers.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> data = new HashMap<String, String>();
        data=createParamMap(req);
        String requestURI = req.getRequestURI();
        ControllerV3 controller = controllers.get(requestURI);
        if (controller == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
            ModelView modelView = controller.process(data);
            MyView mv = viewResolver(modelView.getViewName());
            mv.render(modelView.getModel(),req, resp);
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}




