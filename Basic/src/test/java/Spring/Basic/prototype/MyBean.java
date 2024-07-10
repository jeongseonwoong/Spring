package Spring.Basic.prototype;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class MyBean {

    @PostConstruct
    public void init() {
        System.out.println("MyBean init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("MyBean destroy");
    }
}
