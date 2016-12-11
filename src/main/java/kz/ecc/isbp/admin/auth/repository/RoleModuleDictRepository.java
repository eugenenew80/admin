package kz.ecc.isbp.admin.auth.repository;

import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.common.repository.Repository;

public interface RoleModuleDictRepository extends Repository<RoleModuleDict> {
	RoleModuleDict selectByIds(Long moduleId, Long roleId, Long levelId, Long dictId, Long accessType);
}
