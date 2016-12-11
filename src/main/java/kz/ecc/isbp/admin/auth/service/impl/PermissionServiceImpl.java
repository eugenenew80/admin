package kz.ecc.isbp.admin.auth.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.auth.service.PermissionService;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityService;

@Stateless
public class PermissionServiceImpl extends AbstractEntityService<Permission> implements PermissionService {
    @Inject
    public PermissionServiceImpl(Repository<Permission> repository) {
        super(repository);
    }
}
