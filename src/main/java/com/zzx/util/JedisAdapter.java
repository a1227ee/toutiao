package com.zzx.util;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @ClassName JedisAdapter
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/3 22:40
 **/
@Component
public class JedisAdapter implements InitializingBean {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    private JedisPool pool=null;
    private Jedis jedis = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("112.126.66.145", 6379 );
    }

    public Jedis getJedis(){
        jedis = pool.getResource();
        jedis.auth("zzx123321");
        return jedis;
    }

    public void set(String key, String value) {

        try {
            jedis = pool.getResource();
            jedis.auth("zzx123321");
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String get(String key) {

        try {
            jedis =getJedis();
            return getJedis().get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public long sadd(String key,String value){
        try{
            jedis =getJedis();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("Exception",e);
            return 0;
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
    }
    public void setex(String key, String value) {
        // 验证码, 防机器注册，记录上次注册时间，有效期3天

        try {
            jedis =getJedis();
            jedis.setex(key, 10, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long srem(String key,String value){

        try{
            jedis =getJedis();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("Exception",e);
            return 0;
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
    }
    public Boolean sismember(String key, String value){

        try{
            jedis =getJedis();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("Exception",e);
            return false;
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
    }
    public long scard(String key ){

        try{
            jedis =getJedis();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("Exception",e);
            return 0;
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }
    }

    public long lpush(String key, String value) {

        try {
            jedis =getJedis();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public List<String> brpop(int timeout, String key) {

        try {
            jedis =getJedis();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setObject(String key,Object obj){
         set(key, JSON.toJSONString(obj));
    }

    public <T> T getObject (String key,Class<T> clazz){
        String value = get(key);
        if (value!=null){
            return JSON.parseObject(value,clazz);
        }
        return null;
    }


}
