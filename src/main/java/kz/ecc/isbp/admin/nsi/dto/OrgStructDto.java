package kz.ecc.isbp.admin.nsi.dto;

import java.util.Date;
import lombok.Data;

@Data
public class OrgStructDto {
	private Long id;
	private String nameRu;
	private String nameKz;
	private Date createDate;
	private Date updateDate;
}
