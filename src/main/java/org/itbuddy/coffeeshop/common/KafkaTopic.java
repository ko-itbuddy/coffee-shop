package org.itbuddy.coffeeshop.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopic {
    ORDER("order.v1");
    private final String topic;
}
