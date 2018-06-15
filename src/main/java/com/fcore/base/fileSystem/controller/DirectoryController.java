package com.fcore.base.fileSystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fcore.base.fileSystem.bean.CommonConstants;
import com.fcore.base.fileSystem.config.FileProperties;
import com.fcore.base.fileSystem.entity.Directory;
import com.fcore.base.fileSystem.entity.SysFile;
import com.fcore.base.fileSystem.entity.SysUser;
import com.fcore.base.fileSystem.service.DirectoryService;
import com.fcore.base.fileSystem.service.SysFileService;
import com.fcore.base.fileSystem.utils.DateTimeUtil;
import com.fcore.base.fileSystem.utils.FileUtil;
import com.fcore.base.fileSystem.utils.MapResult;
import com.fcore.base.fileSystem.utils.MapResult.Invoker;

@Controller
@RequestMapping(value = CommonConstants.ROOT_VIEWS + "/directory")
public class DirectoryController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(DirectoryController.class);

	@Autowired
	private DirectoryService directoryService;
	@Autowired
	private FileProperties fileProperties;
	@Autowired
	private SysFileService sysFileService;

	@RequestMapping(value = "/listPage")
	public String list(Model model, Long parentId) {
		model.addAttribute("parentId", parentId);
		return "/views/directory/list";
	}

	@RequestMapping(value = "/addDirPage")
	public String addDirPage(Model model, Directory directory) {
		if (directory.getId() != null && directory.getId() > 0) {
			directory = directoryService.getById(directory.getId());
		}
		log.info(fileProperties.getLocalpath());
		model.addAttribute("directory", directory);
		return "/views/directory/addDir";
	}

	@RequestMapping(value = "/list", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> list(Long parentId) {
		Map<String, Object> resultMap = MapResult.invoke(new Invoker() {
			@Override
			public void writeBody(Map<String, Object> body) throws Exception {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("parentId", parentId == null ? 0 : parentId);
				
				//目录
				List<Directory> directories = directoryService.getByParams(params);
				Directory dir = directoryService.getById(Long.parseLong(params.get("parentId").toString()));
				if (dir == null) {
					dir = new Directory();
					dir.setCurPath("/");
					dir.setId(0l);
				}
				
				//文件
				params.clear();
				params.put("dirId", dir.getId());
				List<SysFile> files = sysFileService.getByParams(params);
				
				body.put("directories", directories);
				body.put("files", files);
				body.put("directory", dir);
			}
		});
		return resultMap;
	}

	@RequestMapping(value = "/edit")
	public String edit(Model model, Directory directory) {
		if (directory.getId() != null && directory.getId() > 0) {
			directory = directoryService.getById(directory.getId());
		}
		model.addAttribute("directory", directory);
		return "/views/directory/edit";
	}

	@RequestMapping("/saveDir")
	@ResponseBody
	public Map<String, Object> saveDir(Directory directory) {
		SysUser user = this.getSessionUser();
		Map<String, Object> resultMap = MapResult.invoke(new Invoker() {
			@Override
			public void writeBody(Map<String, Object> body) throws Exception {
				// 判断文件名在该目录下是否存在
				Boolean isExist = directoryService.checkDirName(directory.getId(), directory.getName(),
						directory.getParentId());
				if (isExist) {
					body.put("state", -100);
				} else {
					directory.setCreateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
					directory.setCreateUserName(user.getUserName());
					directory.setIsDelete(0);
					if(directory.getParentId()==0){
						directory.setCurPath("/"+directory.getName()+"/");
					}else {
						Directory dir = directoryService.getById(directory.getParentId());
						directory.setCurPath(dir.getCurPath()+directory.getName()+"/");
					}
					directoryService.add(directory);
					FileUtil.createDir(fileProperties.getLocalpath()+directory.getCurPath());
					body.put("state", 1);
				}
			}
		});
		return resultMap;
	}
	
	@RequestMapping("/updateDir")
	@ResponseBody
	public Map<String, Object> updateDir(Directory directory) {
		SysUser user = this.getSessionUser();
		Map<String, Object> resultMap = MapResult.invoke(new Invoker() {
			@Override
			public void writeBody(Map<String, Object> body) throws Exception {
				// 判断文件名在该目录下是否存在
				Boolean isExist = directoryService.checkDirName(directory.getId(), directory.getName(),directory.getParentId());
				if (isExist) {
					body.put("state", -100);
				} else {
					Directory dir = directoryService.getById(directory.getId());
					String oldDir = dir.getCurPath();
					if(dir!=null){
						dir.setName(directory.getName());
						dir.setUpdateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
						dir.setUpdateUserName(user.getUserName());
						dir.setCurPath(directoryService.getParentCurPath(dir.getCurPath())+directory.getName()+"/");
						directoryService.update(dir);
						FileUtil.renameDir(fileProperties.getLocalpath()+oldDir, fileProperties.getLocalpath()+dir.getCurPath());
						body.put("state", 1);
					}
				}
			}
		});
		return resultMap;
	}
	

	@RequestMapping("/deleteById")
	@ResponseBody
	public Map<String, Object> deleteById(Long id) {
		SysUser user = this.getSessionUser();
		Map<String, Object> resultMap = MapResult.invoke(new Invoker() {
			@Override
			public void writeBody(Map<String, Object> body) throws Exception {
				Directory directory = directoryService.getById(id);
				if(directory!=null){
					directory.setUpdateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
					directory.setUpdateUserName(user.getUserName());
					directory.setIsDelete(1);
					directoryService.update(directory);
					body.put("state", 1);
				}
			}
		});
		return resultMap;
	}

	@RequestMapping("/getById")
	@ResponseBody
	public Directory getById(HttpServletRequest request) {
		Directory directory = null;
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			directory = directoryService.getById(Long.parseLong(id));
		}
		return directory;
	}
}
