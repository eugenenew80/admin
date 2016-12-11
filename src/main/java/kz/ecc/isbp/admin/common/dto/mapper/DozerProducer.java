package kz.ecc.isbp.admin.common.dto.mapper;

import java.util.Arrays;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import org.dozer.DozerBeanMapper;

@Singleton
@Startup
public class DozerProducer {
	public DozerProducer() {
		mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList(
			"mapping/UserDtoMapping.xml",
			"mapping/ModuleDtoMapping.xml",
			"mapping/RoleModuleDtoMapping.xml",
			"mapping/RoleModuleDictDtoMapping.xml",
			"mapping/PermissionDtoMapping.xml",
			"mapping/RoleDtoMapping.xml",
			"mapping/DictDtoMapping.xml",
			"mapping/BudgetVerDtoMapping.xml",
			"mapping/OrgStructDtoMapping.xml"
		));	
	}

	@Produces
	public DozerBeanMapper getMapper() {
		return mapper;
	}	
	
	private final DozerBeanMapper mapper; 
}
