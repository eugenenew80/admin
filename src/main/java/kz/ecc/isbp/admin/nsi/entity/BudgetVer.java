package kz.ecc.isbp.admin.nsi.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.entity.ability.*;
import kz.ecc.isbp.admin.emf.entity.BudgetType;
import kz.ecc.isbp.admin.nsi.entity.BudgetRequestType;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.entity.BudgetVerStatus;
import kz.ecc.isbp.admin.nsi.entity.FinYear;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "BudgetVer.findAll",      query="select t from BudgetVer t order by t.nameRu"),
	@NamedQuery(name= "BudgetVer.findByName",   query="select t from BudgetVer t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetVer.findByNameRu", query="select t from BudgetVer t where t.nameRu=:name"),
	@NamedQuery(name= "BudgetVer.findByNameKz", query="select t from BudgetVer t where t.nameKz=:name"),
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class BudgetVer implements HasId, HasName, HasDates, HasAuthor {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String shortNameRu;
	private String shortNameKz;
	private BudgetVer parent;
	private BudgetType budgetType;
	private BudgetRequestType budgetRequestType;
	private BudgetVerStatus budgetVerStatus;
	private FinYear beginFinYear;
	private FinYear endFinYear;	
	private Date beginDate;
	private Date endDate;
	private Date createDate;
	private Date updateDate;
	private User createdBy;
	private User updatedBy;
}
