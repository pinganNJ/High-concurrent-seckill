package cn.nupt.service;

import cn.nupt.dao.UserDao;
import cn.nupt.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 20:53
 */

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    public User getById(int id) {
        return userDao.getById(id);
    }
}
