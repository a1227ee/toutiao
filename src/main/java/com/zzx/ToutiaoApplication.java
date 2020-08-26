package com.zzx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableScheduling
public class ToutiaoApplication {

    public static void main(String[] args) {

        SpringApplication.run(ToutiaoApplication.class, args);

    }

}
