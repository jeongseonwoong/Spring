package jpabook.jpashop.api.controller;

import jpabook.jpashop.domain.dto.OrderDTO2;
import jpabook.jpashop.domain.dto.OrderQueryDto;
import jpabook.jpashop.repository.query.OrderQueryRepository;
import jpabook.jpashop.domain.dto.OrderSearch;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
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

    @GetMapping("/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o->o.getItem().getName());
        }
        return all;
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
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDTO2> result = orders.stream().map(order
                        -> new OrderDTO2(order.getId(),order.getMember().getName(), order.getOrderDate(),order.getOrderStatus(),order.getDelivery().getAddress(),  order.getOrderItems()))
                .toList();
        return result;
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
}
