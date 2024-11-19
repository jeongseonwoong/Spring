package Spring.Basic.prototype;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class NeedMyBean2 {

    @Autowired
    private ObjectProvider<MyBean> myBeanProvider;


    public MyBean getMyBean() {
        return myBeanProvider.getObject();
    }

    public MyBean getMyBean2() {
        return new MyBean();//(기능 변경 시 코드 변경이 일어나는) OCP에 위반되서 사용하지 않나?
    }


}
