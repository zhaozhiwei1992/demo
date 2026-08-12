package com.acme.shop.order.repository;

import org.springframework.stereotype.Repository;

/**
 * 数据访问层（演示用，不接真数据库，只演示分层）。
 */
@Repository
public class OrderRepository {

    public Long save(String goodsCode, int count) {
        return System.nanoTime(); // 伪主键，仅演示
    }
}
