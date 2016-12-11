package kz.ecc.isbp.admin.nsi.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityService;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.service.BudgetVerService;

@Stateless
public class BudgetVerServiceImpl extends AbstractEntityService<BudgetVer> implements BudgetVerService {
    @Inject
    public BudgetVerServiceImpl(Repository<BudgetVer> repository) {
        super(repository);
    }
}

