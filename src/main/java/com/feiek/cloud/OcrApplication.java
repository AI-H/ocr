package com.feiek.cloud;

import com.feiek.cloud.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class OcrApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcrApplication.class, args);
	}


	@Bean
	public IdWorker getIdWorker(){
		return new IdWorker(0,0);
	}
	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("10240KB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("102400KB");
		return factory.createMultipartConfig();
	}
}
