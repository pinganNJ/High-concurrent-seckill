package cn.nupt.service;

import cn.nupt.dao.SeckUserDao;
import cn.nupt.domain.SeckUser;
import cn.nupt.exception.GlobalException;
import cn.nupt.redis.RedisService;
import cn.nupt.redis.SeckUserKey;
import cn.nupt.result.CodeMsg;
import cn.nupt.utils.MD5Util;
import cn.nupt.utils.UUIDUtil;
import cn.nupt.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 20:30
 */


@Service
public class SeckUserService {
    public static final String COOKIE_NAME_TOKEN = "token";
    @Autowired
    SeckUserDao seckUserDao;
    @Autowired
    RedisService redisService;

    public SeckUser getById(long id) {

        //先从redis里面看看，能不能取到我想要的东西，这里是user
        SeckUser user = redisService.get(SeckUserKey.getById, "" + id, SeckUser.class);
        if (user != null) {
            return user;
        }

        user = seckUserDao.getById(id);

        if (user != null) {
            //如果从数据库里面拿到了这个user，在结束之前再存到redis里面
            redisService.set(SeckUserKey.getById, "" + id, SeckUser.class);
        }
        return user;

    }


    public CodeMsg login(HttpServletResponse response, LoginVo loginVo) {

        if (loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();


        //看看能不能通过手机号在数据库里面查到这个人
        SeckUser user = getById(Long.parseLong(mobile));

        if (user == null) {
            return CodeMsg.MOBILE_NOTEXIST;
        }

        //查到这个手机号了，再看一下密码有没有错
        String dbPass = user.getPwd();
        String dbSalt = user.getSalt();

        String mergrMd5 = MD5Util.mergrMd5(password, dbSalt);

        //用户传过来的密码和我在数据库的salt加密后不等于我在数据库存的密码
        if (!mergrMd5.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }

        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(user, token, response);

        return CodeMsg.SUCCESS;

    }

    private void addCookie(SeckUser user, String token, HttpServletResponse response) {

        // 将token写到cookie当中，然后传递给客户端
        // 此token对应的是哪一个用户,将我们的私人信息存放到一个第三方的缓存中
        // prefix:MiaoshaUserKey.token key:token value:用户的信息 -->以后拿到了token就知道对应的用户信息。
        // MiaoshaUserKey.token-->

        //存到一个第三方的redis中，key：token，value:user
        redisService.set(SeckUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        // 设置cookie的有效期，与session有效期一致
        cookie.setMaxAge(SeckUserKey.token.expireSeconds());
        // 设置网站的根目录
        cookie.setPath("/");

        response.addCookie(cookie);
        
    }
    
    //根据token从redis里面找到user
    //客户端在随后的访问中，都在cookie中上传这个token，然后服务端拿到这个token之后，就根据这个token来去缓存中取得对应的（用户信息）session信息
    public SeckUser getByToken(String token,HttpServletResponse response){

        if(StringUtils.isEmpty(token)){
            return null;
        }

        SeckUser seckUser = redisService.get(SeckUserKey.token, token, SeckUser.class);

        // 再次请求时候，延长有效期 重新设置缓存里面的值，使用之前cookie里面的token
        if(seckUser != null){
            addCookie(seckUser,token,response);
        }

        return seckUser;

    }


    /**
     * 注意数据修改时候，保持缓存与数据库的一致性
     * 需要传入token
     * @param id
     * @return
     */
    public boolean updatePassword(String token,long id,String passNew) {
        //1.取user对象，查看是否存在
        SeckUser user=getById(id);
        if(user==null) {
            throw new GlobalException(CodeMsg.MOBILE_NOTEXIST);
        }
        //2.更新密码
        SeckUser toupdateuser=new SeckUser();
        toupdateuser.setId(id);
        toupdateuser.setPwd(MD5Util.mergrMd5(passNew, user.getSalt()));
        seckUserDao.update(toupdateuser);
        //3.更新数据库与缓存，一定保证数据一致性，修改token关联的对象以及id关联的对象
        redisService.delete(SeckUserKey.getById, ""+id);
        //不能直接删除token，删除之后就不能登录了
        user.setPwd(toupdateuser.getPwd());
        redisService.set(SeckUserKey.token, token,user);
        return true;
    }

}
