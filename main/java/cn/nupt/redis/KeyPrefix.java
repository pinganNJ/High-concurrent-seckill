package cn.nupt.redis;

public interface KeyPrefix {

    public int expireSeconds();//获得有效期
    public String getPrefix();//获得前缀
}
