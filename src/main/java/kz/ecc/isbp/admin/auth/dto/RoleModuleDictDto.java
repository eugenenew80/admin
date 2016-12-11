package kz.ecc.isbp.admin.auth.dto;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import kz.ecc.isbp.admin.common.dto.xmlAdapter.BooleanYesNoXmlAdapter;
import lombok.Data;

@Data
public class RoleModuleDictDto  {
	private Long id;
	private Long roleId;	
	private Long moduleId;
	private Long levelId;
	private Long dictId;
	private String dictNameRu;
	private String dictNameKz;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isViewGranted;

	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isEditGranted;


	public Long getAccessType() {
		Long accessType = 0L;
		if (isViewGranted && isEditGranted)
			accessType=3L;
		else if (isViewGranted)
			accessType=1L;
		else if (isEditGranted)
			accessType=2L;
		
		return accessType;
	}
}
