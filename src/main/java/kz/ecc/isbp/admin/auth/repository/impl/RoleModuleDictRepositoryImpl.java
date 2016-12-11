package kz.ecc.isbp.admin.auth.repository.impl;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.auth.repository.RoleModuleDictRepository;
import kz.ecc.isbp.admin.common.repository.AbstractRepository;


@Stateless
public class RoleModuleDictRepositoryImpl extends AbstractRepository<RoleModuleDict> implements RoleModuleDictRepository {
	
	public RoleModuleDictRepositoryImpl() { setClazz(RoleModuleDict.class); }
		
	public RoleModuleDictRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
	
	
	public RoleModuleDict selectByIds(Long moduleId, Long roleId, Long levelId, Long dictId, Long accessType) {
		TypedQuery<RoleModuleDict> query = getEntityManager().createNamedQuery("RoleModuleDict.findByIds", RoleModuleDict.class);
		query.setParameter("moduleId", moduleId)
				.setParameter("roleId", roleId)
				.setParameter("levelId", levelId)
				.setParameter("dictId", dictId)
				.setParameter("accessType", accessType);
		
		List<RoleModuleDict> entities = query.getResultList();		
		return (entities.isEmpty() ? null : entities.get(0));
	}
}
