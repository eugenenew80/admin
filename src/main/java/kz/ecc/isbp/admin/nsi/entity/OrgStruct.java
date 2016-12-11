package kz.ecc.isbp.admin.nsi.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "OrgStruct.findAll",      query="select t from OrgStruct t order by t.nameRu"),
	@NamedQuery(name= "OrgStruct.findByName", 	query="select t from OrgStruct t where t.nameRu=:name"),
	@NamedQuery(name= "OrgStruct.findByNameRu", query="select t from OrgStruct t where t.nameRu=:name"),
	@NamedQuery(name= "OrgStruct.findByNameKz", query="select t from OrgStruct t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class OrgStruct implements HasId, HasName, HasDates {
	private Long id;
	private String nameRu;
	private String nameKz;
	private Date createDate;
	private Date updateDate;
}
