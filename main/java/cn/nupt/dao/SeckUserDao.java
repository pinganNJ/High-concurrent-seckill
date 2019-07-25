package cn.nupt.dao;

import cn.nupt.domain.SeckUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 20:24
 */

@Mapper
public interface SeckUserDao {

    @Select("select * form miaosha_user where id = #{id}")
    public SeckUser getById(@Param("id") long id);

    @Update("update miaosha_user set pwd=#{pwd} where id = #{id}")
    public void update(SeckUser seckUser);

}
