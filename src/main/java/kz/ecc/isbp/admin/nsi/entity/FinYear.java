package kz.ecc.isbp.admin.nsi.entity;

import java.util.*;
import javax.persistence.*;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.entity.ability.*;
import lombok.*;

@NamedQueries({
	@NamedQuery(name= "FinYear.findAll",    query="select t from FinYear t order by t.year"),
	@NamedQuery(name= "FinYear.findByYear", query="select t from FinYear t where t.year=:year")
})
@Entity
@Data
@EqualsAndHashCode(of= {"id"})
public class FinYear implements HasId, HasDates, HasAuthor {
	private Long id;
	private Long year;
	private Date beginDate;
	private Date endDate;
	private Date createDate;
	private Date updateDate;
	private User createdBy;
	private User updatedBy;
}
