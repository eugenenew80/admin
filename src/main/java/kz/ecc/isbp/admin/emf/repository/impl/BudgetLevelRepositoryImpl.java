package kz.ecc.isbp.admin.emf.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.emf.entity.BudgetLevel;
import kz.ecc.isbp.admin.emf.repository.BudgetLevelRepository;

@Stateless
public class BudgetLevelRepositoryImpl extends AbstractReadOnlyRepository<BudgetLevel> implements BudgetLevelRepository {		
	public BudgetLevelRepositoryImpl() { setClazz(BudgetLevel.class); }
	
	public BudgetLevelRepositoryImpl(EntityManager em) {  
		this();
		setEntityManager(em);
	}
}
