package com.feiek.cloud.service.ocr.baidu;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baidu.aip.ocr.AipOcr;
import com.feiek.cloud.service.OcrService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OCRBaidu implements OcrService{

    //设置APPID/AK/SK

    @Value("${baidu.APP_ID}")
    public  String APP_ID;

    @Value("${baidu.API_KEY}")
    public  String API_KEY;

    @Value("${baidu.SECRET_KEY}")
    public  String SECRET_KEY;




    /**
     * 百度图文识别接口
     * @param filePath 文件的路径
     * @return
     */
    @Override
    public String identImageToWords(String filePath) {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
//        识别语言类型，默认为CHN_ENG
        options.put("language_type", "CHN_ENG");
//        是否检测图像朝向，默认不检测，即：false
        options.put("detect_direction", "true");
//        是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
        options.put("detect_language", "true");
//        是否返回识别结果中每一行的置信度
        options.put("probability", "true");


        // 参数为本地图片路径
//        String image = "D:\\uploadFile\\0190412175649.png";
        JSONObject res = client.basicGeneral(filePath, options);
//        JSONObject res = client.accurateGeneral(image, options);

        System.out.println(res);
        System.out.println(res.toString(2));

        System.out.println(exchangeWords(res.toString()));
        return exchangeWords(res.toString());

//        // 参数为本地图片二进制数组
//        byte[] file = readImageFile(image);
//        res = client.basicGeneral(file, options);
//        System.out.println(res.toString(2));


//        // 通用文字识别, 图片参数为远程url图片
//        JSONObject res = client.basicGeneralUrl(url, options);
//        System.out.println(res.toString(2));
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

            Object wordslist = hashMap.get("words_result");

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
