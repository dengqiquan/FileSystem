package com.fcore.base.fileSystem.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fcore.base.fileSystem.bean.CommonConstants;
import com.fcore.base.fileSystem.config.FileProperties;
import com.fcore.base.fileSystem.entity.Directory;
import com.fcore.base.fileSystem.entity.SysFile;
import com.fcore.base.fileSystem.entity.SysUser;
import com.fcore.base.fileSystem.service.DirectoryService;
import com.fcore.base.fileSystem.service.SysFileService;
import com.fcore.base.fileSystem.thread.Office2PDFThread;
import com.fcore.base.fileSystem.utils.CommUtil;
import com.fcore.base.fileSystem.utils.DateTimeUtil;
import com.fcore.base.fileSystem.utils.FileUtil;
import com.fcore.base.fileSystem.utils.HttpClientUtil;
import com.fcore.base.fileSystem.utils.MapResult;
import com.fcore.base.fileSystem.utils.MapResult.Invoker;

@Controller
@RequestMapping(value=CommonConstants.ROOT_VIEWS+"/sysFile")
public class SysFileController extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(SysFileController.class);

	@Autowired
	private SysFileService sysFileService;
	@Autowired
	private DirectoryService directoryService;
	@Autowired
	private FileProperties fileProperties;
	
	@RequestMapping(value="/list")
	public String list(Model model,SysFile sysFile) {
		List<SysFile> list = sysFileService.getList(sysFile);
		model.addAttribute("list", list);
		model.addAttribute("sysFile", sysFile);
		return "/views/sysFile/list";
	}
	
	@RequestMapping(value="/edit")
	public String edit(Model model,SysFile sysFile) {
		if(sysFile.getId()!=null && sysFile.getId()>0){
			sysFile = sysFileService.getById(sysFile.getId());
		}
		model.addAttribute("sysFile", sysFile);
		return "/views/sysFile/edit";
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletResponse response,SysFile sysFile){
		SysUser user = this.getSessionUser();
		JSONObject object = new JSONObject();
		if(sysFile.getId() != null && sysFile.getId() >0){
			sysFile.setUpdateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
			sysFile.setUpdateUserName(user.getUserName());
			sysFileService.update(sysFile);
			object.put("state",1);
		}else{
			sysFile.setCreateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
			sysFile.setCreateUserName(user.getUserName());
			sysFile.setIsDelete(0);
			long id = sysFileService.add(sysFile);
			object.put("state",1);
		}
		CommUtil.writeJson(response, object.toString());
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(Long id) {
		SysUser user = this.getSessionUser();
		Map<String, Object> resultMap = MapResult.invoke(new Invoker() {
			@Override
			public void writeBody(Map<String, Object> body) throws Exception {
				SysFile sysFile = sysFileService.getById(id);
				if(sysFile!=null){
					sysFile.setUpdateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
					sysFile.setUpdateUserName(user.getUserName());
					sysFile.setIsDelete(1);
					sysFileService.update(sysFile);
					body.put("state", 1);
				}
			}
		});
		return resultMap;
	}
	
	
	@RequestMapping("/getById")
	@ResponseBody
	public SysFile getById(HttpServletRequest request){
		SysFile sysFile = null;
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			sysFile = sysFileService.getById(Long.parseLong(id));
		}
		return sysFile;
	}
	
	@RequestMapping(value = "uploadFile")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request,SysFile sysFile) {
		SysUser user = this.getSessionUser();
		Map<String, Object> resultMap = MapResult.invoke(new Invoker() {
			@Override
			public void writeBody(Map<String, Object> body) throws Exception {
				String fileType = FileUtil.getFileType(sysFile.getType());
				if(StringUtils.isNotEmpty(fileType)){
					Directory dir = directoryService.getById(sysFile.getDirId());
					if(dir!=null){
						String suffix = sysFile.getName().substring(sysFile.getName().lastIndexOf("."));
						String filePath = FileUtil.uploudFile(fileProperties.getLocalpath()+fileProperties.getRootpath()+dir.getCurPath(), suffix, request);
						sysFile.setPath(dir.getCurPath()+filePath.substring(filePath.lastIndexOf("/")+1,filePath.length()));
						if(!"word,excel,ppt,txt".contains(sysFile.getType())){
							sysFile.setPreviewPath(fileProperties.getRootpath()+sysFile.getPath());
						}					
						sysFile.setCreateUserName(user.getUserName());
						sysFile.setCreateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
						sysFile.setType(FileUtil.getFileType(sysFile.getType()));
						sysFile.setIsDelete(0);
						sysFile.setSuffix(suffix);
						sysFileService.add(sysFile);
						body.put("state", 1);
						
						//启动一个线程转换数据
						if("word,excel,ppt,txt".contains(sysFile.getType())){
							Office2PDFThread office2pdfThread = new Office2PDFThread(fileProperties, sysFile, sysFileService);
							Thread thread = new Thread(office2pdfThread);  
							thread.start(); 
						} 
					}	
				}else{
					body.put("state", -100);//文件格式不正确
				}
			}
		});
		return resultMap;
	}
	
	@RequestMapping(value = "downloadFile")
	@ResponseBody
	public void download(Long id, HttpServletResponse response, HttpServletRequest request) {
		try {
			SysFile sysFile = sysFileService.getById(id);
			if(sysFile!=null){
				File file = new File(fileProperties.getLocalpath()+fileProperties.getRootpath()+sysFile.getPath());
				// 取得文件名。
				String filename = sysFile.getName();
				// 以流的形式下载文件。
				InputStream fis = new BufferedInputStream(new FileInputStream(file));
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();
				// 清空response
				response.reset();
				// 设置response的Header
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping(value="view/{id}")
	public String view(@PathVariable("id") long id){
		SysFile file = sysFileService.getById(id);
		String url = "/html/pdf.html?file="+file.getPreviewPath();
		try {
			if("word,excel,ppt,pdf,txt".contains(file.getType())){
				url = "/html/pdf.html?file="+URLEncoder.encode(file.getPreviewPath(),"UTF-8");
			}else if("image".equals(file.getType())){
				url = "/html/image.html?file="+URLEncoder.encode(file.getPreviewPath(),"UTF-8");
			}else if("video".equals(file.getType())){
				url = "/html/video.html?file="+URLEncoder.encode(file.getPreviewPath(),"UTF-8");
			}else if("txt".equals(file.getType())){
				url = "/html/txt.html?file="+URLEncoder.encode(file.getPreviewPath(),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return "redirect:"+url;
	}
	
	@RequestMapping(value = "viewDown")
	@ResponseBody
	public void viewDown(String filePath,HttpServletResponse response){
		filePath = "http://pic.flaginfo.cn/G1/M00/00/36/CgAAWVhcs7-Ac09bAI2vOGFJljo170.pdf";
		HttpClientUtil.getInstance().downHttpFile(filePath, "aa.pdf", response);
	}
}
