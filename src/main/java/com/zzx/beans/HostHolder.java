package com.zzx.beans;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zzx
 * @date: 2020/1/11  0:23
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }

}
