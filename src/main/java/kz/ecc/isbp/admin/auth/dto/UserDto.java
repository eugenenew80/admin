package kz.ecc.isbp.admin.auth.dto;

import java.util.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import kz.ecc.isbp.admin.common.dto.xmlAdapter.BooleanYesNoXmlAdapter;
import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String iin;
	private String surname;
	private String name;
	private String patronymic;
	private String bin;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isDirector;
	
	private String phoneNumber;
	private String email;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isDisabled;
	
	@XmlJavaTypeAdapter( BooleanYesNoXmlAdapter.class )
	private Boolean isArchive;
	
	private Long orgStructId;
	private String orgStructNameRu;
	private String orgStructNameKz;

	
	private Date createDate;
	private Date updateDate;
}
