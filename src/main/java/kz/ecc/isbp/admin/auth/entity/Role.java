package kz.ecc.isbp.admin.auth.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "Role.findAll",      query="select t from Role t order by t.nameRu"),
	@NamedQuery(name= "Role.findByName",   query="select t from Role t where t.nameRu=:name"),
	@NamedQuery(name= "Role.findByNameRu", query="select t from Role t where t.nameRu=:name"),
	@NamedQuery(name= "Role.findByNameKz", query="select t from Role t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class Role implements HasId, HasName, HasDates, HasArchive, HasDisable {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	private Boolean isDisabled = false; 
	private Date createDate;
	private Date updateDate;
	private Boolean isArchive = false; 
	private Set<Permission> permissions;
}
