package kz.ecc.isbp.admin.webapi.unit;

import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import static com.jayway.restassured.RestAssured.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.*;
import org.junit.runners.MethodSorters;
import kz.ecc.isbp.admin.auth.service.PermissionService;
import kz.ecc.isbp.admin.auth.service.RoleService;
import kz.ecc.isbp.admin.auth.webapi.RoleResourceImpl;
import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthRoleResourceUnitTest extends AbstractResourceTest {
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/roles/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
		RoleService mockRoleService = mock(RoleService.class);
		when(mockRoleService.findAll()).thenReturn(Arrays.asList(newRole(1L), newRole(2L), newRole(3L) ));
		when(mockRoleService.findById(1L)).thenReturn(newRole(1L));
		when(mockRoleService.create(anyObject())).thenReturn(newRole(1L));
		when(mockRoleService.update(anyObject())).then(returnsFirstArg());
		when(mockRoleService.delete(anyObject())).thenReturn(true);
		
		PermissionService mockPermissionService = mock(PermissionService.class);
		when(mockPermissionService.findAll()).thenReturn(Arrays.asList(newPermission(1L), newPermission(2L), newPermission(3L) ));
		when(mockPermissionService.findById(1L)).thenReturn(newPermission(1L));
		when(mockPermissionService.create(anyObject())).thenReturn(newPermission(1L));
		when(mockPermissionService.update(anyObject())).then(returnsFirstArg());
		when(mockPermissionService.delete(anyObject())).thenReturn(true);

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockRoleService).to(RoleService.class);
				bind(mockPermissionService).to(PermissionService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new RoleResourceImpl());
        start(Binding.Manual);     
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	
	
	@Test
	public void theListRolesMayBeFound() {
		given().
		//log().all().
		accept("application/json; charset=utf-8").
		contentType("application/json; charset=utf-8").
	when().
		get().
	then().
		//log().all().
		contentType(ContentType.JSON).
		and().statusCode(200).
		body("[0].id", is(not(nullValue()))).
		body("[0].nameRu", is(not(nullValue()))).
		body("[0].nameKz", is(not(nullValue()))).
		body("[0].code", is(not(nullValue())));
	}
	
	
	@Test
	public void existingRoleMayBeFoundById() {
		Integer testedRoleId = 1;
			
		given().
			//log().all().
			accept("application/json; charset=utf-8").
			contentType("application/json; charset=utf-8").
		when().
			get(testedRoleId.toString()).
		then().
			//log().all().
			contentType(ContentType.JSON).
			and().statusCode(200).
			body("id", equalTo(testedRoleId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
}