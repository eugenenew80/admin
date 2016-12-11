package kz.ecc.isbp.admin.webapi.integration;

import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.*;
import com.jayway.restassured.RestAssured;

import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.repository.impl.BudgetVerRepositoryImpl;
import kz.ecc.isbp.admin.nsi.service.BudgetVerService;
import kz.ecc.isbp.admin.nsi.service.impl.BudgetVerServiceImpl;
import kz.ecc.isbp.admin.nsi.webapi.BudgetVerResourceImpl;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;


public class BudgetVerResourceTest extends AbstractResourceTest { 
	private static DBUnitHelper dbUnitHelper;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/budgetVers/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        dbUnitHelper = new DBUnitHelper();
        BudgetVerRepositoryImpl budgetVerRepository = new BudgetVerRepositoryImpl(dbUnitHelper.getEntityManager());

        setBinder(new AbstractBinder() {
			protected void configure() {
	            bind(budgetVerRepository).to(new TypeLiteral<Repository<BudgetVer>>() {});
	            bind(BudgetVerServiceImpl.class).to(BudgetVerService.class);
	            bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new BudgetVerResourceImpl());
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

		dbUnitHelper.delete( Arrays.asList(
			new DataSetLoader("nsi", "nsi_dict_budget_vers.xml"),
			new DataSetLoader("emf", "emf_budget_types.xml"),
			new DataSetLoader("emf", "emf_budget_levels.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml"),
			new DataSetLoader("nsi", "nsi_dict_fin_years.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_request_types.xml")
		));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	@Test
	public void theListBudgetVersMayBeFound() throws Exception {
		dbUnitHelper.insert( Arrays.asList(
			new DataSetLoader("emf", "emf_budget_types.xml"),
			new DataSetLoader("emf", "emf_budget_levels.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml"),
			new DataSetLoader("nsi", "nsi_dict_fin_years.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_request_types.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_vers.xml")
		));
		
		given().
		//log().all().
		accept("application/json;charset=utf-8").
		contentType("application/json;charset=utf-8").
	when().
		get().
	then().
		//log().all().
		contentType(ContentType.fromContentType("application/json;charset=utf-8")).
		and().statusCode(200).
		body("[0].id", is(not(nullValue()))).
		body("[0].nameRu", is(not(nullValue()))).
		body("[0].nameKz", is(not(nullValue())));
	}
	
	
	@Test
	public void existingBudgetVerMayBeFoundById() throws Exception {
		dbUnitHelper.insert( Arrays.asList(
			new DataSetLoader("emf", "emf_budget_types.xml"),
			new DataSetLoader("emf", "emf_budget_levels.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml"),
			new DataSetLoader("nsi", "nsi_dict_fin_years.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_request_types.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_vers.xml")
		));
		
		Integer testedBudgetVerId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedBudgetVerId.toString()).
		then().
			//log().all().
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			and().statusCode(200).
			body("id", equalTo(testedBudgetVerId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue())));
	}
}