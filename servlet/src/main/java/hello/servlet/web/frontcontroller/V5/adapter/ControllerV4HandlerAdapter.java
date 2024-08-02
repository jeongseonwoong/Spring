package hello.servlet.web.frontcontroller.V5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.V5.MyHandlerAdapter;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerAdapter;

import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {


    @Override
    public boolean supports(Object handler) {
        return handler instanceof ControllerV4;
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ControllerV4 controller = (ControllerV4) handler;

        Map<String,Object> modelMap = new HashMap<>();
        Map<String, String> paramMap = createParamMap(request);
        String viewName = controller.process(paramMap, modelMap);

        ModelView mv = new ModelView(viewName);
        mv.setModel(modelMap);
        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }
}
