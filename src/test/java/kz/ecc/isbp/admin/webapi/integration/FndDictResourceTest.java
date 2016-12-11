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
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.repository.impl.DictRepositoryImpl;
import kz.ecc.isbp.admin.fnd.service.DictService;
import kz.ecc.isbp.admin.fnd.service.impl.DictServiceImpl;
import kz.ecc.isbp.admin.fnd.webapi.DictResourceImpl;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;

public class FndDictResourceTest  extends AbstractResourceTest {
	private static DBUnitHelper dbUnitHelper;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/dicts/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        dbUnitHelper = new DBUnitHelper();  
        DictRepositoryImpl dictRepository = new DictRepositoryImpl();
		dictRepository.setEntityManager(dbUnitHelper.getEntityManager());

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(dictRepository).to(new TypeLiteral<Repository<Dict>>() {});
				bind(DictServiceImpl.class).to(DictService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new DictResourceImpl());
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
		dbUnitHelper.delete( Arrays.asList(new DataSetLoader("fnd", "fnd_dicts.xml")));
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("fnd", "fnd_dicts.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	

	//Success cases 
	
	@Test
	public void theListDictsMayBeFound() throws Exception {
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
		body("[0].code", is(not(nullValue()))).
		body("[0].type", is(not(nullValue())));
	}
	
	
	@Test
	public void existingDictMayBeFoundById() throws Exception {
		Integer testedDictId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedDictId.toString()).
		then().
			//log().all().
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			and().statusCode(200).
			body("id", equalTo(testedDictId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
}