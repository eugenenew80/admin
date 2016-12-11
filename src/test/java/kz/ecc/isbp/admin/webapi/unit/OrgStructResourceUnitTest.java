package kz.ecc.isbp.admin.webapi.unit;

import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;

import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import static com.jayway.restassured.RestAssured.*;
import org.junit.*;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.RestAssured;

import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;
import kz.ecc.isbp.admin.nsi.webapi.OrgStructResourceImpl;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrgStructResourceUnitTest extends AbstractResourceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/orgStructs/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
		OrgStructService mockOrgStructService = mock(OrgStructService.class);
		when(mockOrgStructService.findAll()).thenReturn(Arrays.asList(newOrgStruct(1L), newOrgStruct(2L), newOrgStruct(3L) ));
		when(mockOrgStructService.findById(1L)).thenReturn(newOrgStruct(1L));
		when(mockOrgStructService.create(anyObject())).thenReturn(newOrgStruct(1L));
		when(mockOrgStructService.update(anyObject())).then(returnsFirstArg());
		when(mockOrgStructService.delete(anyObject())).thenReturn(true);

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockOrgStructService).to(OrgStructService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new OrgStructResourceImpl());
        start(Binding.Manual);     
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	
	
	//Success cases 
	
	@Test
	public void theListOrgStructsMayBeFound() {
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
		body("[0].nameRu", is(not(nullValue()))).
		body("[0].nameKz", is(not(nullValue())));
	}
	
	
	@Test
	public void existingOrgStructMayBeFoundById() {
		Integer testedOrgStructId = 1;
			
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedOrgStructId.toString()).
		then().
			//log().all().
			contentType(ContentType.JSON).
			and().statusCode(200).
			body("id", equalTo(testedOrgStructId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue())));
	}
}