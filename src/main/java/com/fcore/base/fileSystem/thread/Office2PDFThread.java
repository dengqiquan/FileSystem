package com.fcore.base.fileSystem.thread;

import java.io.File;

import com.fcore.base.fileSystem.config.FileProperties;
import com.fcore.base.fileSystem.entity.SysFile;
import com.fcore.base.fileSystem.service.SysFileService;
import com.fcore.base.fileSystem.utils.FileUtil;

public class Office2PDFThread implements Runnable{

	private FileProperties fileProperties;
	private SysFile sysFile;
	private SysFileService sysFileService;

	public Office2PDFThread() {
	}
	
	public Office2PDFThread(FileProperties fileProperties,SysFile sysFile,SysFileService sysFileService) {
		this.fileProperties = fileProperties;
		this.sysFile = sysFile;
		this.sysFileService = sysFileService;
	}
	
	
	@Override
	public void run() {
		String filePath = sysFile.getPath();
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1,filePath.length());
		String uuid = fileName.substring(0,fileName.indexOf("."));
		String localpath = fileProperties.getLocalpath();
		String previewRootPath = fileProperties.getPreviewRootPath();
		
		String fileDir = previewRootPath+filePath.substring(0,filePath.lastIndexOf("/")+1);
		File file = new File(localpath+fileDir);
		if(!file.exists()){
			file.mkdirs();
		} 
		
		String previewPath = fileDir+uuid+".pdf";
		String pdfPath = localpath+previewPath;
		String officePath = localpath+fileProperties.getRootpath()+filePath;
		
		if("word".equals(sysFile.getType())){
			FileUtil.word2pdf(officePath, pdfPath);
		}else if("ppt".equals(sysFile.getType())){
			FileUtil.slides2pdf(officePath, pdfPath);
		}else if("excel".equals(sysFile.getType())){
			FileUtil.cells2pdf(officePath, pdfPath);
		}else if ("txt".equals(sysFile.getType())) {
			FileUtil.txt2pdf(officePath, pdfPath);
		}
		sysFile.setPreviewPath(previewPath);
		sysFileService.update(sysFile);
	}
}
