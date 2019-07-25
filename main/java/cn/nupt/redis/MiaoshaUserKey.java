package cn.nupt.redis;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 19:49
 */

public class MiaoshaUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600*24*2; //两天

    public MiaoshaUserKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }

    //一个有效值两天的token
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
    //对象缓存一般没有有效期，永久有效
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");


}
