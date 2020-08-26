package com.zzx.async.handler;

import com.zzx.async.EventHandler;
import com.zzx.async.EventModel;
import com.zzx.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName LikeHandler
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/5 15:15
 **/
@Component
public class LikeHandler implements EventHandler {
    @Override
    public void doHandle(EventModel model) {
        System.out.println("liked----------------");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
