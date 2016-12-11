package kz.ecc.isbp.admin.helper;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import java.util.*;
import kz.ecc.isbp.admin.auth.entity.*;
import kz.ecc.isbp.admin.emf.entity.*;
import kz.ecc.isbp.admin.fnd.entity.*;
import kz.ecc.isbp.admin.nsi.entity.*;

public final class EntitiesHelper {
	
	public final static String USER_SUR_NAME="Иванов";
	public final static String USER_NAME="Иван";
	public final static String USER_EMAIL="ivanov@gmail.com";
	public final static String USER_IIN="111111111111";
	public final static String USER_BIN="222222222222";

	public final static String ORG_STRUCT_CODE="01";
	public final static String ORG_STRUCT_NAME_RU="Отдел 1";
	public final static String ORG_STRUCT_NAME_KZ="Отдел 1";
	
	public final static String ROLE_CODE="01";
	public final static String ROLE_NAME_RU="Роль 1";
	public final static String ROLE_NAME_KZ="Роль 1";

	public final static String MODULE_CODE="01";
	public final static String MODULE_NAME_RU="Модуль 1";
	public final static String MODULE_NAME_KZ="Модуль 1";
	
	public final static String PERMISSION_CODE="01";
	public final static String PERMISSION_NAME_RU="Полномочие 1";
	public final static String PERMISSION_NAME_KZ="Полномочие 1";
	
	public final static String DICT_CODE="01";
	public final static String DICT_TYPE="тип";
	public final static String DICT_NAME_RU="Справочник 1";
	public final static String DICT_NAME_KZ="Справочник 1";

	public final static String BUDGET_TYPE_NAME_RU="Тип бюджета 1";
	public final static String BUDGET_TYPE_NAME_KZ="Тип бюджета 1";

	public final static String BUDGET_LEVEL_NAME_RU="Уровень бюджета 1";
	public final static String BUDGET_LEVEL_NAME_KZ="Уровень бюджета 1";
	
	public final static Long FIN_YEAR_YEAR = 2016L;
	public final static Date FIN_YEAR_BEGIN_DATE = calendarFor(2016, 1, 1).getTime();
	public final static Date FIN_YEAR_END_DATE = calendarFor(2016, 12, 31).getTime();

	public final static String BUDGET_VER_STATUS_NAME_RU="Статус бюджета 1";
	public final static String BUDGET_VER_STATUS_NAME_KZ="Статус бюджета 1";
	public final static String BUDGET_VER_STATUS_CODE="01";
	public final static Date BUDGET_VER_STATUS_BEGIN_DATE = calendarFor(2016, 1, 1).getTime();
	public final static Date BUDGET_VER_STATUS_END_DATE = calendarFor(2016, 12, 31).getTime();
	
	public final static String BUDGET_REQUEST_TYPE_NAME_RU="Тип бюджета 1";
	public final static String BUDGET_REQUEST_TYPE_NAME_KZ="Тип бюджета 1";
	public final static String BUDGET_REQUEST_TYPE_CODE="01";
	public final static Date BUDGET_REQUEST_TYPE_BEGIN_DATE = calendarFor(2016, 1, 1).getTime();
	public final static Date BUDGET_REQUEST_TYPE_END_DATE = calendarFor(2016, 12, 31).getTime();

	public final static String BUDGET_VER_NAME_RU="Версия бюджета 1";
	public final static String BUDGET_VER_NAME_KZ="Версия бюджета 1";
	public final static String BUDGET_VER_SHORT_NAME_RU="Версия 1";
	public final static String BUDGET_VER_SHORT_NAME_KZ="Версия 1";
	public final static Long BUDGET_VER_BEGIN_YEAR_ID = 1L;
	public final static Long BUDGET_VER_END_YEAR_ID = 2L;
	public final static Date BUDGET_VER_BEGIN_DATE = calendarFor(2016, 0, 1).getTime();
	public final static Date BUDGET_VER_END_DATE = calendarFor(2016, 11, 31).getTime();
	public final static Long BUDGET_VER_STATUS_ID = 1L;
	public final static Long BUDGET_VER_TYPE_ID = 1L;
	public final static Long BUDGET_VER_REQUEST_TYPE_ID = 1L;
	
	
    public static Calendar calendarFor(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }
	
	public static User newUser() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		User user = new User();
		user.setId(1l);
		user.setSurname(USER_SUR_NAME);
		user.setName(USER_NAME);
		user.setEmail(USER_EMAIL);
		user.setIin(USER_IIN);
		user.setBin(USER_BIN);
		user.setCreateDate(c.getTime());
		user.setUpdateDate(c.getTime());
		user.setOrgStruct(newOrgStruct(1L));
		
		//user.setModules(new HashSet<>());
		//user.setRoleModules(new HashSet<>());
		//user.setRoleModuleDicts(new HashSet<>());
		
		return user;
	}
	
	public static User newUser(Long id) {
		User user = newUser();
		user.setId(id);
		return user;
	}
	
	
	public static OrgStruct newOrgStruct() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		OrgStruct dep = new OrgStruct();
		dep.setId(1L);
		dep.setNameRu(ORG_STRUCT_NAME_RU);
		dep.setNameKz(ORG_STRUCT_NAME_KZ);
		dep.setCreateDate(c.getTime());
		dep.setUpdateDate(c.getTime());
		return dep;
	}	
	
	
	public static OrgStruct newOrgStruct(Long id) {
		OrgStruct dep = newOrgStruct();
		dep.setId(id);
		return dep;
	}
	
	
	public static Role newRole() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		Role role = new Role();
		role.setId(1L);
		role.setCode(ROLE_CODE);
		role.setNameRu(ROLE_NAME_RU);
		role.setNameKz(ROLE_NAME_KZ);
		role.setIsArchive(false);
		role.setIsDisabled(false);
		role.setCreateDate(c.getTime());
		role.setUpdateDate(c.getTime());

		return role;
	}
	
	
	public static Role newRole(Long id) {
		Role role = newRole();
		role.setId(id);
		return role;
	}
	
	
	
	public static Module newModule() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		Module module = new Module();
		module.setId(1L);
		module.setCode(MODULE_CODE);
		module.setNameRu(MODULE_NAME_RU);
		module.setNameKz(MODULE_NAME_KZ);
		module.setIsArchive(false);
		module.setCreateDate(c.getTime());
		module.setUpdateDate(c.getTime());
		return module;
	}
	
	public static Module newModule(Long id) {
		Module module = newModule();
		module.setId(id);
		return module;
	}
	
	
	public static Permission newPermission() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		Permission permission = new Permission();
		permission.setId(1L);
		permission.setCode(PERMISSION_CODE);
		permission.setNameRu(PERMISSION_NAME_RU);
		permission.setNameKz(PERMISSION_NAME_KZ);
		permission.setIsDisabled(false);
		permission.setIsArchive(false);
		permission.setCreateDate(c.getTime());
		permission.setUpdateDate(c.getTime());
		return permission;
	}

	
	public static Permission newPermission(Long id) {
		Permission permission = newPermission();
		permission.setId(id);
		return permission; 
	}
	
	
	public static Dict newDict() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		Dict dict = new Dict();
		dict.setId(1L);
		dict.setCode(DICT_CODE);
		dict.setType(DICT_TYPE);
		dict.setNameRu(DICT_NAME_RU);
		dict.setNameKz(DICT_NAME_KZ);
		dict.setIsArchive(false);
		dict.setCreateDate(c.getTime());
		dict.setUpdateDate(c.getTime());
		return dict;
	}

	
	public static Dict newDict(Long id) {
		Dict dict = newDict();
		dict.setId(id);
		return dict;
	}
	
	
	public static BudgetType newBudgetType() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		BudgetType budgetType = new BudgetType();
		budgetType.setId(1L);
		budgetType.setNameRu(BUDGET_TYPE_NAME_RU);
		budgetType.setNameKz(BUDGET_TYPE_NAME_KZ);
		return budgetType;
	}

	
	public static BudgetType newBudgetType(Long id) {
		BudgetType budgetType = newBudgetType();
		budgetType.setId(id);
		return budgetType;
	}
	
	
	public static BudgetLevel newBudgetLevel() {
		Calendar c = Calendar.getInstance();
		c.set(2016, 1, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);	
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		BudgetLevel budgetLevel = new BudgetLevel();
		budgetLevel.setId(1L);
		budgetLevel.setNameRu(BUDGET_LEVEL_NAME_RU);
		budgetLevel.setNameKz(BUDGET_LEVEL_NAME_KZ);
		return budgetLevel;
	}

	
	public static BudgetLevel newBudgetLevel(Long id) {
		BudgetLevel budgetLevel = newBudgetLevel();
		budgetLevel.setId(id);
		return budgetLevel;
	}
	
	
	public static BudgetVerStatus newBudgetVerStatus() {
		BudgetVerStatus budgetVerStatus = new BudgetVerStatus();
		budgetVerStatus.setNameRu(BUDGET_VER_STATUS_NAME_RU);
		budgetVerStatus.setNameKz(BUDGET_VER_STATUS_NAME_KZ);
		budgetVerStatus.setCode(BUDGET_VER_STATUS_CODE);
		budgetVerStatus.setBeginDate(BUDGET_VER_STATUS_BEGIN_DATE);
		budgetVerStatus.setEndDate(BUDGET_VER_STATUS_END_DATE);
		budgetVerStatus.setCreateDate(calendarFor(2016, 1, 1).getTime());
		budgetVerStatus.setUpdateDate(calendarFor(2016, 1, 1).getTime());
		return budgetVerStatus;		
	}
	
	public static BudgetVerStatus newBudgetVerStatus(Long id) {
		BudgetVerStatus budgetVerStatus = newBudgetVerStatus();
		budgetVerStatus.setId(id);
		return budgetVerStatus;	
	}
	
	
	public static BudgetRequestType newBudgetRequestType() {
		BudgetRequestType budgetRequestType = new BudgetRequestType();
		budgetRequestType.setNameRu(BUDGET_REQUEST_TYPE_NAME_RU);
		budgetRequestType.setNameKz(BUDGET_REQUEST_TYPE_NAME_KZ);
		budgetRequestType.setCode(BUDGET_REQUEST_TYPE_CODE);
		budgetRequestType.setBeginDate(BUDGET_REQUEST_TYPE_BEGIN_DATE);
		budgetRequestType.setEndDate(BUDGET_REQUEST_TYPE_END_DATE);
		budgetRequestType.setCreateDate(calendarFor(2016, 1, 1).getTime());
		budgetRequestType.setUpdateDate(calendarFor(2016, 1, 1).getTime());
		return budgetRequestType;		
	}
	
	public static BudgetRequestType newBudgetRequestType(Long id) {
		BudgetRequestType budgetRequestType = newBudgetRequestType();
		budgetRequestType.setId(id);
		return budgetRequestType;	
	}
	
	
	public static FinYear newFinYear() {
		FinYear finYear = new FinYear();
		
		finYear.setYear(FIN_YEAR_YEAR);
		finYear.setBeginDate(FIN_YEAR_BEGIN_DATE);
		finYear.setEndDate(FIN_YEAR_END_DATE);
		finYear.setCreateDate(calendarFor(2016, 1, 1).getTime());
		finYear.setUpdateDate(calendarFor(2016, 1, 1).getTime());
		
		return finYear;		
	}
	
	public static FinYear newFinYear(Long id) {
		FinYear finYear = newFinYear();
		finYear.setId(id);
		return finYear;		
	}
	
	
	public static BudgetVer newBudgetVer() {
		BudgetVer budgetVer = new BudgetVer();
		budgetVer.setNameRu(BUDGET_VER_NAME_RU);
		budgetVer.setNameKz(BUDGET_VER_NAME_KZ);
		budgetVer.setShortNameRu(BUDGET_VER_SHORT_NAME_RU);
		budgetVer.setShortNameKz(BUDGET_VER_SHORT_NAME_KZ);
		budgetVer.setBeginDate(BUDGET_VER_BEGIN_DATE);
		budgetVer.setEndDate(BUDGET_VER_END_DATE);
		budgetVer.setCreateDate(calendarFor(2016, 1, 1).getTime());
		budgetVer.setUpdateDate(calendarFor(2016, 1, 1).getTime());
		budgetVer.setBeginFinYear(newFinYear(BUDGET_VER_BEGIN_YEAR_ID));
		budgetVer.setEndFinYear(newFinYear(BUDGET_VER_END_YEAR_ID));
		budgetVer.setBudgetRequestType(newBudgetRequestType(BUDGET_VER_REQUEST_TYPE_ID));
		budgetVer.setBudgetType(newBudgetType(BUDGET_VER_TYPE_ID));
		budgetVer.setBudgetVerStatus(newBudgetVerStatus(BUDGET_VER_STATUS_ID));
		return budgetVer;		
	}
	
	
	public static BudgetVer newBudgetVer(Long id) {
		BudgetVer budgetVer = newBudgetVer();
		budgetVer.setId(id);
		return budgetVer;	
	}

	
	public static void assertUser(User user) {
		assertNotNull(user);
		assertNotNull(user.getId());
		assertTrue(user.getId()>0);
		assertEquals(USER_SUR_NAME, user.getSurname());
		assertEquals(USER_NAME, user.getName() );
		assertEquals(USER_EMAIL, user.getEmail() );
		assertEquals(USER_IIN, user.getIin() );
		assertEquals(USER_BIN, user.getBin() );
		assertNotNull(user.getCreateDate());
		assertNotNull(user.getUpdateDate());		
		assertNotNull(user.getOrgStruct());
		
		assertEquals(ORG_STRUCT_NAME_RU, user.getOrgStruct().getNameRu() );
		assertEquals(ORG_STRUCT_NAME_KZ, user.getOrgStruct().getNameKz() );
	}
		
	
	public static void assertRole(Role role) {
		assertNotNull(role);
		assertNotNull(role.getId());
		assertTrue(role.getId()>0);
		assertEquals(ROLE_CODE, role.getCode());
		assertEquals(ROLE_NAME_RU, role.getNameRu());
		assertEquals(ROLE_NAME_KZ, role.getNameKz());
		assertFalse(role.getIsDisabled());
		assertFalse(role.getIsArchive());
		assertNotNull(role.getCreateDate());
		assertNotNull(role.getUpdateDate());		
	}
	
	
	public static void assertOrgStruct(OrgStruct OrgStruct) {
		assertNotNull(OrgStruct);
		assertNotNull(OrgStruct.getId());
		assertTrue(OrgStruct.getId()>0);
		assertEquals(ORG_STRUCT_NAME_RU, OrgStruct.getNameRu());
		assertEquals(ORG_STRUCT_NAME_KZ, OrgStruct.getNameKz());
		assertNotNull(OrgStruct.getCreateDate());
		assertNotNull(OrgStruct.getUpdateDate());		
	}
	
	public static void assertModule(Module module) {
		assertNotNull(module);
		assertNotNull(module.getId());
		assertTrue(module.getId()>0);
		assertEquals(MODULE_CODE, module.getCode());
		assertEquals(MODULE_NAME_RU, module.getNameRu());
		assertEquals(MODULE_NAME_KZ, module.getNameKz());
		assertFalse(module.getIsArchive());
		assertNotNull(module.getCreateDate());
		assertNotNull(module.getUpdateDate());		
	}
	
	public static void assertPermission(Permission permission) {
		assertNotNull(permission);
		assertNotNull(permission.getId());
		assertTrue(permission.getId()>0);
		assertEquals(PERMISSION_CODE, permission.getCode());
		assertEquals(PERMISSION_NAME_RU, permission.getNameRu());
		assertEquals(PERMISSION_NAME_KZ, permission.getNameKz());
		assertFalse(permission.getIsArchive());
		assertFalse(permission.getIsDisabled());
		assertNotNull(permission);
		assertNotNull(permission.getUpdateDate());		
	}

	public static void assertDict(Dict dict) {
		assertNotNull(dict);
		assertNotNull(dict.getId());
		assertTrue(dict.getId()>0);
		assertEquals(DICT_CODE, dict.getCode());
		assertEquals(DICT_TYPE, dict.getType());
		assertEquals(DICT_NAME_RU, dict.getNameRu());
		assertEquals(DICT_NAME_KZ, dict.getNameKz());
		assertFalse(dict.getIsArchive());
		assertNotNull(dict.getCreateDate());
		assertNotNull(dict.getUpdateDate());		
	}
	
	public static void assertBudgetType(BudgetType budgetType) {
		assertNotNull(budgetType);
		assertNotNull(budgetType.getId());
		assertTrue(budgetType.getId()>0);
		assertEquals(BUDGET_TYPE_NAME_RU, budgetType.getNameRu());
		assertEquals(BUDGET_TYPE_NAME_KZ, budgetType.getNameKz());
	}
	
	public static void assertBudgetLevel(BudgetLevel budgetLevel) {
		assertNotNull(budgetLevel);
		assertNotNull(budgetLevel.getId());
		assertTrue(budgetLevel.getId()>0);
		assertEquals(BUDGET_LEVEL_NAME_RU, budgetLevel.getNameRu());
		assertEquals(BUDGET_LEVEL_NAME_KZ, budgetLevel.getNameKz());
	}
	
	
	public static void assertBudgetVer(BudgetVer budgetVer) {
		assertThat(budgetVer, is(not(nullValue())));
		assertThat(budgetVer.getId(), is(not(nullValue())));
		assertThat(budgetVer.getId(), greaterThan(0L));
		assertThat(BUDGET_VER_NAME_RU, is(equalTo(budgetVer.getNameRu())) );
		assertThat(BUDGET_VER_NAME_KZ, is(equalTo(budgetVer.getNameKz())) )  ;
		assertThat(budgetVer.getCreateDate(), is(not(nullValue())));
		assertThat(budgetVer.getUpdateDate(), is(not(nullValue())));
	}
	
	
	public static void assertBudgetVerStatus(BudgetVerStatus budgetVerStatus) {
		assertThat(budgetVerStatus, is(not(nullValue())));
		assertThat(budgetVerStatus.getId(), is(not(nullValue())));
		assertThat(budgetVerStatus.getId(), greaterThan(0L));
		assertThat(BUDGET_VER_STATUS_NAME_RU, is(equalTo(budgetVerStatus.getNameRu())) );
		assertThat(BUDGET_VER_STATUS_NAME_KZ, is(equalTo(budgetVerStatus.getNameKz())) )  ;
		assertThat(budgetVerStatus.getCreateDate(), is(not(nullValue())));
		assertThat(budgetVerStatus.getUpdateDate(), is(not(nullValue())));
	}
	
	
	public static void assertBudgetRequestType(BudgetRequestType budgetRequestType) {
		assertThat(budgetRequestType, is(not(nullValue())));
		assertThat(budgetRequestType.getId(), is(not(nullValue())));
		assertThat(budgetRequestType.getId(), greaterThan(0L));
		assertThat(BUDGET_REQUEST_TYPE_NAME_RU, is(equalTo(budgetRequestType.getNameRu())) );
		assertThat(BUDGET_REQUEST_TYPE_NAME_KZ, is(equalTo(budgetRequestType.getNameKz())) )  ;
		assertThat(budgetRequestType.getCreateDate(), is(not(nullValue())));
		assertThat(budgetRequestType.getUpdateDate(), is(not(nullValue())));
	}
	
	
	public static void assertFinYear(FinYear finYear) {
		assertThat(finYear, is(not(nullValue())));
		assertThat(finYear.getId(), is(not(nullValue())));
		assertThat(finYear.getId(), greaterThan(0L));
		assertThat(finYear.getCreateDate(), is(not(nullValue())));
		assertThat(finYear.getUpdateDate(), is(not(nullValue())));
	}
}
