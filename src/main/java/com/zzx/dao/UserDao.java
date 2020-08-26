package com.zzx.dao;

import com.zzx.beans.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDao
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/8 19:13
 **/

@Mapper
@Repository
public interface UserDao {

    String TABLE_NAME="user";
    String INSERT_FIELDS="name,password,salt,head_url,nick";
    String SELECT_FIELDS="id,name,password,salt,head_url,nick";

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")values(#{name},#{password},#{salt},#{headUrl},#{nick})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
    User selectById(int id);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name=#{name}"})
    User selectByName(String name);

}
