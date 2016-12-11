package kz.ecc.isbp.admin.common.webapi.config;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.auth.webapi.PermissionResourceImpl;
import kz.ecc.isbp.admin.auth.webapi.RoleResourceImpl;
import kz.ecc.isbp.admin.auth.webapi.UserResourceImpl;
import kz.ecc.isbp.admin.common.webapi.exception.EJBTransactionRolledbackExceptionMapperImpl;
import kz.ecc.isbp.admin.common.webapi.exception.ExceptionMapperImpl;
import kz.ecc.isbp.admin.common.webapi.exception.WebApplicationExceptionMapperImpl;
import kz.ecc.isbp.admin.fnd.webapi.DictResourceImpl;
import kz.ecc.isbp.admin.fnd.webapi.ModuleResourceImpl;
import kz.ecc.isbp.admin.nsi.webapi.*;
import kz.ecc.isbp.admin.security.webapi.UserPermissionImpl;


@ApplicationPath("webapi")
public class JaxRsConfig extends Application {
	public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(UserResourceImpl.class);
        resources.add(RoleResourceImpl.class);
        resources.add(PermissionResourceImpl.class);
        resources.add(ModuleResourceImpl.class);
        resources.add(DictResourceImpl.class);
        resources.add(BudgetVerResourceImpl.class);
        resources.add(OrgStructResourceImpl.class);
        resources.add(UserPermissionImpl.class);
        
        resources.add(WebApplicationExceptionMapperImpl.class);
        resources.add(EJBTransactionRolledbackExceptionMapperImpl.class);
        resources.add(ExceptionMapperImpl.class);
        return resources;
    }
}
