package cn.nupt.controller;

import cn.nupt.result.CodeMsg;
import cn.nupt.result.Result;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-24 21:05
 */
@EnableAutoConfiguration
@Controller
public class DemoController {

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return new Result<>("hello");
    }

    @RequestMapping("/error")
    public Result error(){
        return new Result(CodeMsg.ACCESS_LIMIT);
    }

}
