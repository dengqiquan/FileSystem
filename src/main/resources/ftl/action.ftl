package ${base_packge}.${type_action};

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fcore.base.fileSystem.bean.CommonConstants;
import com.fcore.base.fileSystem.entity.SysUser;
import com.fcore.base.fileSystem.utils.CommUtil;
import com.fcore.base.fileSystem.utils.DateTimeUtil;

import ${base_packge}.${type_model}.${table.entityName?cap_first};
import ${base_packge}.service.${table.entityName?cap_first}Service;

@Controller
@RequestMapping(value=CommonConstants.ROOT_VIEWS+"/${table.entityName?uncap_first}")
public class ${table.entityName?cap_first}Controller extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(${table.entityName?cap_first}Controller.class);

	@Autowired
	private ${table.entityName?cap_first}Service ${table.entityName?uncap_first}Service;
	
	@RequestMapping(value="/list")
	public String list(Model model,${table.entityName?cap_first} ${table.entityName?uncap_first}) {
		List<${table.entityName?cap_first}> list = directoryService.getList(${table.entityName?uncap_first});
		model.addAttribute("list", list);
		model.addAttribute("${table.entityName?uncap_first}", ${table.entityName?uncap_first});
		return "/views/${table.entityName?uncap_first}/list";
	}
	
	@RequestMapping(value="/edit")
	public String edit(Model model,${table.entityName?cap_first} ${table.entityName?uncap_first}) {
		if(${table.entityName?uncap_first}.getId()!=null && ${table.entityName?uncap_first}.getId()>0){
			${table.entityName?uncap_first} = ${table.entityName?uncap_first}Service.getById(${table.entityName?uncap_first}.getId());
		}
		model.addAttribute("${table.entityName?uncap_first}", ${table.entityName?uncap_first});
		return "/views/${table.entityName?uncap_first}/edit";
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletResponse response,${table.entityName?cap_first} ${table.entityName?uncap_first}){
		SysUser user = this.getSessionUser();
		JSONObject object = new JSONObject();
		if(${table.entityName?uncap_first}.get${table.primary_colmun?if_exists?cap_first}() != null && ${table.entityName?uncap_first}.get${table.primary_colmun?if_exists?cap_first}() >0){
			${table.entityName?uncap_first}.setUpdateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
			${table.entityName?uncap_first}.setUpdateUserName(user.getUserName());
			${table.entityName?uncap_first}Service.update(${table.entityName?uncap_first});
			object.put("state",1);
		}else{
			${table.entityName?uncap_first}.setCreateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
			${table.entityName?uncap_first}.setCreateUserName(user.getUserName());
			${table.entityName?uncap_first}.setIsDelete(0);
			long id = ${table.entityName?uncap_first}Service.add(${table.entityName?uncap_first});
			object.put("state",1);
		}
		CommUtil.writeJson(response, object.toString());
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public void deleteById(HttpServletRequest request,HttpServletResponse response){
		JSONObject object = new JSONObject();
		SysUser user = this.getSessionUser();
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			${table.entityName?cap_first} ${table.entityName?uncap_first} = ${table.entityName?uncap_first}Service.getById(Long.parseLong(id));
			${table.entityName?uncap_first}.setUpdateTime(DateTimeUtil.getNowDateStr(DateTimeUtil.yyyy_MM_dd_HH_mm_ss));
			${table.entityName?uncap_first}.setUpdateUserName(user.getUserName());
			${table.entityName?uncap_first}.setIsDelete(1);
			${table.entityName?uncap_first}Service.update(${table.entityName?uncap_first});
		}
		CommUtil.writeJson(response, object.toString());
	}
	
	
	@RequestMapping("/getById")
	@ResponseBody
	public ${table.entityName?cap_first} getById(HttpServletRequest request){
		${table.entityName?cap_first} ${table.entityName?uncap_first} = null;
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			${table.entityName?uncap_first} = ${table.entityName?uncap_first}Service.getById(Long.parseLong(id));
		}
		return ${table.entityName?uncap_first};
	}
}
