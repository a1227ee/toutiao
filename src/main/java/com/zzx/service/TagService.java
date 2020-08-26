package com.zzx.service;

import com.zzx.beans.Tag;
import com.zzx.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TagService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/19 18:07
 **/
@Service
public class TagService {
    @Autowired
    TagDao tagDao;



    public List<Tag> getTag() {

        return   tagDao.selectAll();
    }

    public  List<Tag> getTagSum() {
        return  tagDao.findTagsSum();
    }
}
