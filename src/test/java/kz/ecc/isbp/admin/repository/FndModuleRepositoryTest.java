package kz.ecc.isbp.admin.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import kz.ecc.isbp.admin.helper.EntitiesHelper;
import org.junit.*;

import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.repository.impl.ModuleRepositoryImpl;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class FndModuleRepositoryTest {
	private static ModuleRepositoryImpl moduleRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		moduleRepository = new ModuleRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		dbUnitHelper.delete( Arrays.asList(
			new DataSetLoader("fnd", "fnd_modules.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("fnd", "fnd_modules.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	@Test
	public void theListModulesMayBeSelected() throws Exception {	
		List<Module> modules = moduleRepository.selectAll();
		assertThat(modules, is(not(empty())));
	}
	
	
	@Test
	public void existingModuleMayBeSelectedById() throws Exception {
		Module findModule = moduleRepository.selectById(1L);
		assertModule(findModule);
	}
	
	
	@Test
	public void existingModuleMayBeSelectedByName() throws Exception {
		Module findModule = moduleRepository.selectByName(EntitiesHelper.MODULE_NAME_RU);
		assertModule(findModule);
	}
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertModule() {		
		moduleRepository.insert(newModule(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteModule() {
		moduleRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateModule() {
		moduleRepository.insert(newModule(1L));
	}
}
