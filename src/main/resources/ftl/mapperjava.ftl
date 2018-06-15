package ${base_packge}.${type_dao};

import ${base_packge}.${type_model}.${table.entityName?cap_first};

/**   
* @Title: I${table.entityName?cap_first}Mapper.java 
* @Package ${base_packge}.${type_dao}
* @Description: ${table.table_description}
* @author ${author}
* @date ${current_now}
* @version V1.0   
* create by codeFactory
*/
public interface ${table.entityName?cap_first}Dao extends BaseDao<${table.entityName?cap_first},Long>{
	
}
