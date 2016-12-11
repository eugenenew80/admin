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
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.repository.OrgStructRepository;
import kz.ecc.isbp.admin.nsi.repository.impl.OrgStructRepositoryImpl;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class OrgStructRepositoryTest {
	private static OrgStructRepository orgStructRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		orgStructRepository = new OrgStructRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
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
	public void theListOrgStructsMayBeSelected() throws Exception {	
		List<OrgStruct> orgStructs = orgStructRepository.selectAll();
		assertThat(orgStructs, is(not(empty())));
	}
	
	
	@Test
	public void existingOrgStructMayBeSelectedById() throws Exception {
		OrgStruct findOrgStruct = orgStructRepository.selectById(1L);
		assertOrgStruct(findOrgStruct);
	}
	
	
	@Test
	public void existingOrgStructMayBeSelectedByName() throws Exception {
		OrgStruct findOrgStruct = orgStructRepository.selectByName(EntitiesHelper.ORG_STRUCT_NAME_RU);
		assertOrgStruct(findOrgStruct);
	}
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertOrgStruct() {		
		orgStructRepository.insert(newOrgStruct(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteOrgStruct() {
		orgStructRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateOrgStruct() {
		orgStructRepository.insert(newOrgStruct(1L));
	}
}
