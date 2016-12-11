package kz.ecc.isbp.admin.emf.entity;

import javax.persistence.*;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "BudgetLevel.findAll",      query="select t from BudgetLevel t order by t.nameRu"),
	@NamedQuery(name= "BudgetLevel.findByName",   query="select t from BudgetLevel t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetLevel.findByNameRu", query="select t from BudgetLevel t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetLevel.findByNameKz", query="select t from BudgetLevel t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class BudgetLevel implements HasId, HasName  {
	private Long id;
	private String nameRu;
	private String nameKz;
}
