package org.fffd.l23o6.service;

import java.util.List;

import org.fffd.l23o6.pojo.vo.order.OrderVO;

public interface OrderService {
    Long createOrder(String username, Long trainId, Long fromStationId, Long toStationId, String seatType,
            Long seatNumber,int money);

    List<OrderVO> listOrders(String username);

    OrderVO getOrder(Long id);

    void cancelOrder(Long id,int payType);

    String payOrder(Long id,int payType);
}
