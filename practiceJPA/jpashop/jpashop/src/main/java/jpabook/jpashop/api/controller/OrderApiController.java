package jpabook.jpashop.api.controller;

import jpabook.jpashop.api.domain.dto.OrderDTO;
import jpabook.jpashop.api.domain.dto.OrderDTO2;
import jpabook.jpashop.domain.dto.OrderSearch;
import jpabook.jpashop.domain.entity.Delivery;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderRepository orderRepository;

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
}
