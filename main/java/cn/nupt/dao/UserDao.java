package cn.nupt.dao;

import cn.nupt.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 20:55
 */

public interface UserDao {
    @Select("select * from t_user where id=#{id}")//@Param("id")进行引用
    public User getById(@Param("id") int id);
    @Insert("insert into t_user(id,name) values(#{id},#{name})")  //id为自增的，所以可以不用设置id
    public void insert(User user);
}
