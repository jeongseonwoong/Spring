package Spring.Basic.discount;

import Spring.Basic.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price); /*할인 대상 금액*/


}
