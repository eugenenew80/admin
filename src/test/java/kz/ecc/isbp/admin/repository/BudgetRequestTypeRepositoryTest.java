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
import kz.ecc.isbp.admin.nsi.entity.BudgetRequestType;
import kz.ecc.isbp.admin.nsi.repository.impl.BudgetRequestTypeRepositoryImpl;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class BudgetRequestTypeRepositoryTest {
	private static BudgetRequestTypeRepositoryImpl budgetRequestTypeRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		budgetRequestTypeRepository = new BudgetRequestTypeRepositoryImpl(dbUnitHelper.getEntityManager());
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
	public void theListBudgetRequestTypesMayBeSelected() throws Exception {	
		List<BudgetRequestType> budgetRequestTypes = budgetRequestTypeRepository.selectAll();
		assertThat(budgetRequestTypes, is(not(empty())));
	}
	
	
	@Test
	public void existingBudgetRequestTypeMayBeSelectedById() throws Exception {
		BudgetRequestType findBudgetRequestType = budgetRequestTypeRepository.selectById(1L);
		assertBudgetRequestType(findBudgetRequestType);
	}
	
	
	@Test
	public void existingBudgetRequestTypeMayBeSelectedByName() throws Exception {
		BudgetRequestType findBudgetRequestType = budgetRequestTypeRepository.selectByName(EntitiesHelper.BUDGET_REQUEST_TYPE_NAME_RU);
		assertBudgetRequestType(findBudgetRequestType);
	}
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertBudgetRequestType() {		
		budgetRequestTypeRepository.insert(newBudgetRequestType(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetRequestType() {
		budgetRequestTypeRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetRequestType() {
		budgetRequestTypeRepository.insert(newBudgetRequestType(1L));
	}
}
