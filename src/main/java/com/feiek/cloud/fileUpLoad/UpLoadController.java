package com.feiek.cloud.fileUpLoad;


import com.feiek.cloud.utils.OcrUtil;
import com.feiek.cloud.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload/")
public class UpLoadController {

    //文件保存地址
//    private static final String uploadPicturePath="D:\\uploadFile\\";
    private static final String uploadPicturePath="/usr/local/tomcat838/webapps/ROOT/imageWord/";


    @Autowired
    private IdWorker idWorker;

    @CrossOrigin
    @PostMapping("image")
    public Response<Map> uploadImage( MultipartFile file,String text){
        HashMap<String, String> map = new HashMap<>();
        map.put("image","系统忙碌，请稍后再试");
        map.put("path","/qweqwe/qweq");
        if (file!=null && !StringUtils.isEmpty(text)) {
            boolean flag = false;
            switch (text.trim()){
                case "wangheng":
                    flag = true;
                break;
                case "caoliyang":
                    flag = true;
                    break;
                case "shiyike":
                    flag = true;
                    break;
                case "zhaoyihan":
                    flag = true;
                    break;
                case "feikebuqu":
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }

            if(flag){
                File image = new File(uploadPicturePath);
                if(!image.exists()){
                    image.mkdirs();
                }
                String name = file.getOriginalFilename();
                //String uuid = UUID.randomUUID().toString().replace("-", "");

                String uuid=String.valueOf(idWorker.nextId());
                String fileName = uuid+"_"+name;
                //上传图片到服务器
                try {
                    file.transferTo(new File(uploadPicturePath,fileName));
                    map.put("path","/imageWord/"+fileName);
                } catch (IOException e) {
                    System.out.println("上传失败");
                    e.printStackTrace();
                }
                //图文识别
                if (file.getSize()<=1000000) {
                    try {
                        InputStream in = new FileInputStream(new File(uploadPicturePath+fileName));
                        byte[] data = new byte[in.available()];
                        in.read(data);
                        in.close();
                        String s = OcrUtil.imageToWords(data);
                        map.put("image",s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    map.put("image","图片大小超过1M");
                }
            }else{
                map.put("image","很遗憾，你没有权限使用");
            }
        } else {
            map.put("image","数据为空");
        }
        return Response.success(map);

    }
    @GetMapping("h")
    public  String getUploadPicturePath() {
        return uploadPicturePath;
    }
}
