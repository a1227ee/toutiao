package com.zzx.util;

/**
 * @ClassName RedisKeyUtil
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/4 15:41
 **/

public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String BLOGLIKE="BLOG-LIKE";
    private static String BIZ_EVENT = "EVENT";

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }
    public static String getLikeKey(int blogId){
        return BLOGLIKE+SPLIT+blogId;
    }
}
