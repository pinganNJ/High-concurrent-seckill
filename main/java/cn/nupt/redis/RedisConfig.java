package cn.nupt.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis配置类
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-24 21:45
 */

@Component
//指定配置文件里面前缀为"redis"的配置项，与配置项里面的属性对应起来
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

/*
    #redis  配置服务器等信息
    redis.host=127.0.0.1
    redis.port=6379
    redis.timeout=10
    redis.password=123456
    redis.poolMaxTotal=1000
    redis.poolMaxldle=500
    redis.poolMaxWait=500
*/

    private String host;
    private int port;
    private int timeout;
    private String password;
    private int poolMaxTotal;
    private int poolMaxldle;
    private int poolMaxWait;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxldle() {
        return poolMaxldle;
    }

    public void setPoolMaxldle(int poolMaxldle) {
        this.poolMaxldle = poolMaxldle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }
}
