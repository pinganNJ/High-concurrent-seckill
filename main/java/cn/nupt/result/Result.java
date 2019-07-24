package cn.nupt.result;

/**
 * 结果封装
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-24 17:00
 */



public class Result<T> {

    private int code;
    private String msg;
    private T data;

    //两个构造函数，一个是成功的时候，返回码和数据
    public Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    //一个是失败的时候，返回码和错误信息
    public Result(CodeMsg cm){
        if(cm == null){
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    //成功的方法，调用上面的构造函数
    public static  <T> Result<T> success(T data){
        return new Result<>(data);
    }

    //失败的方法
    public static  <T> Result<T> error(CodeMsg cm){
        return new Result<>(cm);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
