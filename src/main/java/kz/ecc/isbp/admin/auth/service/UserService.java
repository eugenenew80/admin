package kz.ecc.isbp.admin.auth.service;

import java.util.List;
import java.util.Set;
import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.service.EntityService;
import kz.ecc.isbp.admin.fnd.entity.Module;


public interface UserService extends EntityService<User> {
	
	User disable(Long userId);
	
    User enable(Long userId);
	
    User toArchive(Long userId);
	
    User fromArchive(Long userId);
	
	
    User addModules(Long userId, Set<Module> modules);
    
    User addRoles(Long userId, Long moduleId, Set<RoleModule> roleModules);
	
    User addDicts(Long userId, Long moduleId, Long roleId, Long levelId, Set<RoleModuleDict> roleModuleDicts);

    
    List<Module> getModules(Long userId);
    
    List<RoleModule> getRoles(Long userId, Long moduleId);
    
    List<RoleModuleDict> getDicts(Long userId, Long moduleId, Long roleId, Long levelId);
}
