package com.zzx.service;

import com.zzx.beans.News;
import com.zzx.dao.NewsDao;
import com.zzx.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName NewsService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/9 12:14
 **/
@Service

public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getLatesNews(int userId,int offset,int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }
    @Transactional
    public int addNews(News news){
        newsDao.addNews(news);
        return news.getId();
    }

    public News getById(int newsId){
        return newsDao.getById(newsId);
    }
    @Transactional
    public int updateCommentCount(int id,int count){
        return newsDao.updateCommentCount(id,count);
    }
    @Transactional
    public String saveImage(MultipartFile file){
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        if (doPos<0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(doPos + 1);
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }
        String fileName =new Date().getTime()+ UUID.randomUUID().toString().substring(0,4) + "." + fileExt;
        try {
            Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;

    }


}
