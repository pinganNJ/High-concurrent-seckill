package cn.nupt.controller;

import cn.nupt.domain.SeckUser;
import cn.nupt.redis.RedisService;
import cn.nupt.result.CodeMsg;
import cn.nupt.result.Result;
import cn.nupt.service.SeckUserService;
import cn.nupt.service.UserService;
import cn.nupt.utils.MD5Util;
import cn.nupt.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 20:45
 */


@RequestMapping("/login")
@Controller
public class LoginController {
    @Autowired
    RedisService redisService;

    @Autowired
    SeckUserService seckUserService;

    @Autowired
    UserService userServicel;

    //slf4j
    private static Logger log = (Logger) LoggerFactory.getLogger(Logger.class);

    @RequestMapping("/do_login")
    @ResponseBody
    //用了@valid之后，就直接在bean里面用注解来校验，就不要下面的手动校验了
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {



        CodeMsg cm = seckUserService.login(response, loginVo);
        if(cm.getCode()==0){
            return Result.success(true);
        }else {
            return Result.error(cm);
        }
    }


}
