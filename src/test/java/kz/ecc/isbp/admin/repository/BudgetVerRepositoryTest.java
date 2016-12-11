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
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.repository.impl.BudgetVerRepositoryImpl;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class BudgetVerRepositoryTest {
	private static BudgetVerRepositoryImpl budgetVerRepository;
	private static DBUnitHelper dbUnitHelper;
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		budgetVerRepository = new BudgetVerRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		
		dbUnitHelper.delete(Arrays.asList(
			new DataSetLoader("nsi", "nsi_dict_budget_vers.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_request_types.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml"),
			new DataSetLoader("emf", "emf_budget_levels.xml"),
			new DataSetLoader("emf", "emf_budget_types.xml"),
			new DataSetLoader("nsi", "nsi_dict_fin_years.xml")
		));
				
		dbUnitHelper.insert(Arrays.asList(
			new DataSetLoader("nsi", "nsi_dict_fin_years.xml"),
			new DataSetLoader("emf", "emf_budget_types.xml"),
			new DataSetLoader("emf", "emf_budget_levels.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_ver_statuses.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_request_types.xml"),
			new DataSetLoader("nsi", "nsi_dict_budget_vers.xml")
		));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}

	
	//Success cases
	
	@Test
	public void theListBudgetVersMayBeSelected() throws Exception {	
		List<BudgetVer> budgetVers = budgetVerRepository.selectAll();
		assertThat("List of budgetVers is empty", budgetVers, is(not(empty())));
	}
	
	
	@Test
	public void existingBudgetVerMayBeSelectedById() throws Exception {
		Long testedBudgetVerId = 1L;
		BudgetVer findBudgetVer = budgetVerRepository.selectById(testedBudgetVerId);
		assertBudgetVer(findBudgetVer);
	}
	
	
	@Test
	public void existingBudgetVerMayBeSelectedByName() throws Exception {
		String testedBudgetVerName = EntitiesHelper.BUDGET_VER_NAME_RU;
		BudgetVer findBudgetVer = budgetVerRepository.selectByName(testedBudgetVerName);
		assertBudgetVer(findBudgetVer);
	}
	
	
	@Test
	public void newBudgetVerBudgetVerBeInserted() throws Exception {		
		BudgetVer budgetVer = newBudgetVer(null);

		budgetVer = budgetVerRepository.insert(budgetVer);
		assertNotNull(budgetVer.getId());
		assertTrue(budgetVer.getId()>0);
		
		BudgetVer findBudgetVer = budgetVerRepository.selectById(budgetVer.getId());
		assertBudgetVer(findBudgetVer);
	}

	
	@Test
	public void existingBudgetVerMayBeDeleted() throws Exception {
		Long testedBudgetVerId = 1L;
		
		budgetVerRepository.delete(testedBudgetVerId);
		BudgetVer findBudgetVer = budgetVerRepository.selectById(testedBudgetVerId);
		assertNull(findBudgetVer);
	}
	
	
	@Test
	public void inExistingBudgetVerNameRuFieldMayBeUpdated() throws Exception {
		Long testedBudgetVerId = 1L;
		
		BudgetVer budgetVer = budgetVerRepository.selectById(testedBudgetVerId);
		String newNameRu = budgetVer.getNameRu() + " (нов)";
		budgetVer.setNameRu(newNameRu);
		budgetVerRepository.update(budgetVer);
		
		BudgetVer findBudgetVer = budgetVerRepository.selectById(testedBudgetVerId);
		assertEquals(newNameRu, findBudgetVer.getNameRu());
	}
	
	
	@Test
	public void inExistingBudgetVerNameKzFieldMayBeUpdated() throws Exception {
		Long testedBudgetVerId = 1L;
		
		BudgetVer budgetVer = budgetVerRepository.selectById(testedBudgetVerId);
		String newNameKz = budgetVer.getNameKz() + " (нов)";
		budgetVer.setNameKz(newNameKz);
		budgetVerRepository.update(budgetVer);
		
		BudgetVer findBudgetVer = budgetVerRepository.selectById(testedBudgetVerId);
		assertEquals(newNameKz, findBudgetVer.getNameKz());
	}
}
