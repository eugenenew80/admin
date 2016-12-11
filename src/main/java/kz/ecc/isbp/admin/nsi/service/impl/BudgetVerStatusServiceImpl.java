package kz.ecc.isbp.admin.nsi.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityReadOnlyService;
import kz.ecc.isbp.admin.nsi.entity.BudgetVerStatus;
import kz.ecc.isbp.admin.nsi.service.BudgetVerStatusService;

@Stateless
public class BudgetVerStatusServiceImpl extends AbstractEntityReadOnlyService<BudgetVerStatus> implements BudgetVerStatusService {
    @Inject
    public BudgetVerStatusServiceImpl(Repository<BudgetVerStatus> repository) {
        super(repository);
    }
}