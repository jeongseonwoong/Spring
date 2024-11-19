package Spring.Basic.discount;

import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    void vip_discount()
    {
        Member member = new Member("n",1L, Grade.VIP);
        int discount = rateDiscountPolicy.discount(member, 100);
        assertThat(discount).isEqualTo(10);
    }

    @Test
    void noVip_discount()
    {
        Member member = new Member("n",1L, Grade.Basic);
        int discount = rateDiscountPolicy.discount(member, 100);
        assertThat(discount).isEqualTo(0);
    }


}