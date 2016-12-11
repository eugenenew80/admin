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
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.repository.impl.OrgStructRepositoryImpl;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;
import kz.ecc.isbp.admin.nsi.service.impl.OrgStructServiceImpl;
import kz.ecc.isbp.admin.nsi.webapi.OrgStructResourceImpl;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;


public class OrgStructResourceTest extends AbstractResourceTest {
	private static DBUnitHelper dbUnitHelper;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/orgStructs/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        dbUnitHelper = new DBUnitHelper();   
        OrgStructRepositoryImpl orgStructRepository = new OrgStructRepositoryImpl();
		orgStructRepository.setEntityManager(dbUnitHelper.getEntityManager());

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(orgStructRepository).to(new TypeLiteral<Repository<OrgStruct>>() {});
				bind(OrgStructServiceImpl.class).to(OrgStructService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new OrgStructResourceImpl());
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
			new DataSetLoader("auth", "auth_users.xml"),
			new DataSetLoader("nsi", "nsi_dict_org_structs.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("nsi", "nsi_dict_org_structs.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	@Test
	public void theListOrgStructsMayBeFound() throws Exception {
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
	public void existingOrgStructMayBeFoundById() throws Exception {
		Integer testedOrgStructId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedOrgStructId.toString()).
		then().
			//log().all().
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			and().statusCode(200).
			body("id", equalTo(testedOrgStructId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue())));
	}
	
}