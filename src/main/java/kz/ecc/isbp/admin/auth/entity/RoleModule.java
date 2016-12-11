package kz.ecc.isbp.admin.auth.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import kz.ecc.isbp.admin.fnd.entity.Module;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "RoleModule.findByIds", 
		query="select t from RoleModule t where t.role.id=:roleId and t.module.id=:moduleId and t.levelId=:levelId"
	)
})
@Entity
@Data
@EqualsAndHashCode(of= {"module", "role", "levelId"})
public class RoleModule implements HasId {

	public RoleModule() {} 
	
	public RoleModule(Module module, Role role, Long levelId) {
		this.role = role;
		this.module = module;
		this.levelId = levelId;
	}
	
	private Long id;
	private Role role;
	private Module module;
	private Long levelId;
	private Set<User> users;
	private Boolean isGranted;
	private String levelNameRu;
	private String levelNameKz;
	
	
	public String getLevelNameRu() {
		if (levelId==1) 
			levelNameRu = "ГУ";
		else if (levelId==2) 
			levelNameRu = "Комитет";
		else if (levelId==3) 
			levelNameRu = "АБП";
		
		return levelNameRu;
	}

	public String getLevelNameKz() {
		if (levelId==1) 
			levelNameKz = "ГУ";
		else if (levelId==2) 
			levelNameKz = "Комитет";
		else if (levelId==3) 
			levelNameKz = "АБП";
		
		return levelNameKz;
	}
}
