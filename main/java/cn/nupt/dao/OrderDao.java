package cn.nupt.dao;

import cn.nupt.domain.OrderInfo;
import cn.nupt.domain.SeckOrder;
import org.apache.ibatis.annotations.*;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 22:01
 */
@Mapper
public interface OrderDao {


    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public SeckOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId")Long userId, @Param("goodsId")Long goodsId);
    @Insert("insert into order_info (user_id,goods_id,goods_name,goods_count,goods_price,order_channel,order_status,create_date) values "
            + "(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{orderStatus},#{createDate})")
    @SelectKey(keyColumn="id",keyProperty="id",resultType=long.class,before=false,statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);//使用注解获取返回值，返回上一次插入的id

    @Select("select * from order_info where user_id=#{userId} and goods_id=#{goodsId}")
    public OrderInfo selectorderInfo(@Param("userId")Long userId, @Param("goodsId")Long goodsId);//使用注解获取返回值，返回上一次插入的id

    @Insert("insert into miaosha_order (user_id,goods_id,order_id) values (#{userId},#{goodsId},#{orderId})")
    public void insertMiaoshaOrder(SeckOrder miaoshaorder);

    @Select("select * from order_info where id=#{orderId}")
    public OrderInfo getOrderByOrderId(@Param("orderId")long orderId);


}
