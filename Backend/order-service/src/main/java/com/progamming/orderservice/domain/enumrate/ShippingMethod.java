package com.progamming.orderservice.domain.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShippingMethod {
    PAYPAL("paypal");
    private final String value;
}
