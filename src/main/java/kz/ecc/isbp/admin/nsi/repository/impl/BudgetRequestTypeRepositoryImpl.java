package kz.ecc.isbp.admin.nsi.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.nsi.entity.BudgetRequestType;
import kz.ecc.isbp.admin.nsi.repository.BudgetRequestTypeRepository;

@Stateless
public class BudgetRequestTypeRepositoryImpl extends AbstractReadOnlyRepository<BudgetRequestType> implements BudgetRequestTypeRepository {	
	public BudgetRequestTypeRepositoryImpl() { setClazz(BudgetRequestType.class); }

	public BudgetRequestTypeRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}
}
