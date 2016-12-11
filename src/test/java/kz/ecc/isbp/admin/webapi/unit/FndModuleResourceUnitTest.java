package kz.ecc.isbp.admin.webapi.unit;

import static org.hamcrest.Matchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.*;
import org.junit.runners.MethodSorters;
import com.jayway.restassured.RestAssured;

import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.fnd.service.ModuleService;
import kz.ecc.isbp.admin.fnd.webapi.ModuleResourceImpl;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FndModuleResourceUnitTest extends AbstractResourceTest {
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/modules/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
		ModuleService mockModuleService = mock(ModuleService.class);
		when(mockModuleService.findAll()).thenReturn(Arrays.asList(newModule(1L), newModule(2L), newModule(3L) ));
		when(mockModuleService.findById(1L)).thenReturn(newModule(1L));
		when(mockModuleService.create(anyObject())).thenReturn(newModule(1L));
		when(mockModuleService.update(anyObject())).then(returnsFirstArg());
		when(mockModuleService.delete(anyObject())).thenReturn(true);

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockModuleService).to(ModuleService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new ModuleResourceImpl());
        start(Binding.Manual);     
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	
	
	//Success cases
	
	@Test
	public void theListModulesMayBeFound() {
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
		body("[0].nameKz", is(not(nullValue()))).
		body("[0].code", is(not(nullValue())));
	}
	
	
	@Test
	public void existingModuleMayBeFoundById() {
		Integer testedModuleId = 1;
			
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedModuleId.toString()).
		then().
			//log().all().
			contentType(ContentType.JSON).
			and().statusCode(200).
			body("id", equalTo(testedModuleId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
}