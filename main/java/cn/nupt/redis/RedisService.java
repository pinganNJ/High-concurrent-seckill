package cn.nupt.redis;

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
    @Autowired
    //RedisService引用JedisPool--JedisPool在RedisService，只有创建RedisService的实例才可以获取JedisPool的bean
            //所以需要单独拿出JedisPool的bean
            JedisPool jedisPool;

    /**
     * 获取单个对象
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
     * 设置单个、多个对象
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


}
