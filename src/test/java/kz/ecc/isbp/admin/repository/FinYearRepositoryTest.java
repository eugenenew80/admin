package kz.ecc.isbp.admin.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.nsi.entity.FinYear;
import kz.ecc.isbp.admin.nsi.repository.impl.FinYearRepositoryImpl;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class FinYearRepositoryTest {
	private static FinYearRepositoryImpl finYearRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		finYearRepository = new FinYearRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		dbUnitHelper.delete( Arrays.asList(
			new DataSetLoader("nsi", "nsi_dict_budget_vers.xml"),
			new DataSetLoader("nsi", "nsi_dict_fin_years.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("nsi", "nsi_dict_fin_years.xml")) );
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}


	//Testing success cases
	
	@Test
	public void theListFinYearsMayBeSelected() throws Exception {	
		List<FinYear> finYears = finYearRepository.selectAll();
		assertThat(finYears, is(not(empty())));
	}
	
	
	@Test
	public void existingFinYearMayBeSelectedById() throws Exception {
		FinYear findFinYear = finYearRepository.selectById(1L);
		assertFinYear(findFinYear);
	}

	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertFinYear() {		
		finYearRepository.insert(newFinYear(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteFinYear() {
		finYearRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateFinYear() {
		finYearRepository.insert(newFinYear(1L));
	}
}
