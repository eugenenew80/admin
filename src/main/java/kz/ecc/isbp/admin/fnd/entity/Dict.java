package kz.ecc.isbp.admin.fnd.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "Dict.findAll",      query="select t from Dict t order by t.nameRu"),
	@NamedQuery(name= "Dict.findByName",   query="select t from Dict t where t.nameRu=:name"),
	@NamedQuery(name= "Dict.findByNameRu", query="select t from Dict t where t.nameRu=:name"),
	@NamedQuery(name= "Dict.findByNameKz", query="select t from Dict t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class Dict implements HasId, HasName, HasDates, HasArchive {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	private String type;
	private Date createDate;
	private Date updateDate;
	private Boolean isArchive = false;
}
