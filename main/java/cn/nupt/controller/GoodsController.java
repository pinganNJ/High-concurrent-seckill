package cn.nupt.controller;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 20:25
 */

import cn.nupt.domain.SeckUser;
import cn.nupt.redis.GoodsKey;
import cn.nupt.redis.RedisService;
import cn.nupt.service.GoodsService;
import cn.nupt.vo.GoodsVo;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;


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

    /**
     * 做页面缓存的list页面，防止同一时间访问量巨大到达数据库，如果缓存时间过长，数据及时性就不高。
     */
    @RequestMapping(value="/to_list",produces="text/html")
    @ResponseBody
    public String toListCache(Model model, SeckUser user, HttpServletRequest request,
                              HttpServletResponse response) {

        // 1.取缓存
        // public <T> T get(KeyPrefix prefix,String key,Class<T> data)
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        //1.查询商品列表
        List<GoodsVo> goodsList= goodsService.getGoodsVoList();
        model.addAttribute("goodsList", goodsList);
        //2.手动渲染  使用模板引擎	templateName:模板名称 	String templateName="goods_list";
        SpringWebContext context=new SpringWebContext(request,response,request.getServletContext(),
                request.getLocale(),model.asMap(),applicationContext);
        html=thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        //保存至缓存
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);//key---GoodsKey:gl---缓存goodslist这个页面
        }
        return html;
        //return "goods_list";//返回页面login


    }
    /**
     * 做了页面缓存的to_detail商品详情页。
     * 做了页面缓存  URL缓存  ""+goodsId  不同的url进行缓存redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value="/to_detail_html/{goodsId}")  //produces="text/html"
    @ResponseBody
    public String toDetailCachehtml(Model model,SeckUser user,
                                    HttpServletRequest request,HttpServletResponse response,@PathVariable("goodsId")long goodsId) {//id一般用snowflake算法
        // 1.取缓存
        // public <T> T get(KeyPrefix prefix,String key,Class<T> data)
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);//不同商品页面不同的详情
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //缓存中没有，则将业务数据取出，放到缓存中去。
        model.addAttribute("user", user);
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        //既然是秒杀，还要传入秒杀开始时间，结束时间等信息
        long start=goods.getStartDate().getTime();
        long end=goods.getEndDate().getTime();
        long now=System.currentTimeMillis();
        //秒杀状态量
        int status=0;
        //开始时间倒计时
        int remailSeconds=0;
        //查看当前秒杀状态
        if(now<start) {//秒杀还未开始，--->倒计时
            status=0;
            remailSeconds=(int) ((start-now)/1000);  //毫秒转为秒
        }else if(now>end){ //秒杀已经结束
            status=2;
            remailSeconds=-1;  //毫秒转为秒
        }else {//秒杀正在进行
            status=1;
            remailSeconds=0;  //毫秒转为秒
        }
        model.addAttribute("status", status);
        model.addAttribute("remailSeconds", remailSeconds);

        // 2.手动渲染 使用模板引擎 templateName:模板名称 String templateName="goods_detail";
        SpringWebContext context = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        // 将渲染好的html保存至缓存
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;//html是已经渲染好的html文件
        //return "goods_detail";//返回页面login
    }





}
