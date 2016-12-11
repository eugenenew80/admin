package kz.ecc.isbp.admin.auth.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.auth.repository.PermissionRepository;
import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;

@Stateless
public class PermissionRepositoryImpl extends AbstractReadOnlyRepository<Permission> implements PermissionRepository {	
	public PermissionRepositoryImpl() { setClazz(Permission.class); }
	
	public PermissionRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
