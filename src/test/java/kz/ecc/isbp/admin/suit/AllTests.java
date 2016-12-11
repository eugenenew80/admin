package kz.ecc.isbp.admin.suit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import kz.ecc.isbp.admin.repository.*;
import kz.ecc.isbp.admin.service.*;
//import kz.ecc.isbp.admin.selenium.Selenium2Example;
import kz.ecc.isbp.admin.webapi.integration.*;
import kz.ecc.isbp.admin.webapi.unit.*;

@RunWith(Suite.class)
@SuiteClasses({
	//BO
	AuthPermissionServiceTest.class,
	AuthRoleServiceTest.class,
	AuthUserServiceTest.class,
	OrgStructServiceTest.class,
	FndDictServiceTest.class,
	FndModuleServiceTest.class,
	EmfBudgetTypeServiceTest.class,
	EmfBudgetLevelServiceTest.class,
	BudgetVerServiceTest.class,
	BudgetRequestTypeServiceTest.class,
	BudgetVerStatusServiceTest.class,
	FinYearServiceTest.class,
	
	//webapi
	AuthUserResourceUnitTest.class,
	AuthPermissionResourceUnitTest.class,
	AuthRoleResourceUnitTest.class,
	OrgStructResourceUnitTest.class,
	FndModuleResourceUnitTest.class,
	FndDictResourceUnitTest.class,
	BudgetVerResourceUnitTest.class,
	
	//DAO
	AuthPermissionRepositoryTest.class,
	AuthRoleRepositoryTest.class,
	AuthUserRepositoryTest.class,
	OrgStructRepositoryTest.class,
	FndDictRepositoryTest.class,
	FndModuleRepositoryTest.class,
	EmfBudgetTypeRepositoryTest.class,
	EmfBudgetLevelRepositoryTest.class,
	BudgetVerRepositoryTest.class,
	BudgetRequestTypeRepositoryTest.class,
	BudgetVerStatusRepositoryTest.class,
	FinYearRepositoryTest.class,
	
	//webapi (integration)
	AuthUserResourceTest.class,
	AuthPermissionResourceTest.class,
	AuthRoleResourceTest.class,
	OrgStructResourceTest.class,
	FndModuleResourceTest.class,
	FndDictResourceTest.class,	
	
	//Selenium2Example.class
})


public class AllTests { }
