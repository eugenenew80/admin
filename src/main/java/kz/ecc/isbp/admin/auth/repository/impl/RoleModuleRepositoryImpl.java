package kz.ecc.isbp.admin.auth.repository.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.auth.repository.RoleModuleRepository;
import kz.ecc.isbp.admin.common.repository.AbstractRepository;


@Stateless
public class RoleModuleRepositoryImpl extends AbstractRepository<RoleModule> implements RoleModuleRepository {
	
	public RoleModuleRepositoryImpl() { setClazz(RoleModule.class); }
	
	public RoleModuleRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}	
	

	public RoleModule selectByIds(Long moduleId, Long roleId, Long levelId) {
		TypedQuery<RoleModule> query = getEntityManager().createNamedQuery("RoleModule.findByIds", RoleModule.class);
		query.setParameter("levelId", levelId)
				.setParameter("roleId", roleId)
				.setParameter("moduleId", moduleId);
		
		List<RoleModule> entities = query.getResultList();		
		return (entities.isEmpty() ? null : entities.get(0));
	}
}
