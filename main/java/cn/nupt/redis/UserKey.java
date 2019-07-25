package cn.nupt.redis;

/**
 * 用户key
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 19:47
 */

public class UserKey extends BasePrefix{
    //用的是父类的只有前缀，说明用户的前缀永不过期
    public UserKey(String prefix){
        super(prefix);
    }

    public static UserKey getById=new UserKey("id");
    public static UserKey getByName=new UserKey("name");


}
