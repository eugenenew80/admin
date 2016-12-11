package kz.ecc.isbp.admin.nsi.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityReadOnlyService;
import kz.ecc.isbp.admin.nsi.entity.FinYear;
import kz.ecc.isbp.admin.nsi.service.FinYearService;

@Stateless
public class FinYearServiceImpl extends AbstractEntityReadOnlyService<FinYear> implements FinYearService {
    @Inject
    public FinYearServiceImpl(Repository<FinYear> repository) {
        super(repository);
    }
}
