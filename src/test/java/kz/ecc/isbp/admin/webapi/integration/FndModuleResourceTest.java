package kz.ecc.isbp.admin.webapi.integration;

import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import com.jayway.restassured.http.ContentType;

import org.dozer.DozerBeanMapper;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import static com.jayway.restassured.RestAssured.*;
import org.junit.*;
import com.jayway.restassured.RestAssured;

import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.repository.impl.ModuleRepositoryImpl;
import kz.ecc.isbp.admin.fnd.service.ModuleService;
import kz.ecc.isbp.admin.fnd.service.impl.ModuleServiceImpl;
import kz.ecc.isbp.admin.fnd.webapi.ModuleResourceImpl;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;

public class FndModuleResourceTest extends AbstractResourceTest {
	private static DBUnitHelper dbUnitHelper;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/modules/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        dbUnitHelper = new DBUnitHelper();
        ModuleRepositoryImpl moduleRepository = new ModuleRepositoryImpl();
		moduleRepository.setEntityManager(dbUnitHelper.getEntityManager());

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(moduleRepository).to(new TypeLiteral<Repository<Module>>() {});
				bind(ModuleServiceImpl.class).to(ModuleService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new ModuleResourceImpl());
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
		dbUnitHelper.delete( Arrays.asList(new DataSetLoader("fnd", "fnd_modules.xml")));
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("fnd", "fnd_modules.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	
	@Test
	public void theListModuleMayBeFound() throws Exception {
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
		body("[0].nameKz", is(not(nullValue()))).
		body("[0].code", is(not(nullValue())));
	}
	
	
	@Test
	public void existingModuleMayBeFoundById() throws Exception {
		Integer testedModuleId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedModuleId.toString()).
		then().
			//log().all().
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			and().statusCode(200).
			body("id", equalTo(testedModuleId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
	
}	
