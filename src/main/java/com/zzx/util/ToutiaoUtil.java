package com.zzx.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName ToutiaoUtil
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/12 17:04
 **/
@Component
public class ToutiaoUtil {
    public static String[] IMAGE_FILE_EXTD = new String[] {"png", "bmp", "jpg", "jpeg"};
    public static String TOUTIAO_DOMAIN = "http://127.0.0.1:8080/";
    public static String IMAGE_DIR = "D:/upload/";
    public static boolean isFileAllowed(String fileName){
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;

    }
}
