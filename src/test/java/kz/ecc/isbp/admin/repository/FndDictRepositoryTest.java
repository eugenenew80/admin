package kz.ecc.isbp.admin.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import kz.ecc.isbp.admin.helper.EntitiesHelper;
import org.junit.*;

import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.repository.impl.DictRepositoryImpl;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class FndDictRepositoryTest  {
	private static DictRepositoryImpl dictRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		dictRepository =  new DictRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		dbUnitHelper.delete( Arrays.asList(
			new DataSetLoader("fnd", "fnd_dicts.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("fnd", "fnd_dicts.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	//Success cases
	
	
	@Test
	public void theListDictsMayBeSelected() throws Exception {	
		List<Dict> dicts = dictRepository.selectAll();
		assertThat(dicts, is(not(empty())));
	}
	
	
	@Test
	public void existingDictMayBeSelectedById() throws Exception {
		Dict findDict = dictRepository.selectById(1L);
		assertDict(findDict);
	}
	
	
	@Test
	public void existingDictMayBeSelectedByName() throws Exception {
		Dict findDict = dictRepository.selectByName(EntitiesHelper.DICT_NAME_RU);
		assertDict(findDict);
	}
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertDict() {		
		dictRepository.insert(newDict(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteDict() {
		dictRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateDict() {
		dictRepository.insert(newDict(1L));
	}
}
