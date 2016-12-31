package kz.ecc.isbp.admin.repository;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kz.ecc.isbp.admin.auth.repository.PermissionRepository;
import kz.ecc.isbp.admin.auth.repository.impl.PermissionRepositoryImpl;
import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.helper.EntitiesHelper;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class AuthPermissionRepositoryTest {
	private static PermissionRepository permissionRepository;
	private static DBUnitHelper dbUnitHelper;
	

	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		permissionRepository = new PermissionRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		dbUnitHelper.delete( Arrays.asList(new DataSetLoader("auth", "auth_permissions.xml")));
		dbUnitHelper.insert( Arrays.asList(new DataSetLoader("auth", "auth_permissions.xml")));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}

	
	
	//Success cases
	
	@Test
	public void theListPermissionsMayBeSelected() throws Exception {	
		List<Permission> permissions = permissionRepository.selectAll();
		assertThat(permissions, is(not(empty())));
	}
	
	
	@Test
	public void existingPermissionMayBeSelectedById() throws Exception {
		Permission findPermission = permissionRepository.selectById(1L);
		assertPermission(findPermission);
	}
	
	
	@Test
	public void existingPermissionMayBeSelectedByName() throws Exception {
		Permission findPermission = permissionRepository.selectByName(EntitiesHelper.PERMISSION_NAME_RU);
		assertPermission(findPermission);
	}
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryInsertPermission() {		
		permissionRepository.insert(newPermission(null));
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeletePermission() {
		permissionRepository.delete(1L);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdatePermission() {
		permissionRepository.insert(newPermission(1L));
	}
}
