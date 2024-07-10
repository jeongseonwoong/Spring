package Spring.Basic.discount;

import Spring.Basic.annotation.MainDiscountPolicy;
import Spring.Basic.member.Grade;
import Spring.Basic.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent =10;

    @Override
    public int discount(Member member, int price) {

        if(member.getGrade()== Grade.VIP)
        {
            return (price*discountPercent)/100;
        }
        return 0;
    }




}
