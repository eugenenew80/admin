package kz.ecc.isbp.admin.fnd.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityService;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.service.DictService;

@Stateless
public class DictServiceImpl extends AbstractEntityService<Dict> implements DictService {
    @Inject
    public DictServiceImpl(Repository<Dict> repository) {
        super(repository);
    }
}
