package cn.nupt.redis;

import cn.nupt.domain.SeckUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-24 22:18
 */

@Service
public class RedisService {

/*  public T get(KeyPrefix prefix,String key,Class data) 根据key取得缓存中值（根据传入的前缀）
    public boolean delete(KeyPrefix prefix,String key) 删除key
    public boolean set(KeyPrefix prefix,String key,T value) 根据key设置缓存中值
    public Long decr(KeyPrefix prefix,String key) 自减
    public Long incr(KeyPrefix prefix,String key)	自增
    public boolean exitsKey(KeyPrefix prefix,String key) 是否存在key
    public static T stringToBean(String s,Class clazz)
    public static String beanToString(T value)
    */


    @Autowired
    //RedisService引用JedisPool--JedisPool在RedisService，只有创建RedisService的实例才可以获取JedisPool的bean
            //所以需要单独拿出JedisPool的bean
            JedisPool jedisPool;

    /**
     * 根据key取得缓存中值（根据传入的前缀）
     *
     * @param prefix:区别每个表相同的id
     * @param key
     * @param data:??
     * @return
     */

    public <T> T get(KeyPrefix prefix, String key, Class<T> data) {
        Jedis jedis = null;

        try {
            //从jedisPool里面获得jedis
            jedis = jedisPool.getResource();
            //通过key的前缀获得真正的key
            String realKey = prefix.getPrefix() + key;

            String value = jedis.get(realKey);
            //将String转换为Bean入后传出
            T t = stringToBean(value, data);
            return t;
        } finally {
            //关闭redis链接
            returnToPool(jedis);
        }
    }

    /**
     * 移除对象,删除
     *
     * @param prefix
     * @param key
     * @return
     */


    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            String realKey = prefix.getPrefix() + key;

            //返回删除结果，成功就>0
            Long del = jedis.del(realKey);
            return del > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 根据key设置缓存中值
     *
     * @param prefix
     * @param key
     * @param value
     * @return
     */

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            //将T类型转换为String类型，json类型？？
            String s = beanToString(value);
            if (s == null || s.length() <= 0) {
                return false;
            }

            int seconds = prefix.expireSeconds();
            //代表key不过期，直接设置
            if (seconds <= 0) {
                jedis.set(realKey, s);
            } else {
                //自己设置有效期
                jedis.setex(realKey, seconds, s);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }


/*    public Long decr(KeyPrefix prefix,String key) 自减
    public Long incr(KeyPrefix prefix,String key)	自增
    public boolean exitsKey(KeyPrefix prefix,String key) 是否存在key
    public static T stringToBean(String s,Class clazz)
    public static String beanToString(T value)*/

    /**
     * 减少值
     *
     * @param prefix
     * @param key
     * @return
     */

    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);

        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加
     *
     * @param prefix
     * @param key
     * @return
     */

    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 检查key是否存在
     *
     * @param prefix
     * @param key
     * @return
     */

    public boolean exitsKey(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 将字符串转换为Bean对象
     * clazz:这个是传入的指示想要转的bean的类型，比如 long、int、
     * parseInt()返回的是基本类型int 而valueOf()返回的是包装类Integer
     * Integer是可以使用对象方法的  而int类型就不能和Object类型进行互相转换 。
     * int a=Integer.parseInt(s);
     * Integer b=Integer.valueOf(s);
     */

    public static <T> T stringToBean(String s, Class<T> clazz) {

        if (s == null || s.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return ((T) Integer.valueOf(s));
        } else if (clazz == String.class) {
            return (T) s;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(s);
        } else {
            //先将json转化为json
            JSONObject object = JSON.parseObject(s);
            //将json转化为bean
            return JSON.toJavaObject(object, clazz);
        }
    }

    /**
     * 将Bean对象转换为字符串类型
     *
     * @param <T>
     */

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }

        if (value == int.class || value == Integer.class || value == String.class || value == long.class
                || value == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    //关闭jedis
    public void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }




}
