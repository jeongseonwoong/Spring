package Spring.Basic.order;

import Spring.Basic.discount.DiscountPolicy;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    DiscountPolicy discountPolicy;
    MemberService memberService;

    @Autowired
    public OrderServiceImpl(DiscountPolicy discountPolicy, MemberService memberService) {
        this.discountPolicy = discountPolicy;
        this.memberService = memberService;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberService.findMember(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);
        Order order = new Order(memberId, itemName,itemPrice,discountPrice);
        return order;
    }

    public MemberService findMemberService() {
        return memberService;
    }
}
