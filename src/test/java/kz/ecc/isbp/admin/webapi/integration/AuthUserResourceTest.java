package kz.ecc.isbp.admin.webapi.integration;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.json.JSONObject;
import static com.jayway.restassured.RestAssured.*;
import org.junit.*;
import com.jayway.restassured.RestAssured;
import kz.ecc.isbp.admin.auth.service.*;
import kz.ecc.isbp.admin.auth.service.impl.*;
import kz.ecc.isbp.admin.auth.repository.impl.*;
import kz.ecc.isbp.admin.auth.entity.*;
import kz.ecc.isbp.admin.auth.webapi.UserResourceImpl;
import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.common.repository.*;

import kz.ecc.isbp.admin.fnd.entity.*;
import kz.ecc.isbp.admin.fnd.repository.impl.*;
import kz.ecc.isbp.admin.fnd.service.*;
import kz.ecc.isbp.admin.fnd.service.impl.*;
import kz.ecc.isbp.admin.helper.*;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.repository.impl.OrgStructRepositoryImpl;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;
import kz.ecc.isbp.admin.nsi.service.impl.OrgStructServiceImpl;
import kz.ecc.isbp.admin.webapi.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class AuthUserResourceTest  extends AbstractResourceTest {
	private static DBUnitHelper dbUnitHelper;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/users/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        dbUnitHelper = new DBUnitHelper(); 
        PermissionRepositoryImpl permissionRepository = new PermissionRepositoryImpl();
		permissionRepository.setEntityManager(dbUnitHelper.getEntityManager());

        RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();
		roleRepository.setEntityManager(dbUnitHelper.getEntityManager());

		UserRepositoryImpl userRepository = new UserRepositoryImpl();
		userRepository.setEntityManager(dbUnitHelper.getEntityManager());

		ModuleRepositoryImpl moduleRepository = new ModuleRepositoryImpl();
		moduleRepository.setEntityManager(dbUnitHelper.getEntityManager());

		OrgStructRepositoryImpl departmentRepository = new OrgStructRepositoryImpl();
		departmentRepository.setEntityManager(dbUnitHelper.getEntityManager());
		
		DictRepositoryImpl dictRepository = new DictRepositoryImpl();
		dictRepository.setEntityManager(dbUnitHelper.getEntityManager());

		RoleModuleRepositoryImpl roleModuleRepository = new RoleModuleRepositoryImpl();
		roleModuleRepository.setEntityManager(dbUnitHelper.getEntityManager());

		RoleModuleDictRepositoryImpl roleModuleDictRepository = new RoleModuleDictRepositoryImpl();
		roleModuleDictRepository.setEntityManager(dbUnitHelper.getEntityManager());
		
        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(PermissionServiceImpl.class).to(PermissionService.class);
				bind(RoleServiceImpl.class).to(RoleService.class);
				bind(UserServiceImpl.class).to(UserService.class);
				bind(ModuleServiceImpl.class).to(ModuleService.class);
				bind(OrgStructServiceImpl.class).to(OrgStructService.class);
				bind(DictServiceImpl.class).to(DictService.class);
				
				bind(permissionRepository).to(new TypeLiteral<Repository<Permission>>() {});
				bind(roleRepository).to(new TypeLiteral<Repository<Role>>() {});
				bind(userRepository).to(new TypeLiteral<Repository<User>>() {});				
				bind(moduleRepository).to(new TypeLiteral<Repository<Module>>() {});
				bind(departmentRepository).to(new TypeLiteral<Repository<OrgStruct>>() {});
				bind(dictRepository).to(new TypeLiteral<Repository<Dict>>() {});
				bind(roleModuleRepository).to(new TypeLiteral<Repository<RoleModule>>() {});
				bind(roleModuleDictRepository).to(new TypeLiteral<Repository<RoleModuleDict>>() {});
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
				
				bind(DefaultQueryImpl.builder()).to(QueryBuilder.class);
			}
        }); 
		
        setResource(new UserResourceImpl());
        start(Binding.Manual);        

	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
		stop();
	}
	
	
	@Before
	public void setUp() throws Exception {
		dbUnitHelper.beginTransaction();
		
		dbUnitHelper.delete(Arrays.asList(
			new DataSetLoader("auth", "auth_users.xml"),
			new DataSetLoader("nsi", "nsi_dict_org_structs.xml")
		));
		
		dbUnitHelper.insert(Arrays.asList(
			new DataSetLoader("nsi", "nsi_dict_org_structs.xml"),
			new DataSetLoader("auth", "auth_users.xml")
		));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	@Test
	public void theListUsersMayBeFound() throws Exception {
		given().
		//log().all().
		accept("application/json;charset=utf-8").
		contentType("application/json;charset=utf-8").
	when().
		get("?departmentId=1").
	then().
		//log().all().
		contentType(ContentType.fromContentType("application/json;charset=utf-8")).
		and().statusCode(200).
		body("[0].id", is(not(nullValue()))).
		body("[0].iin", is(not(nullValue()))).
		body("[0].bin", is(not(nullValue()))).
		body("[0].surname", is(not(nullValue()))).
		body("[0].name", is(not(nullValue()))).
		body("[0].email", is(not(nullValue()))).
		body("[0].orgStructId", is(not(nullValue()))).
		body("[0].orgStructNameRu", is(not(nullValue()))).
		body("[0].orgStructNameKz", is(not(nullValue())));
	}
	
	
	@Test
	public void existingUserMayBeFoundById() throws Exception {
		Integer testedUserId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedUserId.toString()).
		then().
			//log().all().
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			and().statusCode(200).
			body("id", equalTo(testedUserId)).
			body("iin", is(not(nullValue()))).
			body("bin", is(not(nullValue()))).
			body("surname", is(not(nullValue()))).
			body("name", is(not(nullValue()))).
			body("email", is(not(nullValue()))).
			body("orgStructId", is(not(nullValue()))).
			body("orgStructNameRu", is(not(nullValue()))).
			body("orgStructNameKz", is(not(nullValue())));
	}

	
	@Test
	public void newUserMayBeCreated() throws Exception {
		User newUser = newUser(null);
		JSONObject body = new JSONObject();
		body.put("iin", newUser.getIin());
		body.put("bin", newUser.getBin());
		body.put("surname", newUser.getSurname());
		body.put("patronymic", newUser.getPatronymic());
		body.put("name", newUser.getName());
		body.put("email", newUser.getEmail());
		body.put("phoneNumber", newUser.getPhoneNumber());
		body.put("orgStructId", newUser.getOrgStruct().getId());
		body.put("orgStructNameRu", newUser.getOrgStruct().getNameRu());
		body.put("orgStructNameKz", newUser.getOrgStruct().getNameKz());
		body.put("isDirector", newUser.getIsDirector() ? "Y" : "N" );
		body.put("isDisabled", newUser.getIsDisabled() ? "Y" : "N" );
		body.put("isArchive", newUser.getIsArchive() ? "Y" : "N" );
				
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
			body(body.toString()).
		when().
			post("/").
		then().
			//log().all().
			statusCode(201).
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			body("id", is(not(nullValue()))).
			body("iin", is(not(nullValue()))).
			body("bin", is(not(nullValue()))).
			body("surname", is(not(nullValue()))).
			body("name", is(not(nullValue()))).
			body("email", is(not(nullValue()))).
			body("orgStructId", is(not(nullValue()))).
			body("orgStructNameRu", is(not(nullValue()))).
			body("orgStructNameKz", is(not(nullValue())));			
	}
	
	
	@Test
	public void existingUserMayBeUpdated() throws Exception {
		Integer testedUserId = 1;
		User newUser = newUser(new Long(testedUserId));
		newUser.setSurname(newUser.getSurname() + " (нов)");

		JSONObject body = new JSONObject()
			.put("id", newUser.getId())
			.put("iin", newUser.getIin())
			.put("bin", newUser.getBin())
			.put("surname", newUser.getSurname())
			.put("patronymic", newUser.getPatronymic())
			.put("name", newUser.getName())
			.put("email", newUser.getEmail())
			.put("phoneNumber", newUser.getPhoneNumber())
			.put("orgStructId", newUser.getOrgStruct().getId())
			.put("orgStructNameRu", newUser.getOrgStruct().getNameRu())
			.put("orgStructNameKz", newUser.getOrgStruct().getNameKz())
			.put("isDirector", newUser.getIsDirector() ? "Y" : "N" )
			.put("isDisabled", newUser.getIsDisabled() ? "Y" : "N" )
			.put("isArchive", newUser.getIsArchive() ? "Y" : "N" );
				
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
			body(body.toString()).
		when().
			put("/" + testedUserId.toString()).
		then().
			//log().all().
			statusCode(200).
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			body("id", is(not(nullValue()))).
			body("id", equalTo(testedUserId)).
			body("iin", is(not(nullValue()))).
			body("bin", is(not(nullValue()))).
			body("surname", is(not(nullValue()))).
			body("surname", equalTo(newUser.getSurname())).
			body("name", is(not(nullValue()))).
			body("email", is(not(nullValue()))).
			body("orgStructId", is(not(nullValue()))).
			body("orgStructNameRu", is(not(nullValue()))).
			body("orgStructNameKz", is(not(nullValue())));	
	}
	
	
	@Test
	public void existingUserMayBeDeleted() throws Exception {
		Integer testedUserId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
		when().
			delete(testedUserId.toString()).
		then().
			//log().all().
			and().statusCode(204);	
	}
	
}