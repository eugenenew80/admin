package kz.ecc.isbp.admin.fnd.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.repository.DictRepository;

@Stateless
public class DictRepositoryImpl extends AbstractReadOnlyRepository<Dict> implements DictRepository {
	public DictRepositoryImpl() { setClazz(Dict.class); }
	
	public DictRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
