package Spring.Basic.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient{

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출 =" + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect(){
        System.out.println("connect: " +url);
    }

    public void call(String message){
        System.out.println("call: " +url + "message: " +message);
    }

    public void disconnect(){
        System.out.println("close"+url);
    }

    //빈이 소멸되기 직전에 자동으로 호출됨.
    @PostConstruct
    public void close() throws Exception {
        disconnect();
    }

    @PreDestroy
    //빈에 등록되고 의존관계 주입까지 끝났을 때 자동으로 호출됨.
    public void init() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }
}

