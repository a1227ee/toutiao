package com.zzx.service;

import com.zzx.util.JedisAdapter;
import com.zzx.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LikeService
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/4 15:38
 **/
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public int getLickStatus(String userId,int blogId ){
        String likeKey = RedisKeyUtil.getLikeKey( blogId );
        if (jedisAdapter.sismember(likeKey, userId )){
            return 1;
        }
        return 0;
    }

    public long like(String userId,int blogId){
        String likeKey = RedisKeyUtil.getLikeKey(blogId);
        jedisAdapter.sadd(likeKey,  userId );
        return jedisAdapter.scard(likeKey);
    }

    public long getNumber( int blogId){
        String likeKey = RedisKeyUtil.getLikeKey(blogId);
        return jedisAdapter.scard(likeKey);
    }

}
