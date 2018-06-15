package ${base_packge}.${type_service}.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${base_packge}.dao.${table.entityName?cap_first}Dao;
import ${base_packge}.entity.${table.entityName?cap_first};
import ${base_packge}.service.${table.entityName?cap_first}Service;

 /**   
* @Title: ${table.entityName?cap_first}ServiceImpl.java 
* @Package ${base_packge}.${type_service}
* @Description: ${table.table_description}
* @author ${author}
* @date ${current_now}
* @version V1.0   
* create by codeFactory
*/
@Service("${table.entityName?cap_first}ServiceImpl")
public class ${table.entityName?cap_first}ServiceImpl  extends BaseServiceImpl<${table.entityName?cap_first},Long> implements ${table.entityName?cap_first}Service{
	@Autowired
	private ${table.entityName?cap_first}Dao ${table.entityName?uncap_first}Dao;
	@Autowired
	public void setBaseDao() {
		super.setBaseDao(${table.entityName?uncap_first}Dao);
	}
}