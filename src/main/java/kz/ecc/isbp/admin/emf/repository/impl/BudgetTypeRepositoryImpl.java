package kz.ecc.isbp.admin.emf.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import kz.ecc.isbp.admin.common.repository.AbstractReadOnlyRepository;
import kz.ecc.isbp.admin.emf.entity.BudgetType;
import kz.ecc.isbp.admin.emf.repository.BudgetTypeRepository;

@Stateless
public class BudgetTypeRepositoryImpl extends AbstractReadOnlyRepository<BudgetType> implements BudgetTypeRepository {		
	public BudgetTypeRepositoryImpl() { setClazz(BudgetType.class); }
	
	public BudgetTypeRepositoryImpl(EntityManager em) {  
		this();
		setEntityManager(em);
	}	
}
