package kz.ecc.isbp.admin.nsi.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "BudgetRequestType.findAll",      query="select t from BudgetRequestType t order by t.nameRu"),
	@NamedQuery(name= "BudgetRequestType.findByName",   query="select t from BudgetRequestType t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetRequestType.findByNameRu", query="select t from BudgetRequestType t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetRequestType.findByNameKz", query="select t from BudgetRequestType t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class BudgetRequestType implements HasId,HasName, HasDates, HasAuthor {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	private Date beginDate;
	private Date endDate;
	private Date createDate;
	private Date updateDate;
	private User createdBy;
	private User updatedBy;
}
