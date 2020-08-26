package com.zzx.dao;

import com.zzx.beans.Type;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TypeDao
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/19 16:17
 **/
@Mapper
@Repository
public interface TypeDao {

    @Select("select * from type")
    List<Type> selectAll();

    @Select("select  name,count(*) as id FROM type,blog WHERE blog.type_id=type.id and status=1 and published=1  GROUP BY name ORDER BY id desc")
    List<Type> findTypesSum();

    @Select("select  name FROM type  WHERE  id=#{id}    ")
    String getTypeById(int typeId);
}
