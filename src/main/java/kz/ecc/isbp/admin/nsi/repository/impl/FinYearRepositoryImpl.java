package kz.ecc.isbp.admin.nsi.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.nsi.entity.FinYear;
import kz.ecc.isbp.admin.nsi.repository.FinYearRepository;

@Stateless
public class FinYearRepositoryImpl extends AbstractReadOnlyRepository<FinYear> implements FinYearRepository {	
	public FinYearRepositoryImpl() { setClazz(FinYear.class); }

	public FinYearRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
