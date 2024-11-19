package Spring.Basic.order;

import Spring.Basic.annotation.MainDiscountPolicy;
import Spring.Basic.discount.DiscountPolicy;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final DiscountPolicy discountPolicy;
    private final MemberService memberService;

    @Autowired
    public OrderServiceImpl(@MainDiscountPolicy DiscountPolicy discountPolicy, MemberService memberService) {
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
