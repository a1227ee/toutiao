package com.zzx;


import com.zzx.beans.User;
import com.zzx.util.JedisAdapter;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName JedisTests
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/5 14:37
 **/
@SpringBootTest
public class JedisTests {

    @Autowired
    JedisAdapter jedisAdapter;
    @Test

    public void testObject(){
        User user = new User();
        user.setHeadUrl("zxxczcasdada");
        user.setName("zzx");
        user.setPassword("zxxczxczxc");
        user.setSalt("112313");
        jedisAdapter.setObject("zzxuser",user);


        User zzxuser = jedisAdapter.getObject("zzxuser", User.class);

        System.out.println(zzxuser);
    }

}
