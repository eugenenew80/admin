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
import kz.ecc.isbp.admin.fnd.service.DictService;
import kz.ecc.isbp.admin.fnd.webapi.DictResourceImpl;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FndDictResourceUnitTest  extends AbstractResourceTest {
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/dicts/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
		DictService mockDictService = mock(DictService.class);
		when(mockDictService.findAll()).thenReturn(Arrays.asList(newDict(1L), newDict(2L), newDict(3L) ));
		when(mockDictService.findById(1L)).thenReturn(newDict(1L));
		when(mockDictService.create(anyObject())).thenReturn(newDict(1L));
		when(mockDictService.update(anyObject())).then(returnsFirstArg());
		when(mockDictService.delete(anyObject())).thenReturn(true);

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockDictService).to(DictService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new DictResourceImpl());
        start(Binding.Manual);     
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	
	
	//Success cases
	
	@Test
	public void theListDictsMayBeFound() {
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
		body("[0].code", is(not(nullValue()))).
		body("[0].type", is(not(nullValue())));
	}
	
	
	@Test
	public void existingDictMayBeFoundById() {
		Integer testedDictId = 1;
			
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedDictId.toString()).
		then().
			//log().all().
			contentType(ContentType.JSON).
			and().statusCode(200).
			body("id", equalTo(testedDictId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
}