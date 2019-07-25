package cn.nupt.controller;

import cn.nupt.domain.SeckUser;
import cn.nupt.redis.RedisService;
import cn.nupt.result.CodeMsg;
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
    public CodeMsg doLogin(LoginVo loginVo) {

        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();


        //看看能不能通过手机号在数据库里面查到这个人
        SeckUser user = seckUserService.getById(Long.parseLong(mobile));

        if (user == null) {
            return CodeMsg.MOBILE_NOTEXIST;
        }

        //查到这个手机号了，再看一下密码有没有错
        String dbPass = user.getPwd();
        String dbSalt = user.getSalt();

        String mergrMd5 = MD5Util.mergrMd5(password, dbSalt);

        //用户传过来的密码和我在数据库的salt加密后不等于我在数据库存的密码
        if (!mergrMd5.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR
        }

        return CodeMsg.SUCCESS;
    }


}
