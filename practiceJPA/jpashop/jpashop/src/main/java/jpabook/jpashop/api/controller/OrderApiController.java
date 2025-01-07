package jpabook.jpashop.api.controller;

import jpabook.jpashop.domain.dto.OrderDTO2;
import jpabook.jpashop.domain.dto.OrderFlatDto;
import jpabook.jpashop.domain.dto.OrderQueryDto;
import jpabook.jpashop.repository.query.OrderQueryRepository;
import jpabook.jpashop.domain.dto.OrderSearch;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderQueryService orderQueryService;

    @GetMapping("/v1/orders")
    public List<Order> ordersV1(){
        return orderRepository.findAllByString(new OrderSearch());
    }

    @GetMapping("/v2/orders")
    public List<OrderDTO2> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDTO2> result = orders.stream().map(order
                        -> new OrderDTO2(order.getId(),order.getMember().getName(), order.getOrderDate(),order.getOrderStatus(),order.getDelivery().getAddress(),  order.getOrderItems()))
                .toList();
        return result;
    }

    @GetMapping("/v3/orders")
    public List<OrderDTO2> ordersV3(){
        return orderQueryService.ordersV3();
    }

    @GetMapping("/v3.1/orders")
    public List<OrderDTO2> ordersV3_page(
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        List<OrderDTO2> result = orders.stream().map(order
                        -> new OrderDTO2(order.getId(),order.getMember().getName(), order.getOrderDate(),order.getOrderStatus(),order.getDelivery().getAddress(),  order.getOrderItems()))
                .toList();
        return result;
    }

    @GetMapping("/v4/orders")
    public List<OrderQueryDto>ordersV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/v5/orders")
    public List<OrderQueryDto>ordersV5(){
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/v6/orders")
    public List<OrderFlatDto>ordersV6(){
        return orderQueryRepository.findAllBy_dto_flat();
    }
}
