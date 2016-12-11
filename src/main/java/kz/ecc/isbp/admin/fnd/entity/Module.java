package kz.ecc.isbp.admin.fnd.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "Module.findAll",      query="select t from Module t order by t.nameRu"),
	@NamedQuery(name= "Module.findByName",   query="select t from Module t where t.nameRu=:name"),
	@NamedQuery(name= "Module.findByNameRu", query="select t from Module t where t.nameRu=:name"),
	@NamedQuery(name= "Module.findByNameKz", query="select t from Module t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class Module implements HasId, HasName, HasDates, HasArchive {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	private Date createDate;
	private Date updateDate;
	private Boolean isArchive = false;
	private Set<User> users;
	private Boolean isGranted;
}
