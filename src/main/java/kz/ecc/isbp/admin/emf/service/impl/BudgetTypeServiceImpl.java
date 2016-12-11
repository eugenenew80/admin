package kz.ecc.isbp.admin.emf.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityReadOnlyService;
import kz.ecc.isbp.admin.emf.entity.BudgetType;
import kz.ecc.isbp.admin.emf.service.BudgetTypeService;

@Stateless
public class BudgetTypeServiceImpl extends AbstractEntityReadOnlyService<BudgetType> implements BudgetTypeService {
    @Inject
    public BudgetTypeServiceImpl(Repository<BudgetType> repository) {
        super(repository);
    }
}
