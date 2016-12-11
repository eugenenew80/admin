package kz.ecc.isbp.admin.auth.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.auth.service.RoleService;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityService;

@Stateless
public class RoleServiceImpl extends AbstractEntityService<Role> implements RoleService {
	@Inject
	public RoleServiceImpl(Repository<Role> repository) {
		super(repository);
	}
}
