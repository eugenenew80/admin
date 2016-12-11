package kz.ecc.isbp.admin.auth.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "Permission.findAll",      query="select t from Permission t order by t.nameRu"),
	@NamedQuery(name= "Permission.findByName",   query="select t from Permission t where t.nameRu=:name"),
	@NamedQuery(name= "Permission.findByNameRu", query="select t from Permission t where t.nameRu=:name"),
	@NamedQuery(name= "Permission.findByNameKz", query="select t from Permission t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class Permission implements HasId, HasName, HasDates, HasArchive, HasDisable {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	private Date createDate;
	private Date updateDate;
	private Boolean isArchive = false;
	private Boolean isDisabled = false;
	private Set<Role> roles;
}
