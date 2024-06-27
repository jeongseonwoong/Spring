package Spring.Basic;

import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;
import Spring.Basic.order.Order;
import Spring.Basic.order.OrderService;
import Spring.Basic.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService= appConfig.memberService();
        OrderService orderService =appConfig.orderService();

        Long memberId =1L;
        Member member = new Member("testMember", memberId, Grade.VIP);
        memberService.join(member);
        Order order = orderService.createOrder(memberId, "라면", 1500);
        System.out.println(order);
        System.out.println(order.calculatePrice());
    }
}
