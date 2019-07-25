package cn.nupt.redis;

/**
 * 抽象类，继承并实现KeyPrefix,定义成抽象防止被创建，只能被实现
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 19:33
 */

public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    //无参构造
    public BasePrefix() {
    }

    //没有时间限制，永不过期
    public BasePrefix(String prefix) {
        this.prefix = prefix;
    }

    //有前缀和过期时间
    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }


    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
