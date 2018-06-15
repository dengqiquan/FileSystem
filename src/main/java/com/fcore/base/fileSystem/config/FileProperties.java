package com.fcore.base.fileSystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "file")  
@PropertySource("classpath:sys.properties")
public class FileProperties {
	private String localpath;
	private String rootpath;
	private String previewRootPath;
	public String getPreviewRootPath() {
		return previewRootPath;
	}

	public void setPreviewRootPath(String previewRootPath) {
		this.previewRootPath = previewRootPath;
	}

	public String getRootpath() {
		return rootpath;
	}

	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}

	public String getLocalpath() {
		return localpath;
	}

	public void setLocalpath(String localpath) {
		this.localpath = localpath;
	}
	
}
