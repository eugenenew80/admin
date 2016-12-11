package kz.ecc.isbp.admin.nsi.dto;

import java.util.Date;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import lombok.Data;

@Data
public class BudgetVerDto {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String shortNameRu;
	private String shortNameKz;
	private BudgetVer parentId;
	private Date beginDate;
	private Date endDate;
	private Date createDate;
	private Date updateDate;
	private Long budgetTypeId;
	private String budgetTypeNameRu;
	private String budgetTypeNameKz;
	private Long budgetRequestTypeId;
	private String budgetRequestTypeNameRu;
	private String budgetRequestTypeNameKz;
	private Long budgetVerStatusId;
	private String budgetVerStatusNameRu;
	private String budgetVerStatusNameKz;
	private Long beginFinYearId;
	private Long beginFinYearValue;	
	private Long endFinYearId;
	private Long endFinYearValue;	
}
