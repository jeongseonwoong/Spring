package Spring.Basic.order;

import Spring.Basic.discount.DiscountPolicy;
import Spring.Basic.discount.FixDiscountPolicy;
import Spring.Basic.member.Member;
import Spring.Basic.member.MemberService;
import Spring.Basic.member.MemberServiceImpl;

public class OrderServiceImpl implements OrderService{

    DiscountPolicy discountPolicy = new FixDiscountPolicy();
    MemberService memberService = new MemberServiceImpl();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberService.findMember(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);
        Order order = new Order(memberId, itemName,itemPrice,discountPrice);
        return order;
    }
}
