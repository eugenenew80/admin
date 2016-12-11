package kz.ecc.isbp.admin.nsi.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityReadOnlyService;
import kz.ecc.isbp.admin.nsi.entity.BudgetRequestType;
import kz.ecc.isbp.admin.nsi.service.BudgetRequestTypeService;

@Stateless
public class BudgetRequestTypeServiceImpl extends AbstractEntityReadOnlyService<BudgetRequestType> implements BudgetRequestTypeService {
    @Inject
    public BudgetRequestTypeServiceImpl(Repository<BudgetRequestType> repository) {
        super(repository);
    }
}

