package cn.nupt.config;

import cn.nupt.domain.SeckUser;
import cn.nupt.service.SeckUserService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-26 21:08
 */

public class UserArgumentResolver {
    @Autowired
    //既然能注入service，那么可以用来容器来管理，将其放在容器中
    SeckUserService seckUserService;


    public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest webRequest,
                                  WebDataBinderFactory arg3) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(seckUserService.COOKIE_NAME_TOKEN);
        System.out.println("@UserArgumentResolver-resolveArgument  paramToken:" + paramToken);
        //获取cookie
        String cookieToken = getCookieValue(request, seckUserService.COOKIE_NAME_TOKEN);
        System.out.println("@UserArgumentResolver-resolveArgument  cookieToken:" + cookieToken);
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        //System.out.println("goods-token:"+token);
        //System.out.println("goods-cookieToken:"+cookieToken);
        SeckUser user = seckUserService.getByToken(token, response);
        System.out.println("@UserArgumentResolver--------user:" + user);
        //去取得已经保存的user，因为在用户登录的时候,user已经保存到threadLocal里面了，因为拦截器首先执行，然后才是取得参数
        //MiaoshaUser user=UserContext.getUser();
        return user;
    }

    public String getCookieValue(HttpServletRequest request, String cookie1NameToken) {//COOKIE1_NAME_TOKEN-->"token"
        //遍历request里面所有的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookie1NameToken)) {
                    System.out.println("getCookieValue:" + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        System.out.println("No getCookieValue!");
        return null;
    }

    public boolean supportsParameter(MethodParameter parameter) {
        //返回参数的类型
        Class<?> clazz = parameter.getParameterType();
        return clazz == SeckUser.class;
    }

}
