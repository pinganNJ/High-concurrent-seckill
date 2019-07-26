package cn.nupt.exception;

import cn.nupt.result.CodeMsg;
import cn.nupt.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 21:56
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            CodeMsg cm = ex.getCm();
            return Result.error(cm);
        }
        if (e instanceof BindException) {//是绑定异常的情况
            //强转
            BindException ex = (BindException) e;
            //获取错误信息
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillMsg(msg));

        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }


    }
}