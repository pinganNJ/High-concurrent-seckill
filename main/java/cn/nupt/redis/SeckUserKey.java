package cn.nupt.redis;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 19:49
 */

public class SeckUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600*24*2; //两天

    public SeckUserKey(int expireSeconds, String prefix){
        super(expireSeconds,prefix);
    }

    //一个有效值两天的token
    public static SeckUserKey token = new SeckUserKey(TOKEN_EXPIRE,"tk");
    //对象缓存一般没有有效期，永久有效
    public static SeckUserKey getById = new SeckUserKey(0,"id");


}
