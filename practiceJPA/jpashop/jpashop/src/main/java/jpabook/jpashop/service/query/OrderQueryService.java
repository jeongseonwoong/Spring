package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.dto.OrderDTO2;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderDTO2> ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDTO2> result = orders.stream().map(order
                        -> new OrderDTO2(order.getId(),order.getMember().getName(), order.getOrderDate(),order.getOrderStatus(),order.getDelivery().getAddress(),  order.getOrderItems()))
                .toList();
        return result;
    }
}
