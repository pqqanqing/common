package com.wjs.common.base.money;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

/**
 * Created by panqingqing on 16/10/18.
 */
public class Money {

    private BigDecimal money;

    public Money() {
        this.money = ZERO;
    }

    public Money(String money) {
        this.money = genarateMoney(money);
    }

    private BigDecimal genarateMoney(String money) {
        if (isNull(money)) return ZERO;
        try {
            return new BigDecimal(money);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public BigDecimal getMoney() {
        return money;
    }
}