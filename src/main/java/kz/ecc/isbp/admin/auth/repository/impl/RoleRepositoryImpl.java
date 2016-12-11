package kz.ecc.isbp.admin.auth.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.auth.repository.RoleRepository;
import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;

@Stateless
public class RoleRepositoryImpl extends AbstractReadOnlyRepository<Role> implements RoleRepository {
	public RoleRepositoryImpl() { setClazz(Role.class); }
	
	public RoleRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
