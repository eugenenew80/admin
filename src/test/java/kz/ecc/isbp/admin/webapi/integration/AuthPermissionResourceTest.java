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
import kz.ecc.isbp.admin.auth.service.PermissionService;
import kz.ecc.isbp.admin.auth.service.impl.PermissionServiceImpl;
import kz.ecc.isbp.admin.auth.repository.impl.PermissionRepositoryImpl;
import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.auth.webapi.PermissionResourceImpl;
import kz.ecc.isbp.admin.common.dto.mapper.DozerProducer;
import kz.ecc.isbp.admin.common.repository.Repository;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.webapi.AbstractResourceTest;
import kz.ecc.isbp.admin.webapi.Binding;


public class AuthPermissionResourceTest extends AbstractResourceTest  {
	private static DBUnitHelper dbUnitHelper;

	
	@BeforeClass
	public static void setUpClass() throws Exception {
		RestAssured.baseURI = "http://localhost:2222";
        RestAssured.basePath = "/permissions/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        dbUnitHelper = new DBUnitHelper();       
        PermissionRepositoryImpl permissionRepository = new PermissionRepositoryImpl();
        permissionRepository.setEntityManager(dbUnitHelper.getEntityManager());
		

        setBinder(new AbstractBinder() {
			protected void configure() {
				bind(permissionRepository).to(new TypeLiteral<Repository<Permission>>() {});
				bind(PermissionServiceImpl.class).to(PermissionService.class);
				bind(new DozerProducer().getMapper()).to(DozerBeanMapper.class);
			}
        }); 
		
        setResource(new PermissionResourceImpl());
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
		dbUnitHelper.delete( Arrays.asList(new DataSetLoader("auth", "auth_permissions.xml")));
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("auth", "auth_permissions.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	@Test
	public void theListPermissionsMayBeFound() throws Exception {
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("auth", "auth_permissions.xml")));
		
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
	public void existingPermissionMayBeFoundById() throws Exception {
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("auth", "auth_permissions.xml")));
		
		Integer testedPermissionId = 1;
		given().
			//log().all().
			accept("application/json;charset=utf-8").
			contentType("application/json;charset=utf-8").
		when().
			get(testedPermissionId.toString()).
		then().
			//log().all().
			contentType(ContentType.fromContentType("application/json;charset=utf-8")).
			and().statusCode(200).
			body("id", equalTo(testedPermissionId)).
			body("nameRu", is(not(nullValue()))).
			body("nameKz", is(not(nullValue()))).
			body("code", is(not(nullValue())));
	}
}