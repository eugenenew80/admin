package kz.ecc.isbp.admin.auth.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.entity.Module;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "RoleModuleDict.findByIds", 
		query="select t from RoleModuleDict t where t.module.id=:moduleId and t.role.id=:roleId and t.levelId=:levelId and t.dict.id=:dictId and t.accessType=:accessType"
	)
})
@Entity
@Data
@EqualsAndHashCode(of= {"module", "role", "levelId", "dict", "accessType"})
public class RoleModuleDict implements HasId {
	public RoleModuleDict() {}
	
	public RoleModuleDict(Module module, Role role, Long levelId, Dict dict) {
		this.module=module;
		this.role=role;
		this.levelId=levelId;
		this.dict=dict;
	}
		
	public RoleModuleDict(Module module, Role role, Long levelId, Dict dict, Long accessType) {
		this(module, role, levelId, dict);
		this.accessType=accessType;
	}

	private Long id;
	private Module module;
	private Role role;
	private Long levelId;
	private Dict dict;
	private Long accessType;
	private Set<User> users;
	
	private Boolean isViewGranted;
	private Boolean isEditGranted;
}
