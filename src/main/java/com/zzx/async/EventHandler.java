package com.zzx.async;

import java.util.List;

/**
 * @ClassName EventHandler
 * @Deacription:
 * @Author zzx
 * @Date 2020/3/5 15:12
 **/

public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();

}
