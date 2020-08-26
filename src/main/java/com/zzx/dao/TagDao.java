package com.zzx.dao;

import com.zzx.beans.Blog;
import com.zzx.beans.Tag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.util.List;


@Mapper
@Repository
public interface TagDao {
    String TABLE_NAME = "blog_tags";

    @Insert({"insert into",TABLE_NAME, "(blog_id,tag_id)values (#{id},#{tagIds})"})
    void addTag(@Param("id") Long id, @Param("tagIds")Integer tagIds);

    @Select("SELECT tag.id ,tag.name FROM blog_tags,tag,blog WHERE blog_id=#{id} AND blog.id=blog_tags.blog_id AND blog_tags.tag_id=tag.id")
    List<Tag> selectById(@Param("id") int id);

    @Select("SELECT tag.id,tag.name FROM tag ")
    List<Tag> selectAll();

    @Delete({"delete from",TABLE_NAME,"where blog_id=#{id} "})
    int deleteById(@Param("id") Long id);

    @Select("SELECT  name,COUNT(*) as id FROM tag,blog_tags,blog WHERE status=1 and published=1 and  blog.id =blog_tags.blog_id and  blog_tags.tag_id=tag.id GROUP BY name ORDER BY id DESC  ")
    List<Tag>  findTagsSum();



}
