package kz.ecc.isbp.admin.fnd.dto;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import kz.ecc.isbp.admin.common.dto.xmlAdapter.BooleanYesNoXmlAdapter;
import lombok.Data;

@Data
public class ModuleDto {
	private Long id;
	private String nameRu;
	private String nameKz;
	private String code;
	private Date createDate;
	private Date updateDate;

	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isArchive; 

	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isGranted;
}
