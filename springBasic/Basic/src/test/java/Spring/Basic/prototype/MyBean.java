package Spring.Basic.prototype;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class MyBean {

    private int count;

    @PostConstruct
    public void init() {
        System.out.println("MyBean init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("MyBean destroy");
    }

    public int add()
    {
        count++;
        return count;
    }

    public int getCount() {
        return count;
    }
}
