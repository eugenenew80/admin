package kz.ecc.isbp.admin.nsi.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.nsi.entity.BudgetVerStatus;
import kz.ecc.isbp.admin.nsi.repository.BudgetVerStatusRepository;

@Stateless
public class BudgetVerStatusRepositoryImpl extends AbstractReadOnlyRepository<BudgetVerStatus> implements BudgetVerStatusRepository {	
	public BudgetVerStatusRepositoryImpl() { setClazz(BudgetVerStatus.class); }

	public BudgetVerStatusRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
