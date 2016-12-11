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
import kz.ecc.isbp.admin.helper.EntitiesHelper;
import kz.ecc.isbp.admin.nsi.entity.BudgetVerStatus;
import kz.ecc.isbp.admin.nsi.repository.impl.BudgetVerStatusRepositoryImpl;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class BudgetVerStatusRepositoryTest {
	private static BudgetVerStatusRepositoryImpl budgetVerStatusRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		budgetVerStatusRepository = new BudgetVerStatusRepositoryImpl(dbUnitHelper.getEntityManager());
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
			new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml")) );
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}


	//Testing success cases
	
	@Test
	public void theListBudgetVerStatussMayBeSelected() throws Exception {	
		List<BudgetVerStatus> budgetVerStatuss = budgetVerStatusRepository.selectAll();
		assertThat(budgetVerStatuss, is(not(empty())));
	}
	
	
	@Test
	public void existingBudgetVerStatusMayBeSelectedById() throws Exception {
		BudgetVerStatus findBudgetVerStatus = budgetVerStatusRepository.selectById(1L);
		assertBudgetVerStatus(findBudgetVerStatus);
	}
	
	
	@Test
	public void existingBudgetVerStatusMayBeSelectedByName() throws Exception {
		BudgetVerStatus findBudgetVerStatus = budgetVerStatusRepository.selectByName(EntitiesHelper.BUDGET_VER_STATUS_NAME_RU);
		assertBudgetVerStatus(findBudgetVerStatus);
	}
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertBudgetVerStatus() {		
		budgetVerStatusRepository.insert(newBudgetVerStatus(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetVerStatus() {
		budgetVerStatusRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetVerStatus() {
		budgetVerStatusRepository.insert(newBudgetVerStatus(1L));
	}
}
