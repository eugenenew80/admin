package kz.ecc.isbp.admin.auth.dto;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import kz.ecc.isbp.admin.common.dto.xmlAdapter.BooleanYesNoXmlAdapter;
import lombok.Data;

@Data
public class RoleModuleDto  {
	private Long id;
	private Long roleId;
	private String roleNameRu;
	private String roleNameKz;	
	private Long moduleId;
	private Long levelId;
	private String levelNameRu;
	private String levelNameKz;	
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isGranted;
}
