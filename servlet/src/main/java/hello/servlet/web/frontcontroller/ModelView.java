package hello.servlet.web.frontcontroller;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ModelView {
    private String viewName;
    private Map<String, Object> model = new HashMap<String, Object>();
    public ModelView(String viewName) {
        this.viewName = viewName;
    }
}
