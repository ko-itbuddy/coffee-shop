package org.itbuddy.coffeeshop.order.event;


import org.itbuddy.coffeeshop.order.application.OrderDto;

public record OrderEvent(OrderDto orderDto) {

}
