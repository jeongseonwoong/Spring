package jpabook.jpashop.api.controller;

import jpabook.jpashop.domain.dto.OrderSimpleQueryDto;
import jpabook.jpashop.domain.dto.SimpleOrderDto;
import jpabook.jpashop.domain.dto.OrderSearch;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne 관계(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    @GetMapping("/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getStatus();
        }
        return all;
    }

    @GetMapping("/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        /**
         * Order 2ro
         * 1 + N(회원 지연로딩) +N(배송 지연로딩)  = N + 1 문제
         */
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> simpleOrder = orders.stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return simpleOrder;
    }

    @GetMapping("/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        /**
         * fetchJoin으로 인해 N+1문제 해결
         */
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> simpleOrder = orders.stream().map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return simpleOrder;
    }

    @GetMapping("/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4(){
        /**
         * new DTO()로 바로 반환
         */
        return orderRepository.findOrderDTOs();
    }


}
