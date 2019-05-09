package com.feiek.cloud.service.ocr.tengxun;

import com.alibaba.fastjson.JSON;
import com.feiek.cloud.service.OcrService;
import com.feiek.cloud.utils.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
/**
 * 腾讯图文识别工具
 * @author 飞客不去
 * @date  创建时间 2019/4/18 16:30
 * @version 1.0
 */
@Component
public class OcrUtilTengXun implements OcrService{


    @Value("${tx.appKey}")
    private  String appKey;
    @Value("${tx.appid}")
    private  String appid;

    public static void main(String[] args) throws Exception {
        InputStream in=null;
        byte[] data=null;

        in=new FileInputStream("D:\\uploadFile\\0190412175649.png");
        data = new byte[in.available()];
        in.read(data);
        in.close();

//        String s = imageToWords(data);
//        System.out.println(s);
    }




    /**
     * 对参数进行签名，返回字符串形式的签名，对参数值进行url编码
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String generateSignature(Map<String ,String> data, String key) throws Exception {
        Set<String> strings = data.keySet();
        String[] keyStrings = (String[])strings.toArray(new String[strings.size()]);

        Arrays.sort(keyStrings);
        String[] var9=keyStrings;

        StringBuilder sb = new StringBuilder();

        int var8=keyStrings.length;

        for (int var7 = 0; var7 <var8 ; var7++) {
            String k= var9[var7];
            if (k != "sign" && ((String)data.get(k)).trim().length()>0){
                sb.append(k).append("=").append(URLEncoder.encode((String) (data.get(k).trim()),"UTF-8")).append("&");
            }
        }
        sb.append("app_key=").append(key);
        System.out.println(sb.toString());

        return WXPayUtil.MD5(sb.toString()).toUpperCase();
    }



    @Override
    public String identImageToWords(String filePath) {
        return null;
    }

    /**
     * 图文识别，需要传图片的字节码文件
     * @param imageFile
     * @return
     */
    @Override
    public String identImageToWords(byte[] imageFile) {
        //返回的参数
        Map<String, String> map = new HashMap<>();
        String ctime=String.valueOf((int)(System.currentTimeMillis()/1000));
        System.out.println(ctime);

        map.put("app_id",appid);
        map.put("time_stamp",ctime);
        map.put("nonce_str",WXPayUtil.generateNonceStr());

//        InputStream in=null;
//        byte[] data=null;

//        in=new FileInputStream("D:\\uploadFile\\0190412175649.png");
//        data = new byte[in.available()];
//        in.read(data);
//        in.close();

        Base64.Encoder encoder = Base64.getEncoder();
        String s = encoder.encodeToString(imageFile);


        map.put("image",s);
        map.put("sign","");
        String content = null;
        try {
            String sign = generateSignature(map,appKey);
            map.put("sign",sign);
            System.out.println(map);
            //2.发送请求
            HttpClient httpClient = new HttpClient("https://api.ai.qq.com/fcgi-bin/ocr/ocr_generalocr");
            httpClient.setHttps(true);
            httpClient.setParameter(map);
            httpClient.post();

            content = httpClient.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(content);

       return exchangeWords(content);

    }

    /**
     * 将接口返回的内容，格式化成，直接的文字
     * @param content 调用腾讯接口返回的内容
     * @return  腾讯接口格式化后的规范，简洁的文字
     */
    @Override
    public String exchangeWords(String content) {
        HashMap hashMap = JSON.parseObject(content, HashMap.class);

        if((int)hashMap.get("ret")==0){
            Map data1 = (Map)hashMap.get("data");
//
            List itemlist = (List)data1.get("item_list");
            int l=itemlist.size();
            Map<Object, Object> dataMap = new HashMap<>();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <l ; i++) {
                dataMap = (Map)itemlist.get(i);
                sb.append(dataMap.get("itemstring")+"<br>");
            }

            return sb.toString();
        }
        return null;
    }





    /**
     * 对数据进行MD5加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        byte[] var7 = array;
        int var6 = array.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            byte item = var7[var5];
            sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }
}
