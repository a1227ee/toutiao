package com.zzx.dao;

import com.zzx.beans.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName BlogDao
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/19 16:16
 **/
@Mapper
@Repository
public interface BlogDao {
    String TABLE_NAME = "blog";
    String INSERT_FIELDS = " content,firstPicture,flag,create_date,update_date,status,title,user_id,type_id,published,views";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id} "})
    Blog selectById(int id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")values(#{content},#{firstPicture},#{flag},#{createDate},#{updateDate},#{status},#{title},#{userId},#{typeId},#{published},#{views})"})
    int addBlog(Blog blog);

    @Delete({"delete from",TABLE_NAME,"where id=#{id} and user_id=#{userid}"})
    int deleteBlog(@Param("id")  long id,@Param("userid") int userid);

    @Insert({"insert into",TABLE_NAME,"(id,",INSERT_FIELDS,")values(#{id},#{content},#{firstPicture},#{flag},#{createDate},#{updateDate},#{status},#{title},#{userId},#{typeId},#{published},#{views})"})
    void addUpdateBlog(Blog blog);

    @Select("select * from blog where user_id=#{id} order by update_date desc")
    List<Blog> getByUserId(int id);

    //@Select("select * from blog where status=1 and published=1  order by update_date desc  ")
    List<Blog> findBlogByStatus( );

    @Select("SELECT COUNT(*) FROM blog where status=1 and published=1")
    int selectCountBlog();


    @Select("SELECT COUNT(*) FROM blog where user_id=#{id}")
    int findCountByUser(int id);

    @Update({"update ",TABLE_NAME,"set views=views+1 where id=#{id}"})
    void updateViews(int id);
}
