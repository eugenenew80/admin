package kz.ecc.isbp.admin.nsi.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "BudgetVerStatus.findAll",      query="select t from BudgetVerStatus t order by t.nameRu"),
	@NamedQuery(name= "BudgetVerStatus.findByName",   query="select t from BudgetVerStatus t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetVerStatus.findByNameRu", query="select t from BudgetVerStatus t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetVerStatus.findByNameKz", query="select t from BudgetVerStatus t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class BudgetVerStatus implements HasId, HasName, HasDates, HasAuthor {
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
