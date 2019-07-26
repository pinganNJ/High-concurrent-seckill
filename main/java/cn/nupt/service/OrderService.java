package cn.nupt.service;

import cn.nupt.domain.OrderInfo;
import cn.nupt.domain.SeckOrder;
import cn.nupt.domain.SeckUser;
import cn.nupt.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 21:29
 */
@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    /**
     * 根据用户userId和goodsId判断是否有者条订单记录，有则返回此纪录
     *
     * @param userId
     * @param goodsId
     * @return
     */
    public SeckOrder getMiaoshaOrderByUserIdAndGoodsId(Long userId, Long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdAndGoodsId(userId, goodsId);
    }

    /**
     * 生成订单,事务
     *
     * @param user
     * @param goodsvo
     * @return
     */

    @Transactional
    public OrderInfo createOrder(SeckUser user, GoodsVo goodsvo) {
/*      private Long id;
        private Long userId;
        private Long goodsId;
        private Long deliveryAddrId;
        private String goodsName;
        private Integer goodsCount;
        private Double goodsPrice;
        private Integer orderChannel;
        private Integer orderStatus;
        private Date createDate;
        private Date payDate;*/

        //1.生成order_info
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setDeliveryAddrId(0L);//long类型 private Long deliveryAddrId;   L
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsvo.getId());
        //秒杀价格
        orderInfo.setGoodsPrice(goodsvo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        //订单状态  ---0-新建未支付  1-已支付  2-已发货  3-已收货
        orderInfo.setOrderStatus(0);
        //用户id
        orderInfo.setUserId(user.getId());

        orderDao.insert(orderInfo);
        //2.生成秒杀订单
        SeckOrder seckOrder = new SeckOrder();
        seckOrder.setGoodsId(goodsvo.getId());
        seckOrder.setUserId(user.getId());
        seckOrder.setOrderId(orderInfo.getId());
        orderDao.insertMiaoshaOrder(seckOrder);
        return orderInfo;
    }

    public OrderInfo getOrderByOrderId(long orderId) {
        return orderDao.getOrderByOrderId(orderId);
    }
}
