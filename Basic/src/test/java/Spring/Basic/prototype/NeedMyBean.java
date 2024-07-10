package Spring.Basic.prototype;

import lombok.RequiredArgsConstructor;


public class NeedMyBean {

    public final MyBean myBean;
    public NeedMyBean(MyBean myBean) {
        this.myBean = myBean;
    }

}
