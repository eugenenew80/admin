package kz.ecc.isbp.admin.nsi.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import kz.ecc.isbp.admin.common.repository.AbstractRepository;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.repository.BudgetVerRepository;

@Stateless
public class BudgetVerRepositoryImpl extends AbstractRepository<BudgetVer> implements BudgetVerRepository {	
	public BudgetVerRepositoryImpl() { setClazz(BudgetVer.class); }
	
	public BudgetVerRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
