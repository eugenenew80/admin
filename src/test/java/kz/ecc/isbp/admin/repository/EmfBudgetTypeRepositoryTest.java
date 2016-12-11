package kz.ecc.isbp.admin.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import kz.ecc.isbp.admin.helper.EntitiesHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kz.ecc.isbp.admin.emf.entity.BudgetType;
import kz.ecc.isbp.admin.emf.repository.impl.BudgetTypeRepositoryImpl;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class EmfBudgetTypeRepositoryTest {
	private static BudgetTypeRepositoryImpl budgetTypeRepository;	
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		budgetTypeRepository = new BudgetTypeRepositoryImpl(dbUnitHelper.getEntityManager());
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
			new DataSetLoader("emf", "emf_budget_types.xml")
		));
		
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("emf", "emf_budget_types.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}

	
	//Testing success cases
	
	@Test
	public void theListBudgetTypesMayBeSelected() throws Exception {	
		List<BudgetType> budgetTypes = budgetTypeRepository.selectAll();
		assertThat(budgetTypes, is(not(empty())));
	}
	
	
	@Test
	public void existingBudgetTypeMayBeSelectedById() throws Exception {
		BudgetType findBudgetType = budgetTypeRepository.selectById(1L);
		assertBudgetType(findBudgetType);
	}
	
	
	@Test
	public void existingBudgetTypeMayBeSelectedByName() throws Exception {
		BudgetType findBudgetType = budgetTypeRepository.selectByName(EntitiesHelper.BUDGET_TYPE_NAME_RU);
		assertBudgetType(findBudgetType);
	}

	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertBudgetType() {		
		budgetTypeRepository.insert(newBudgetType(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetType() {
		budgetTypeRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetLevel() {
		budgetTypeRepository.insert(newBudgetType(1L));
	}
}
