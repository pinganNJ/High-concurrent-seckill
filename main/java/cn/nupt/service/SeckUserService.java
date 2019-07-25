package cn.nupt.service;

import cn.nupt.dao.SeckUserDao;
import cn.nupt.domain.SeckUser;
import cn.nupt.redis.RedisService;
import cn.nupt.redis.SeckUserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
