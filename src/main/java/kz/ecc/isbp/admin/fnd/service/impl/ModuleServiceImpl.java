package kz.ecc.isbp.admin.fnd.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityService;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.service.ModuleService;

@Stateless
public class ModuleServiceImpl extends AbstractEntityService<Module> implements ModuleService {
    @Inject
    public ModuleServiceImpl(Repository<Module> repository) {
        super(repository);
    }
}
