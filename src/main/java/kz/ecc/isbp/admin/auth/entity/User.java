package kz.ecc.isbp.admin.auth.entity;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import kz.ecc.isbp.admin.fnd.entity.*;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "User.findAll", query="select t from User t order by t.name")
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class User implements HasId, HasDates, HasArchive, HasDisable {
	private Long id;
	private String iin;
	private String surname; 
	private String name;
	private String patronymic;
	private String bin;
	private Boolean isDirector = false;
	private String phoneNumber;
	private String email;
	private Boolean isDisabled = false;
	private Boolean isArchive = false;
	private Date createDate;
	private Date updateDate;
	private OrgStruct orgStruct;
	private Set<Module> modules = new HashSet<>();
	private Set<RoleModule> roleModules = new HashSet<>();
	private Set<RoleModuleDict> roleModuleDicts = new HashSet<>();
}
