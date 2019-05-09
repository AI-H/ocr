package com.feiek.cloud.service.ocr.huawei;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.feiek.cloud.service.OcrService;
import com.feiek.cloud.service.ocr.huawei.utils.HWOcrClientAKSK;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OCRASKUtilHuawei implements OcrService{

//    public static void main(String[] args) {
//        /*
//         * AK/SK demo code
//         * */
//        String Httpendpoint="https://ocr.cn-north-1.myhuaweicloud.com"; //httpendpoint for the service
//        String Region="cn-north-1";   //region name for the service
//        String AK="UDLWPWLXLV5KCYVG5B2P";  //AK                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           authentication
//        String SK="Auc9ynBMJzgAsq7wCm3YBMVoipucxZZHblD672Td"; //SK from authentication
//        try {
//            HWOcrClientAKSK ocrClient=new HWOcrClientAKSK(Httpendpoint,Region,AK,SK);
//            HttpResponse response=ocrClient.requestOcrServiceBase64("/v1.0/ocr/general-text", "D:\\uploadFile\\20190416154957.png");
//            System.out.println(response);
//            String content = IOUtils.toString(response.getEntity().getContent());
//            System.out.println(content);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Value("${huawei.Httpendpoint}")
    private String  Httpendpoint;

    @Value("${huawei.Region}")
    private String  Region;

    @Value("${huawei.AK}")
    private String  AK;

    @Value("${huawei.SK}")
    private String  SK;





    /**
     * 华为云图文识别
     * @param filePath
     * @return
     */
    @Override
    public String identImageToWords(String filePath) {
        String words=null;
        try {

            System.out.println(Httpendpoint+Region+AK+SK);
            HWOcrClientAKSK ocrClient=new HWOcrClientAKSK(Httpendpoint,Region,AK,SK);
            HttpResponse response=ocrClient.requestOcrServiceBase64("/v1.0/ocr/general-text", filePath);
            System.out.println(response);
            String content = IOUtils.toString(response.getEntity().getContent());
            System.out.println(content);
            words = exchangeWords(content);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }



    @Override
    public String identImageToWords(byte[] imageFile) {
        return null;
    }




    /**
     * 获得接口返回的文字内容
     * @param content
     * @return
     */
    @Override
    public String exchangeWords(String content) {
        try {
            HashMap hashMap = JSON.parseObject(content, HashMap.class);
            String result = hashMap.get("result").toString();
            HashMap resultMap = JSON.parseObject(result, HashMap.class);
            Object wordslist = resultMap.get("words_block_list");

            JSONArray objects = JSON.parseArray(wordslist.toString());
            int size = objects.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                Object o = objects.get(i);
                HashMap wordsMap = JSON.parseObject(o.toString(), HashMap.class);
                String s = wordsMap.get("words").toString();
                sb.append(s).append("\n");
            }
            System.out.println(wordslist);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
