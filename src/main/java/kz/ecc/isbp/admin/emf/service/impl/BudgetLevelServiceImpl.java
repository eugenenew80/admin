package kz.ecc.isbp.admin.emf.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityReadOnlyService;
import kz.ecc.isbp.admin.emf.entity.BudgetLevel;
import kz.ecc.isbp.admin.emf.service.BudgetLevelService;

@Stateless
public class BudgetLevelServiceImpl extends AbstractEntityReadOnlyService<BudgetLevel> implements BudgetLevelService {
    @Inject
    public BudgetLevelServiceImpl(Repository<BudgetLevel> repository) {
        super(repository);
    }
}
