package kz.ecc.isbp.admin.auth.repository;

import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.common.repository.Repository;

public interface RoleModuleRepository extends Repository<RoleModule> {
	RoleModule selectByIds(Long moduleId, Long roleId, Long levelId);
}
