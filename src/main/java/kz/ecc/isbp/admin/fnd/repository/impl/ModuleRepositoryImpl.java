package kz.ecc.isbp.admin.fnd.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.repository.ModuleRepository;

@Stateless
public class ModuleRepositoryImpl extends AbstractReadOnlyRepository<Module> implements ModuleRepository {
	public ModuleRepositoryImpl() { setClazz(Module.class); }
	
	public ModuleRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
