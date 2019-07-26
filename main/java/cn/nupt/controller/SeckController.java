package cn.nupt.controller;

import cn.nupt.domain.OrderInfo;
import cn.nupt.domain.SeckOrder;
import cn.nupt.domain.SeckUser;
import cn.nupt.redis.RedisService;
import cn.nupt.result.CodeMsg;
import cn.nupt.service.GoodsService;
import cn.nupt.service.OrderService;
import cn.nupt.service.SeckService;
import cn.nupt.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 21:24
 */

public class SeckController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    SeckService seckService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/do_miaosha")
    public String toList(Model model, SeckUser seckUser, @RequestParam("goodsId") Long goodsId) {
        model.addAttribute("user", seckUser);
        //如果用户为空，则返回至登录页面
        if (seckUser == null) {
            return "login";
        }
        GoodsVo goodsvo = goodsService.getGoodsVoByGoodsId(goodsId);
        //判断商品库存，库存大于0，才进行操作，多线程下会出错
        int stockcount = goodsvo.getStockCount();
        if (stockcount <= 0) {//失败			库存至临界值1的时候，此时刚好来了加入10个线程，那么库存就会-10
            model.addAttribute("errorMessage", CodeMsg.MIAOSHA_OVER_ERROR);
            return "miaosha_fail";
        }

        //判断这个秒杀订单形成没有，判断是否已经秒杀到了，避免一个账户秒杀多个商品
        SeckOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(seckUser.getId(), goodsId);
        if (order != null) {//重复下单
            model.addAttribute("errorMessage", CodeMsg.REPEATE_MIAOSHA);
            return "miaosha_fail";
        }
        //可以秒杀，原子操作：1.库存减1，2.下订单，3.写入秒杀订单--->是一个事务
        OrderInfo orderinfo = seckService.miaosha(seckUser, goodsvo);
        //如果秒杀成功，直接跳转到订单详情页上去。
        model.addAttribute("orderinfo", orderinfo);
        model.addAttribute("goods", goodsvo);
        return "order_detail";//返回页面login
    }


}
