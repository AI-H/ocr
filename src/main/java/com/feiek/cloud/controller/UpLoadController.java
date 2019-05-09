package com.feiek.cloud.controller;


import com.feiek.cloud.entity.Response;
import com.feiek.cloud.service.LogService;
import com.feiek.cloud.service.ocr.baidu.OCRBaidu;
import com.feiek.cloud.service.ocr.huawei.OCRASKUtilHuawei;
import com.feiek.cloud.service.ocr.tengxun.OcrUtilTengXun;
import com.feiek.cloud.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class UpLoadController extends BaseController{

    @Value("${upload.filePath}")
    private   String  uploadPicturePath ;
    @Value("${upload.fileLocalPath}")
    private   String  uploadLocalPicturePath ;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OcrUtilTengXun tengXun;

    @Autowired
    private OCRASKUtilHuawei huawei;

    @Autowired
    private OCRBaidu baidu;


    @Autowired
    private LogService logService;

    @CrossOrigin
    @PostMapping("tengxun")
    public Response<Map> uploadImageTengXun(MultipartFile file, String text){

        logService.saveLog(getCurrentUser(),"tengxun");
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
                String fileName = uuid+"-tengxun-"+name;
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
                        String s = tengXun.identImageToWords(data);
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



    @CrossOrigin
    @PostMapping("huawei")
    @PreAuthorize(" hasRole('狮')")
    public Response<Map> uploadImageHuaWei( MultipartFile file,String text){
        //记录操作日志到数据库
        logService.saveLog(getCurrentUser(),"huawei");
        HashMap<String, String> map = new HashMap<>();
        map.put("image","华为系统忙碌，请稍后再试");
        map.put("path","/huawei/huawei");
        if (file!=null && !StringUtils.isEmpty(text)) {
            boolean flag = false;
            switch (text.trim()){
                case "caoliyang":
                    flag = true;
                    break;
                case "wangheng":
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
                String fileName = uuid+"-huawei-"+name;
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
                        String s = huawei.identImageToWords(uploadPicturePath + fileName);
                        if (StringUtils.isEmpty(s)){
                            map.put("image","华为系统忙碌，请稍后再试");
                        }else{
                            map.put("image",s);
                        }
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



    @CrossOrigin
    @PostMapping("baidu")
    public Response<Map> uploadImageBaidu( MultipartFile file,String text){

        logService.saveLog(getCurrentUser(),"baidu");
        Map<String,String> map=uploadImage(file,text,"baidu");
        try {
            String localpath = map.remove("localpath");
            String s = baidu.identImageToWords(localpath);
            if (StringUtils.isEmpty(s)) {
                map.put("image", "百度系统忙碌，请稍后再试");
            } else {
                map.put("image", s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.success(map);
    }


    /**
     * 上传图片到服务器
     * @param file
     * @param text
     * @return 返回一个图片在服务器的路径，和网络路径
     */
    private Map<String,String> uploadImage(MultipartFile file,String text,String company){
        HashMap<String, String> map = new HashMap<>();
        map.put("image",company+"系统忙碌，请稍后再试");
        map.put("path","/"+company);
        if (file!=null && !StringUtils.isEmpty(text)) {
            boolean flag = false;
            switch (text.trim()){
                case "shiyike":
                    flag = true;
                    break;
                case "caoliyang":
                    flag = true;
                    break;
                case "wangheng":
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
                String uuid=String.valueOf(idWorker.nextId());
                String fileName = uuid+"-"+company+"-"+name;
                //上传图片到服务器
                try {
                    file.transferTo(new File(uploadPicturePath,fileName));
                    map.put("path","/imageWord/"+fileName);
                    map.put("localpath",uploadPicturePath + fileName);
                } catch (IOException e) {
                    System.out.println("上传失败");

                    e.printStackTrace();
                }
            }else{
                map.put("image","很遗憾，你没有权限使用");
            }
        } else {
            map.put("image","数据为空");
        }
        return map;
    }
}
