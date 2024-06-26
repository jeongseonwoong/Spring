package Spring.Basic.order;

import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder()
    {
        Long memberId = 13L;
        Member member = new Member("TestMember",memberId, Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberService.findMember(memberId).getId(), "TestItem", 10000);
        Assertions.assertThat(order.getItemPrice()).isEqualTo(10000);
        Assertions.assertThat(order.calculatePrice()).isEqualTo(9000);
        Assertions.assertThat(order.getItemName()).isEqualTo("TestItem");
        Assertions.assertThat(order.getMemberId()).isEqualTo(13L);
    }
}
