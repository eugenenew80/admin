package kz.ecc.isbp.admin.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import kz.ecc.isbp.admin.auth.repository.UserRepository;
import kz.ecc.isbp.admin.auth.repository.impl.UserRepositoryImpl;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.helper.DBUnitHelper;
import kz.ecc.isbp.admin.helper.DataSetLoader;
import kz.ecc.isbp.admin.nsi.repository.OrgStructRepository;
import kz.ecc.isbp.admin.nsi.repository.impl.OrgStructRepositoryImpl;

import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;


public class AuthUserRepositoryTest  {
	private static UserRepository userRepository;
	private static OrgStructRepository orgStructRepository;
	private static DBUnitHelper dbUnitHelper;
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		dbUnitHelper = new DBUnitHelper(); 
		userRepository = new UserRepositoryImpl(dbUnitHelper.getEntityManager());
		orgStructRepository = new OrgStructRepositoryImpl(dbUnitHelper.getEntityManager());
	}
		
	@AfterClass
	public static void tearDownClass() throws Exception {
		dbUnitHelper.closeDatabase();
	}
	
	
	@Before
	public void setUp() throws Exception {		
		dbUnitHelper.beginTransaction();
		
		dbUnitHelper.delete(Arrays.asList(
			new DataSetLoader("auth", "auth_user_modules_rel.xml"),	
			new DataSetLoader("fnd", "fnd_modules.xml"),
			new DataSetLoader("auth", "auth_users.xml"),
			new DataSetLoader("nsi", "nsi_dict_org_structs.xml")
		));
				
		dbUnitHelper.insert(Arrays.asList(
			new DataSetLoader("nsi", "nsi_dict_org_structs.xml"),
			new DataSetLoader("auth", "auth_users.xml"),
			new DataSetLoader("fnd", "fnd_modules.xml"),
			new DataSetLoader("auth", "auth_user_modules_rel.xml")
		));
	}
	
	@After
	public void tearDown() {
		dbUnitHelper.commitTransaction(true);
	}
	
	
	
	//Success cases
	
	@Test
	public void theListUsersMayBeSelected() throws Exception {	
		List<User> users = userRepository.selectAll();
		assertThat(users, is(not(empty())));
	}
	
	
	@Test
	public void existingUserMayBeSelectedById() throws Exception {
		Long testedUserId = 1L;
		User findUser = userRepository.selectById(testedUserId);
		assertUser(findUser);
	}
	
	
	@Test
	public void newUserMayBeInserted() throws Exception {
		User user = newUser();
		user.setId(null);		
		user.setOrgStruct(orgStructRepository.selectById(1L));
		
		userRepository.insert(user);
		assertThat(user.getId(), is(not(equalTo(null))));
		assertThat(user.getId(), is(greaterThan(0L)));
		
		User findUser = userRepository.selectById(user.getId());
		assertUser(findUser);
	}

	
	@Test
	public void existingUserMayBeDeleted() throws Exception {
		Long testedUserId = 1L;
		boolean result = userRepository.delete(testedUserId);
		assertThat(result, is(equalTo(true)));
		
		User findUser = userRepository.selectById(testedUserId);
		assertThat(findUser, is(equalTo(null)));
	}
	
	
	@Test
	public void inExistingUserNameFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;
		User user = userRepository.selectById(testedUserId);
		int countModulesBefore = user.getModules().size();
		assertThat(countModulesBefore, is(greaterThan(0)));
		
		String newName = user.getName() + " (нов)";
		user.setName(newName);		
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertThat(newName, is(equalTo(findUser.getName())));
		assertThat(countModulesBefore, equalTo(user.getModules().size()));
	}
	
	
	@Test
	public void inExistingUserSurnameFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;
		User user = userRepository.selectById(testedUserId);
		String newSurname = user.getSurname() + " (нов)";
		user.setSurname(newSurname);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertEquals(newSurname, findUser.getSurname());
	}
	
	
	@Test
	public void inExistingUserBinFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;
		User user = userRepository.selectById(testedUserId);
		String newBin = "123456789123";
		user.setBin(newBin);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertEquals(newBin, findUser.getBin());
	}
	
	
	@Test
	public void inExistingUserIinFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;
		User user = userRepository.selectById(testedUserId);
		String newIin = "123456789123";
		user.setIin(newIin);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertEquals(newIin, findUser.getIin());
	}
	
	
	@Test
	public void inExistingUserEmailFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;
		User user = userRepository.selectById(testedUserId);
		String newEmail = "pipkin@gmail.com";
		user.setEmail(newEmail);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertEquals(newEmail, findUser.getEmail());
	}	
	
	
	@Test
	public void inExistingUserIsDisabledFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;
		User user = userRepository.selectById(testedUserId);
		user.setIsDisabled(true);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertTrue(findUser.getIsDisabled());
	}	
	
	@Test
	public void inExistingUserIsArchiveFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;		
		User user = userRepository.selectById(testedUserId);
		user.setIsArchive(true);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertTrue(findUser.getIsArchive());
	}
	
	
	@Test
	public void inExistingUserIsDirectorFieldMayBeUpdated() throws Exception {
		Long testedUserId = 1L;		
		User user = userRepository.selectById(testedUserId);
		user.setIsDirector(true);
		userRepository.update(user);
		
		User findUser = userRepository.selectById(testedUserId);
		assertTrue(findUser.getIsDirector());
	}
}
