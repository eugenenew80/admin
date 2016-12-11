package kz.ecc.isbp.admin.emf.entity;

import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@NamedQueries({
	@NamedQuery(name= "BudgetType.findAll",      query="select t from BudgetType t order by t.nameRu"),
	@NamedQuery(name= "BudgetType.findByName",   query="select t from BudgetType t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetType.findByNameRu", query="select t from BudgetType t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetType.findByNameKz", query="select t from BudgetType t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class BudgetType implements HasId, HasName {
	private Long id;
	private String nameRu;
	private String nameKz;
}
