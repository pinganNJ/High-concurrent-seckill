package cn.nupt.service;

import cn.nupt.domain.OrderInfo;
import cn.nupt.domain.SeckUser;
import cn.nupt.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 21:29
 */
@Service
public class SeckService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    /**
     * 秒杀，原子操作：1.库存减1，2.下订单，3.写入秒杀订单--->是一个事务
     * 返回生成的订单
     *
     * @param user
     * @param goodsvo
     * @return
     */
    @Transactional
    public  OrderInfo miaosha(SeckUser user, GoodsVo goodsvo) {
        //1.减少库存,即更新库存
        goodsService.reduceStock(goodsvo);//考虑减少库存失败的时候，不进行写入订单
        //2.下订单,其中有两个订单: order_info   miaosha_order
        OrderInfo orderinfo = orderService.createOrder(user, goodsvo);

        return orderinfo;
    }

}


