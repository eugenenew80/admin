package kz.ecc.isbp.admin.auth.dto;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import kz.ecc.isbp.admin.common.dto.xmlAdapter.BooleanYesNoXmlAdapter;
import lombok.Data;

@Data
public class RoleDto {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private boolean isDisabled;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private boolean isArchive; 
	
	private Date createDate;
	private Date updateDate;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private boolean isGranted;
}
