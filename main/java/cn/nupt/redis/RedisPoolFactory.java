package cn.nupt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 生成Jedis连接池（配置），方便在RedisService中调用。
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-24 22:10
 */

@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxIdle(redisConfig.getPoolMaxldle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        //因为我们使用的是s（秒）来配置的，而源码使用的是ms（毫秒），所以转换一下
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        //注入进JedisPool
        JedisPool jedisPool = new JedisPool(poolConfig, redisConfig.getHost()
                , redisConfig.getPort()
                , redisConfig.getTimeout() * 1000
                , redisConfig.getPassword()
                , 0);

        return jedisPool;
    }


}
