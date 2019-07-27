package cn.nupt.vo;

import cn.nupt.domain.SeckUser;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-27 10:35
 */

public class GoodsDetailVo {
    /**
     * 为了给页面传值
     */
    // 秒杀状态量初始值
    private int status = 0;
    // 开始时间倒计时
    private int remailSeconds = 0;
    private GoodsVo goodsVo;
    private SeckUser user;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRemailSeconds() {
        return remailSeconds;
    }

    public void setRemailSeconds(int remailSeconds) {
        this.remailSeconds = remailSeconds;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public SeckUser getUser() {
        return user;
    }

    public void setUser(SeckUser user) {
        this.user = user;
    }
}
