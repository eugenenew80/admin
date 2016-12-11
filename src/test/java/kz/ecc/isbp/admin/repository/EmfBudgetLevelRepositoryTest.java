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

import kz.ecc.isbp.admin.emf.entity.BudgetLevel;
import kz.ecc.isbp.admin.emf.repository.impl.BudgetLevelRepositoryImpl;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.helper.EntitiesHelper;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class EmfBudgetLevelRepositoryTest {
	private static BudgetLevelRepositoryImpl budgetlevelRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		budgetlevelRepository = new BudgetLevelRepositoryImpl(dbUnitHelper.getEntityManager());
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
			new DataSetLoader("emf", "emf_budget_levels.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("emf", "emf_budget_levels.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}


	//Testing success cases
	
	@Test
	public void theListBudgetLevelsMayBeSelected() throws Exception {	
		List<BudgetLevel> budgetlevels = budgetlevelRepository.selectAll();
		assertThat( budgetlevels, is(not(empty())) );
	}
	
	
	@Test
	public void existingBudgetLevelMayBeSelectedById() throws Exception {
		BudgetLevel findBudgetLevel = budgetlevelRepository.selectById(1L);
		assertBudgetLevel(findBudgetLevel);
	}
	
	
	@Test
	public void existingBudgetLevelMayBeSelectedByName() throws Exception {
		BudgetLevel findBudgetLevel = budgetlevelRepository.selectByName(EntitiesHelper.BUDGET_LEVEL_NAME_RU);
		assertBudgetLevel(findBudgetLevel);
	}
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertBudgetLevel() {		
		budgetlevelRepository.insert(newBudgetLevel(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetLevel() {
		budgetlevelRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetLevel() {
		budgetlevelRepository.insert(newBudgetLevel(1L));
	}
}
