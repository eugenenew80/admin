package kz.ecc.isbp.admin.webapi.unit;

import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import com.jayway.restassured.http.ContentType;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import static com.jayway.restassured.RestAssured.*;
import org.junit.*;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.RestAssured;
import kz.ecc.isbp.admin.auth.service.PermissionService;
import kz.ecc.isbp.admin.auth.webapi.PermissionResourceImpl;
import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthPermissionResourceUnitTest extends AbstractResourceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/permissions/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
		PermissionService mockPermissionService = mock(PermissionService.class);
		when(mockPermissionService.findAll()).thenReturn(Arrays.asList(newPermission(1L), newPermission(2L), newPermission(3L) ));
		when(mockPermissionService.findById(1L)).thenReturn(newPermission(1L));
		when(mockPermissionService.create(anyObject())).thenReturn(newPermission(1L));
		when(mockPermissionService.update(anyObject())).then(returnsFirstArg());
		when(mockPermissionService.delete(anyObject())).thenReturn(true);

		setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockPermissionService).to(PermissionService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
		});

		setResource(new PermissionResourceImpl());
		start(Binding.Manual);
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	
	
	//Success cases
	
	@Test
	public void theListPermissionsMayBeFound() {
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
	public void existingPermissionMayBeFoundById() {
		Integer testedPermissionId = 1;
			
		given().
			//log().all().
			accept("application/json; charset=utf-8").
			contentType("application/json; charset=utf-8").
		when().
			get(testedPermissionId.toString()).
		then().
			//log().all().
            contentType(ContentType.JSON).
			and().statusCode(200).
			body("id", equalTo(testedPermissionId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
}