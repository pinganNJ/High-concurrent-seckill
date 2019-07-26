package cn.nupt.service;

import cn.nupt.dao.GoodsDao;
import cn.nupt.domain.SeckGoods;
import cn.nupt.redis.RedisService;
import cn.nupt.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 20:08
 */

public class GoodsService {

    public static final String COOKIE1_NAME_TOKEN="token";
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    RedisService redisService;

    //获取商品信息列表
    public List<GoodsVo> getGoodsVoList() {
        return goodsDao.getGoodsVoList();
    }
    //获取商品根据商品Id
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    //减少商品的库存
    public void reduceStock(GoodsVo goodsvo) {
        SeckGoods goods = new SeckGoods();
        goods.setGoodsId(goodsvo.getId());
        //goods.setStockCount(goodsvo.getGoodsStock()-1);  sql里面去运算
        //goodsDao.reduceStock(goods.getGoodsId());
        goodsDao.reduceStock(goods);
    }

}
