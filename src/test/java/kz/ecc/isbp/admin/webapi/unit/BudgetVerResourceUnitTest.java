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
import kz.ecc.isbp.admin.emf.service.BudgetTypeService;
import kz.ecc.isbp.admin.nsi.service.BudgetRequestTypeService;
import kz.ecc.isbp.admin.nsi.service.BudgetVerService;
import kz.ecc.isbp.admin.nsi.service.BudgetVerStatusService;
import kz.ecc.isbp.admin.nsi.service.FinYearService;
import kz.ecc.isbp.admin.nsi.webapi.BudgetVerResourceImpl;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BudgetVerResourceUnitTest extends AbstractResourceTest { 
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/budgetVers/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        BudgetVerService mockBudgetVerService = mock(BudgetVerService.class);
		when(mockBudgetVerService.findAll()).thenReturn(Arrays.asList(newBudgetVer(1L), newBudgetVer(2L), newBudgetVer(3L) ));
		when(mockBudgetVerService.findById(1L)).thenReturn(newBudgetVer(1L));
		when(mockBudgetVerService.create(anyObject())).thenReturn(newBudgetVer(1L));
		when(mockBudgetVerService.update(anyObject())).then(returnsFirstArg());
		when(mockBudgetVerService.delete(anyObject())).thenReturn(true);

		
		BudgetTypeService mockBudgetTypeService = mock(BudgetTypeService.class);
		BudgetVerStatusService mockBudgetVerStatusService = mock(BudgetVerStatusService.class);
		BudgetRequestTypeService mockBudgetRequestTypeService = mock(BudgetRequestTypeService.class);
		FinYearService mockFinYearService = mock(FinYearService.class);
		
        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(mockBudgetVerService).to(BudgetVerService.class);
				bind(mockBudgetTypeService).to(BudgetTypeService.class);
				bind(mockBudgetVerStatusService).to(BudgetVerStatusService.class);
				bind(mockBudgetRequestTypeService).to(BudgetRequestTypeService.class);
				bind(mockFinYearService).to(FinYearService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new BudgetVerResourceImpl());
        start(Binding.Manual);     
	}
	
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		stop();
	}
	

	@Test
	public void theListBudgetVersMayBeFound() {
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
	public void existingBudgetVerMayBeFoundById() {
		Integer testedBudgetVerId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedBudgetVerId.toString()).
		then().
			//log().all().
			contentType(ContentType.JSON).
			and().statusCode(200).
			body("id", equalTo(testedBudgetVerId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue())));
	}
}