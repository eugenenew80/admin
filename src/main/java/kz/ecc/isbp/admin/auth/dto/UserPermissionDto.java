package kz.ecc.isbp.admin.auth.dto;

import java.io.*;
import javax.xml.bind.annotation.*;

import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.fnd.entity.Module;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserPermissionDto  implements Serializable {
	private static final long serialVersionUID = -1288994105399051684L;
	
	public Long userId;
	public Long roleId;
	public String roleNameRu;
	public String roleNameKz;
	public Long levelId;
	public String levelNameRu;
	public String levelNameKz;
	public Long moduleId;
	public String moduleNameRu;
	public String moduleNameKz;
	public Long permissionId;
	public String permissionType;
	public String permissionNameRu;
	public String permissionNameKz;
	public Long dictId;
	public String dictNameRu;
	public String dictNameKz;	
	
	public UserPermissionDto() {}
	
	public UserPermissionDto(User user, Module module, RoleModule roleModule, Permission permission) {
		userId = user.getId();
		moduleId = module.getId();
		moduleNameRu = module.getNameRu();
		moduleNameKz =  module.getNameKz();
		
		roleId = roleModule.getRole().getId();
		roleNameRu = roleModule.getRole().getNameRu();
		roleNameKz = roleModule.getRole().getNameKz();
		
		levelId = roleModule.getLevelId();
		if (levelId==1) {
			levelNameRu = "ГУ";
			levelNameKz = "ГУ";
		}
		else if (levelId==2) {
			levelNameRu = "Комитет";
			levelNameKz = "Комитет";
		}
		else if (levelId==3) {
			levelNameRu = "АБП";
			levelNameKz = "АБП";
		}
		
		permissionType = "base";
		permissionId = permission.getId();
		permissionNameRu = permission.getNameRu();
		permissionNameKz = permission.getNameKz();		
	}
	
	
	public UserPermissionDto(User user, Module module, RoleModule roleModule, RoleModuleDict roleModuleDict) {
		userId = user.getId();
		moduleId = module.getId();
		moduleNameRu = module.getNameRu();
		moduleNameKz =  module.getNameKz();
		
		roleId = roleModule.getRole().getId();
		roleNameRu = roleModule.getRole().getNameRu();
		roleNameKz = roleModule.getRole().getNameKz();
		
		levelId = roleModule.getLevelId();
		if (levelId==1) {
			levelNameRu = "ГУ";
			levelNameKz = "ГУ";
		}
		else if (levelId==2) {
			levelNameRu = "Комитет";
			levelNameKz = "Комитет";
		}
		else if (levelId==3) {
			levelNameRu = "АБП";
			levelNameKz = "АБП";
		}
		
		dictId = roleModuleDict.getDict().getId();
		dictNameRu = roleModuleDict.getDict().getNameRu();
		dictNameKz = roleModuleDict.getDict().getNameKz();
		
		permissionType = "additional";
		if (roleModuleDict.getAccessType().longValue()==1) {
			permissionNameRu = "Просмотр";
			permissionNameKz = "Просмотр";
		}
		else if (roleModuleDict.getAccessType().longValue()==2) {
			permissionNameRu = "Редактирование";
			permissionNameKz = "Редактирование";
		}
		else if (roleModuleDict.getAccessType().longValue()==3) {
			permissionNameRu = "Просмотр / Редактирование";
			permissionNameKz = "Просмотр / Редактирование";
		}		
	}
}
