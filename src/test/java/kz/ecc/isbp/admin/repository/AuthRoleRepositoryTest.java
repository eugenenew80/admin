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
import kz.ecc.isbp.admin.auth.repository.RoleRepository;
import kz.ecc.isbp.admin.auth.repository.impl.RoleRepositoryImpl;
import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class AuthRoleRepositoryTest {
	private static RoleRepository roleRepository;
	private static DBUnitHelper dbUnitHelper;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		roleRepository = new RoleRepositoryImpl(dbUnitHelper.getEntityManager());;
	}

		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		
		dbUnitHelper.delete(Arrays.asList(
			new DataSetLoader("auth", "auth_role_permissions_rel.xml"),	
			new DataSetLoader("auth", "auth_roles.xml")
		));
				
		dbUnitHelper.insert(Arrays.asList(
			new DataSetLoader("auth", "auth_roles.xml"),
			new DataSetLoader("auth", "auth_role_permissions_rel.xml")
		));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}

	
	
	//Success cases
	
	@Test
	public void theListRolesMayBeSelected() throws Exception {	
		List<Role> roles = roleRepository.selectAll();
		assertThat(roles, is(not(empty())));
	}
	
		
	@Test
	public void existingRoleMayBeSelectedById() throws Exception {
		Role findRole = roleRepository.selectById(1L);
		assertRole(findRole);
	}
	
	
	@Test
	public void existingRoleMayBeSelectedByName() throws Exception {
		Role findRole = roleRepository.selectByName(EntitiesHelper.ROLE_NAME_RU);
		assertRole(findRole);
	}
	
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertRole() {		
		roleRepository.insert(newRole(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteRole() {
		roleRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateRole() {
		roleRepository.insert(newRole(1L));
	}
}
