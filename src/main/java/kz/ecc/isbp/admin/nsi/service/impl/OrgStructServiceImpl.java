package kz.ecc.isbp.admin.nsi.service.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.common.service.AbstractEntityReadOnlyService;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;

@Stateless
public class OrgStructServiceImpl extends AbstractEntityReadOnlyService<OrgStruct> implements OrgStructService {
    @Inject
    public OrgStructServiceImpl(Repository<OrgStruct> repository) {
        super(repository);
    }
}
