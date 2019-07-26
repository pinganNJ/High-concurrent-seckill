package cn.nupt.controller;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 20:25
 */

import cn.nupt.domain.SeckUser;
import cn.nupt.service.GoodsService;
import cn.nupt.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_detail/{goodsId}")
    public  String toDetail(Model model, SeckUser user,@PathVariable("goodsId") long goodsId){
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long start = goods.getStartDate().getTime();//秒杀开始时间
        long end = goods.getEndDate().getTime();//秒杀结束时间

        long now = System.currentTimeMillis();//现在的时间

        int status = 0;
        //秒杀倒计时
        int remailSeconds = 0;

        if(now < start){ //秒杀还没开始
            status = 0;
            remailSeconds = (int)(start - now)/1000;//ms -> s
        }else if(now > start){ //秒杀已经结束了
            status = 2;
            remailSeconds = -1;
        }else{
            status = 1;
            remailSeconds = 0;
        }

        model.addAttribute("status",status);
        model.addAttribute("remailSeconds",remailSeconds);

        return "goods_detail";




    }




}
