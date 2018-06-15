package com.fcore.base.fileSystem;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ServletComponentScan
@EnableScheduling
@MapperScan(value = { "com.fcore.base.fileSystem.dao" })
@ComponentScan(value = { "com.fcore.base.fileSystem" })
public class Application {

	protected static Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private ServletContext servletContext;
	/**
	 * 系统所有数据在此处初始化
	 * 
	 * SysParamInitService中提供部分初始化方法
	 */
	public @PostConstruct void  init() {
		logger.info("=================初始化数据字典到缓存中===================");
		/*sysParamInitService.initSysDictToRedis();
		logger.info("数据结果："+redisService.get("sex"));*/
		logger.info(servletContext.getRealPath("/"));
	}

	@PreDestroy
	public void dostory() {
		logger.info("=================系统关闭注销===================");
	}

	public static void main(String[] args) {
		// 启动程序
		SpringApplication.run(Application.class, args);
	}
}
