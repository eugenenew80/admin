package kz.ecc.isbp.admin.nsi.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.repository.OrgStructRepository;

@Stateless
public class OrgStructRepositoryImpl extends AbstractReadOnlyRepository<OrgStruct> implements OrgStructRepository {
	public OrgStructRepositoryImpl() { setClazz(OrgStruct.class); }
	
	public OrgStructRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
