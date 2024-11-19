package Spring.Basic.order;

import Spring.Basic.discount.FixDiscountPolicy;
import Spring.Basic.member.MemberServiceImpl;
import Spring.Basic.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest2 {

    @Test
    void createOrder(){
        OrderServiceImpl orderService  =new OrderServiceImpl(new FixDiscountPolicy(),new MemberServiceImpl(new MemoryMemberRepository()));
        //set get으로 의존성 주입을 받을 경우 어떤 의존성 주입이 필요한지 직관적으로 알기 힘들기 때문에 위 처럼 생성자를 통해 의존성 주입이
        // 필요한 것을 직관적으로 알 수 있도록 만드는 것이 좋다.
        //final 키워드를 통해 한번
        Assertions.assertThat(orderService).isInstanceOf(OrderService.class);

    }

}