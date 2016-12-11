package kz.ecc.isbp.admin.webapi.unit;

import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.json.JSONObject;
import static com.jayway.restassured.RestAssured.*;
import org.junit.*;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.RestAssured;

import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.auth.service.*;
import kz.ecc.isbp.admin.auth.webapi.UserResourceImpl;
import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.common.repository.DefaultQueryImpl;
import kz.ecc.isbp.admin.common.repository.QueryBuilder;
import kz.ecc.isbp.admin.fnd.service.*;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;
import kz.ecc.isbp.admin.webapi.*;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthUserResourceUnitTest  extends AbstractResourceTest {

	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/users/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
		UserService mockUserService = mock(UserService.class);
        when(mockUserService.findAll()).thenReturn(Arrays.asList(newUser(1L), newUser(2L), newUser(3L) ));
        when(mockUserService.find(anyObject())).thenReturn(Arrays.asList(newUser(1L), newUser(2L), newUser(3L) ));
        
        when(mockUserService.findById(1L)).thenReturn(newUser(1L));
        when(mockUserService.findById(4L)).thenReturn(newUser(4L));
        
		when(mockUserService.create(anyObject())).thenReturn(newUser(1L));
		when(mockUserService.update(anyObject())).then(returnsFirstArg());
		when(mockUserService.delete(anyObject())).thenReturn(true);
		
		ModuleService mockModuleService = mock(ModuleService.class);
		when(mockModuleService.findAll()).thenReturn(Arrays.asList(newModule(1L), newModule(2L), newModule(3L) ));
		when(mockModuleService.findById(1L)).thenReturn(newModule(1L));
		when(mockModuleService.create(anyObject())).thenReturn(newModule(1L));
		when(mockModuleService.update(anyObject())).then(returnsFirstArg());
		when(mockModuleService.delete(anyObject())).thenReturn(true);
		
		RoleService mockRoleService = mock(RoleService.class);
		when(mockRoleService.findAll()).thenReturn(Arrays.asList(newRole(1L), newRole(2L), newRole(3L) ));
		when(mockRoleService.findById(1L)).thenReturn(newRole(1L));
		when(mockRoleService.create(anyObject())).thenReturn(newRole(1L));
		when(mockRoleService.update(anyObject())).then(returnsFirstArg());
		when(mockRoleService.delete(anyObject())).thenReturn(true);
		
		DictService mockDictService = mock(DictService.class);
		when(mockDictService.findAll()).thenReturn(Arrays.asList(newDict(1L), newDict(2L), newDict(3L) ));
		when(mockDictService.findById(1L)).thenReturn(newDict(1L));
		when(mockDictService.create(anyObject())).thenReturn(newDict(1L));
		when(mockDictService.update(anyObject())).then(returnsFirstArg());
		when(mockDictService.delete(anyObject())).thenReturn(true);
		
		OrgStructService mockOrgStructService = mock(OrgStructService.class);
		when(mockOrgStructService.findAll()).thenReturn(Arrays.asList(newOrgStruct(1L), newOrgStruct(2L), newOrgStruct(3L) ));
		when(mockOrgStructService.findById(1L)).thenReturn(newOrgStruct(1L));
		when(mockOrgStructService.create(anyObject())).thenReturn(newOrgStruct(1L));
		when(mockOrgStructService.update(anyObject())).then(returnsFirstArg());
		when(mockOrgStructService.delete(anyObject())).thenReturn(true);
		
        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockUserService).to(UserService.class);
				bind(mockModuleService).to(ModuleService.class);
				bind(mockRoleService).to(RoleService.class);
				bind(mockDictService).to(DictService.class);
				bind(mockOrgStructService).to(OrgStructService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
				bind(DefaultQueryImpl.builder()).to(QueryBuilder.class);
			}
        }); 
		
        setResource(new UserResourceImpl());
        start(Binding.Manual);
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	

	//Success cases
	
	
	@Test
	public void theListUsersMayBeFound() {
		given().
		//log().all().
		accept("application/json;charset=utf-8").
		contentType("application/json;charset=utf-8").
	when().
		get().
	then().
		//log().all().
		contentType(ContentType.JSON).
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
	public void existingUserMayBeFoundById() {
		Integer testedUserId = 1;
			
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedUserId.toString()).
		then().
			//log().all().
			contentType(ContentType.JSON).
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
			contentType(ContentType.JSON).
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
		Integer testedUserId = 4;
		User newUser = newUser(new Long(testedUserId));
		newUser.setSurname(newUser.getSurname() + " (нов)");

		JSONObject body = new JSONObject();
		body.put("id", newUser.getId());
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
			put("/" + testedUserId.toString()).
		then().
			//log().all().
			statusCode(200).
			contentType(ContentType.JSON).
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
	public void existingUserMayBeDeleted() {
		Integer testedUserId = 4;
		
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			delete(testedUserId.toString()).
		then().
			//log().all().
			and().statusCode(204);	
	}
	
}