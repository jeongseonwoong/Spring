package jpabook.jpashop.api.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.api.domain.dto.*;
import jpabook.jpashop.domain.entity.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        /**
         * 이렇게 Entity를 이용해서 값을 받게되면
         * 1.화면에서 들어오는 값의 검증 로직이 Entity에 포함되게 된다. -> entity가 지저분해짐
         * 2.entity 속성이 바뀌면 api 스팩이 같이 바뀜 -> 유지보수가 힘들다.
         */
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        /**
         * 이렇게 dto를 이용해서 값을 받게되면
         * 1. 화면에서 들어오는 값의 검증 로직을 dto에서 따로 포함하게 된다 -> entity는 깔끔하게 유지 가능
         * 2. entity 속성이 바뀌어도 api 스팩이 같이 바뀌지 않는다. -> 유지보수가 쉽다.
         */
        Member member = new Member();
        member.updateValueForRegister(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponse(@PathVariable("id") Long id,
                                                     @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/v1/members")
    public List<Member> membersV1(){
        /**
         * JPA의 양방향 연관관계에서 발생하는 순환 참조(Circular Reference) 문제 발생.
         * Member 엔티티가 Order 리스트를 포함하고 있고,
         * Order 엔티티가 다시 Member를 참조하면서
         * JSON 직렬화 과정에서 계속 순환 참조가 발생하여
         * 무한 루프가 생성
         * -> @JSONIgnore로 해결(Entity에 화면 로직이 들어감, 각 api 요청에 따른 유연한 대처 불가능)/
         * -> DTO로 해결(추천)
         */
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public MemberResult membersV2() {
        List<Member> findMembers = memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDTO> collect = findMembers.stream()
                .map(m -> new MemberDTO(m.getName()))
                .collect(Collectors.toList());
        return new MemberResult(collect.size(),collect);
    }

//    @GetMapping("/v2/members")
//    public MemberResult membersV2(){
//        List<Member> members = memberService.findMembers();
//        List<MemberDTO> collect = members.stream().map(member ->
//                new MemberDTO(member.getName(),member.getOrders().stream().map(order ->
//                    new OrderDTO(order.getDelivery(),order.getOrderDate(),order.getOrderStatus())
//                ).collect(Collectors.toList())))
//                .collect(Collectors.toList());
//
//        return new MemberResult(collect.size(),collect);
//    }

}
