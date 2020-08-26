package com.zzx;

import com.zzx.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToutiaoApplicationTests {
    @Autowired
    UserDao userDao;
    @Test
    void contextLoads() {

    }

}
