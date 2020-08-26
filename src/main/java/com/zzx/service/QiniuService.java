package com.zzx.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.zzx.util.ToutiaoUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName QiniuService
 * @Deacription:
 * @Author zzx
 * @Date 2020/1/13 0:19
 **/
@Service
public class QiniuService {
    String ACCESS_KEY = "lG8GdIw4Po0LooW3yoO3bR_32pm-73fNOfxXvFmy";
    String SECRET_KEY = "uWdEGjyHCsu3DCakc36Y3DG-TpovpsWbeK18RHIp";
    String bucket = "zzxblank";
    String domainOfBucket = "http://q402gyiyd.bkt.clouddn.com";



    //构造一个带指定 Region 对象的配置类
    Configuration cfg = new Configuration(Region.huabei());

    //创建上传对象
    UploadManager uploadManager = new UploadManager(cfg);

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    SmsManager smsManager = new SmsManager(auth);

    public String getUpToken(String key) {
        return auth.uploadToken(bucket, key);
    }


    @Transactional
    public String saveImage(MultipartFile file) {
        String fileName = null;
        try {
            int doPos = file.getOriginalFilename().lastIndexOf(".");
            if (doPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(doPos + 1);
            if (!ToutiaoUtil.isFileAllowed(fileExt)) {
                return null;
            }
            fileName = new Date().getTime() + UUID.randomUUID().toString().substring(0, 4) + "." + fileExt;

            String upToken = getUpToken(fileName);

            Response res = uploadManager.put(file.getBytes(), fileName, upToken);
            //解析上传成功的结果

            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public InputStream getImage(String fileName) {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(domainOfBucket+"/"+fileName).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream inputStream = body.byteStream();
                return inputStream;


            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
        return null;
    }
    @Transactional
    public boolean sendMessage(Map map,String phone){

        try {
            Response resp = smsManager.sendMessage("1217025820439089152", new String[]{phone}, map);
            System.out.println(resp.bodyString());
            if(resp.statusCode == 200){
                return true;
            }else {
                return false;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
            return  false;
    }
}
