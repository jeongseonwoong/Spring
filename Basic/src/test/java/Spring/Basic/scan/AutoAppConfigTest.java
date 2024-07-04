package Spring.Basic.scan;

import Spring.Basic.AutoAppConfig;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    void basicScan(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService ms = ac.getBean(MemberService.class);
        Assertions.assertThat(ms).isInstanceOf(MemberService.class);
    }
}
