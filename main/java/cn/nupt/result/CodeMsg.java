package cn.nupt.result;

/**
 * 返回码和其含义
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-24 19:39
 */

public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常!");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
    public static CodeMsg REQUEST_ILLEAGAL = new CodeMsg(500102, "非法请求!");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(500103, "秒杀失败!");
    public static CodeMsg ACCESS_LIMIT = new CodeMsg(500104, "达到访问限制次数，访问太频繁!");
    //登录模块异常
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "session失效!");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空!");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空!");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误!");
    public static CodeMsg MOBILE_NOTEXIST = new CodeMsg(500214, "手机号号码不存在!");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误!");
    //订单模块异常
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500410, "订单不存在!");
    //秒杀模块异常
    public static CodeMsg MIAOSHA_OVER_ERROR = new CodeMsg(500500, "商品秒杀完毕，库存不足!");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500500, "不能重复秒杀!");


    //无参构造
    public CodeMsg() {
    }

    //有参构造
    public CodeMsg(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    //给一个可变参数的方法，主要是这里的msg，可能有更多的描述
    public CodeMsg fillMsg(Object... args) {
        this.code = code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}
