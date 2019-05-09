package com.feiek.cloud.service;
/**
 * @author 飞客不去
 * @date  创建时间 2019/5/8 10:45
 * @version 1.0
 * 图文识别的总接口，统一定义图文识别的规范，具体实现由子类实现
 */
public interface OcrService {
    /**
     * 通过获取图片文件的位置，来识别图片内容
     * @param filePath  文件的路径
     * @return 图片的内容
     */
    String identImageToWords(String filePath);


    /**
     * 通过文件的字节码文件，来识别图片内容
     * @param imageFile  源文件的字节码数据
     * @return 图片的内容
     */
    String identImageToWords(byte[] imageFile);


    /**
     * 将接口返回的内容，格式化成，直接的文字
     * @param content 调用第三方接口返回的内容
     * @return  将第三方接口格式化后的规范，简洁的文字
     */
    String exchangeWords(String content);



}
