package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import java.util.stream.Collectors;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.auth.service.UserService;
import kz.ecc.isbp.admin.auth.service.impl.UserServiceImpl;
import kz.ecc.isbp.admin.auth.repository.RoleModuleDictRepository;
import kz.ecc.isbp.admin.auth.repository.RoleModuleRepository;
import kz.ecc.isbp.admin.auth.repository.RoleRepository;
import kz.ecc.isbp.admin.auth.repository.UserRepository;
import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.repository.DictRepository;
import kz.ecc.isbp.admin.fnd.repository.ModuleRepository;


public class AuthUserServiceTest {
	UserRepository mockUserRepository;
	RoleRepository mockRoleRepository;
	ModuleRepository mockModuleRepository;	
	DictRepository mockDictRepository;
	RoleModuleRepository mockRoleModuleRepository;
	RoleModuleDictRepository mockRoleModuleDictRepository;
	UserService userService;
	
	@Before
	public void setUp() throws Exception {
		mockUserRepository = mock(UserRepository.class);
		mockModuleRepository = mock(ModuleRepository.class);
		mockRoleRepository = mock(RoleRepository.class);
		mockDictRepository = mock(DictRepository.class);
		mockRoleModuleRepository = mock(RoleModuleRepository.class);
		mockRoleModuleDictRepository = mock(RoleModuleDictRepository.class);
		
		UserServiceImpl userServiceImpl = new UserServiceImpl(mockUserRepository);
		userServiceImpl.setModuleRepository(mockModuleRepository);
		userServiceImpl.setRoleRepository(mockRoleRepository);
		userServiceImpl.setDictRepository(mockDictRepository);
		userServiceImpl.setRoleModuleRepository(mockRoleModuleRepository);
		userServiceImpl.setRoleModuleDictRepository(mockRoleModuleDictRepository);
		userService = userServiceImpl;
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListUsersMayBeFound() {
		when(mockUserRepository.selectAll()).thenReturn(Arrays.asList(newUser(1L), newUser(2L), newUser(3L) ));
		
		List<User> users = userService.findAll();		
		verify(mockUserRepository, times(1)).selectAll();
		assertThat(users, is(not(nullValue())));
		assertThat(users, is(not(empty())));
		assertThat(users, hasSize(3));
		assertThat(users.get(0).getId(), is(equalTo(1L)));
		assertThat(users.get(1).getId(), is(equalTo(2L)));
		assertThat(users.get(2).getId(), is(equalTo(3L)));
		assertUser(users.get(0));		
	}
	
	
	@Test
	public void existingUserMayBeFoundById() {
		when(mockUserRepository.selectById(1L)).thenReturn(newUser(1L));		
		User user = userService.findById(1L);		
		verify(mockUserRepository, times(1)).selectById(1L);
		assertThat(user, is(not(nullValue())));
		assertUser(user);
	}
	

	@Test
	public void newUserMayBeCreated() {
		User origUser = newUser(null);
		when(mockUserRepository.insert(origUser)).thenReturn(newUser(1L));
		
		User user = userService.create(origUser);		
		verify(mockUserRepository, times(1)).insert(origUser);		
		assertThat(user.getId(), is(not(nullValue())));
		assertUser(user);
	}

	
	@Test
	public void existingUserMayBeUpdated() {
		User origUser = newUser(1L);		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());
		
		User user = userService.update(origUser);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user, is(not(nullValue())));
		assertUser(user);
	}

	
	@Test
	public void existingUserMayBeDeleted() {
		when(mockUserRepository.selectById(1L)).thenReturn(newUser(1L));
		when(mockUserRepository.delete(anyLong())).thenReturn(true);		
		
		boolean result = userService.delete(1L);		
		verify(mockUserRepository, times(1)).delete(1L);
		assertThat(result, is(equalTo(true)));
	}

	
	@Test
	public void existingUserMayBeDisabled() {
		User origUser = newUser(1L);		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());
		
		User user = userService.disable(1L);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getIsDisabled(), is(equalTo(true)));
	}

	
	@Test
	public void existingUserMayBeEnabled() {
		User origUser = newUser(1L);
		origUser.setIsDisabled(true);		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());
		
		User user = userService.enable(1L);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getIsDisabled(), is(equalTo(false)));
	}

	
	@Test
	public void existingUserMayBeSendToArchive() {
		User origUser = newUser(1L);		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());
		
		User user = userService.toArchive(1L);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getIsArchive(), is(equalTo(true)));
	}

	
	@Test
	public void existingUserMayBeExtractedFromArchive() {
		User origUser = newUser(1L);
		origUser.setIsArchive(true);
		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());
		
		User user = userService.fromArchive(1L);
		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getIsArchive(), is(equalTo(false)));
	}

	
	@Test
	public void modulesMayBeAddedToExistingUser() {
		User origUser = newUser(1L);
		origUser.getModules().add(newModule(3L));
		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());
		
		Set<Module> modules = new HashSet<>(Arrays.asList(newModule(1L) , newModule(2L)));
		User user = userService.addModules(origUser.getId(), modules);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getModules(), hasSize(2));
	}


	@Test
	public void rolesMayBeAddedToExistingUser() {
		User origUser = newUser(1L);
		RoleModule roleModuleCurrent1 = new RoleModule(newModule(1L), newRole(3L), 1L);
		roleModuleCurrent1.setId(3L);
		origUser.getRoleModules().add(roleModuleCurrent1);
		
		RoleModule roleModuleNew1 = new RoleModule(newModule(1L), newRole(1L), 1L);
		roleModuleNew1.setId(1L);
		RoleModule roleModuleNew2 = new RoleModule(newModule(1L), newRole(2L), 1L);
		roleModuleNew2.setId(2L);

		when(mockUserRepository.selectById(1L)).thenReturn(origUser);		
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());		
		when(mockModuleRepository.selectById(1L)).thenReturn(newModule(1L));		
		when(mockRoleRepository.selectById(1L)).thenReturn(newRole(1L));
		when(mockRoleRepository.selectById(2L)).thenReturn(newRole(2L));				
		when(mockRoleModuleRepository.selectByIds(1L, 1L, 1L)).thenReturn(roleModuleNew1);
		when(mockRoleModuleRepository.selectByIds(1L, 2L, 1L)).thenReturn(roleModuleNew2);
		
		
		Set<RoleModule> roleModules = new HashSet<>(Arrays.asList(
					new RoleModule(newModule(1L), newRole(1L), 1L), 
					new RoleModule(newModule(1L), newRole(2L), 1L) 
				));
		
		User user = userService.addRoles(1L, 1L, roleModules);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getRoleModules(), hasSize(2));
	}

	
	@Test
	public void dictsMayBeAddedToExistingUser() {
		User origUser = newUser(1L);
		RoleModuleDict roleModuleDictCurrent = new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(3L), 1L);
		roleModuleDictCurrent.setId(3L);
		origUser.getRoleModuleDicts().add(roleModuleDictCurrent);
		
		RoleModuleDict roleModuleDictNew1 = new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(1L), 1L);
		roleModuleDictNew1.setId(1L);
		
		RoleModuleDict roleModuleDictNew2 = new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(2L), 2L);
		roleModuleDictNew2.setId(2L);

		when(mockUserRepository.selectById(1L)).thenReturn(origUser);		
		when(mockUserRepository.update(anyObject())).then(returnsFirstArg());		
		when(mockModuleRepository.selectById(1L)).thenReturn(newModule(1L));		
		when(mockRoleRepository.selectById(1L)).thenReturn(newRole(1L));
		when(mockDictRepository.selectById(1L)).thenReturn(newDict(1L));
		when(mockDictRepository.selectById(2L)).thenReturn(newDict(2L));
		
		when(mockRoleModuleDictRepository.selectByIds(1L, 1L, 1L, 1L, 1L)).thenReturn(roleModuleDictNew1);
		when(mockRoleModuleDictRepository.selectByIds(1L, 1L, 1L, 2L, 2L)).thenReturn(roleModuleDictNew2);
		
		Set<RoleModuleDict> roleModuleDicts = new HashSet<>(Arrays.asList(
				new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(1L), 1L), 
				new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(2L), 2L) 
			));
		
		User user = userService.addDicts(1L, 1L, 1L, 1L, roleModuleDicts);		
		verify(mockUserRepository, times(1)).selectById(1L);
		verify(mockUserRepository, times(1)).update(origUser);
		assertThat(user.getRoleModuleDicts(), hasSize(2));
	}
	
	
	@Test
	public void theModulesMayBeGetForExistingUser() {
		User origUser = newUser(1L);
		origUser.getModules().add(newModule(1L));
		origUser.getModules().add(newModule(2L));
		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		when(mockModuleRepository.selectAll()).thenReturn(Arrays.asList(newModule(1L), newModule(2L), newModule(3L) ));
		
		List<Module> modules = userService.getModules(1L);
		verify(mockUserRepository, times(1)).selectById(1L);
		assertThat(modules, hasSize(3));		
		assertThat(modules.stream().filter(Module::getIsGranted).collect(Collectors.toList()), hasSize(2));
		
		assertThat(modules.stream().filter(it -> Objects.equals(it.getId(), 1L) && it.getIsGranted()).findFirst().isPresent(), is(equalTo(true)));		
		assertThat(modules.stream().filter(it -> Objects.equals(it.getId(), 2L) && it.getIsGranted()).findFirst().isPresent(), is(equalTo(true)));
	}
	
	
	@Test
	public void theRolesMayBeGetForExistingUser() {
		User origUser = newUser(1L);
		origUser.getRoleModules().add(new RoleModule(newModule(1L), newRole(1L), 1L));
		origUser.getRoleModules().add(new RoleModule(newModule(1L), newRole(2L), 1L));
		origUser.getRoleModules().add(new RoleModule(newModule(2L), newRole(1L), 1L));
		origUser.getRoleModules().add(new RoleModule(newModule(3L), newRole(1L), 1L));
		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		
		when(mockModuleRepository.selectAll()).thenReturn(Arrays.asList(newModule(1L), newModule(2L), newModule(3L) ));
		when(mockModuleRepository.selectById(1L)).thenReturn(newModule(1L));		
		when(mockModuleRepository.selectById(2L)).thenReturn(newModule(2L));		
		when(mockModuleRepository.selectById(3L)).thenReturn(newModule(3L));		
		
		when(mockRoleRepository.selectAll()).thenReturn(Arrays.asList(newRole(1L), newRole(2L), newRole(3L) ));
		when(mockRoleRepository.selectById(1L)).thenReturn(newRole(1L));
		when(mockRoleRepository.selectById(2L)).thenReturn(newRole(2L));
		when(mockRoleRepository.selectById(3L)).thenReturn(newRole(3L));
				
		List<RoleModule> roles = userService.getRoles(1L, 1L);
		verify(mockUserRepository, times(1)).selectById(1L);		
		assertThat(roles, hasSize(9));
		assertThat(roles.stream().filter(RoleModule::getIsGranted).collect(Collectors.toList()), hasSize(2));
		
		boolean isGranted = roles.stream().filter(it -> 
					Objects.equals(it.getModule().getId(), 1L) &&
					Objects.equals(it.getRole().getId(), 1L) &&
					Objects.equals(it.getLevelId(), 1L)
				).findFirst().isPresent();
		assertThat(isGranted, is(equalTo(true)));

		isGranted = roles.stream().filter(it -> 
			Objects.equals(it.getModule().getId(), 1L) &&
			Objects.equals(it.getRole().getId(), 2L) &&
			Objects.equals(it.getLevelId(), 1L)
		).findFirst().isPresent();
		assertThat(isGranted, is(equalTo(true)));
	}
	

	@Test
	public void theDictsMayBeGetForExistingUser() {
		User origUser = newUser(1L);
		origUser.getRoleModuleDicts().add(new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(1L), 1L ));
		origUser.getRoleModuleDicts().add(new RoleModuleDict(newModule(1L), newRole(1L), 1L, newDict(2L), 2L ));
		origUser.getRoleModuleDicts().add(new RoleModuleDict(newModule(1L), newRole(1L), 2L, newDict(1L), 1L ));
		origUser.getRoleModuleDicts().add(new RoleModuleDict(newModule(1L), newRole(1L), 2L, newDict(2L), 2L ));
		
		when(mockUserRepository.selectById(1L)).thenReturn(origUser);
		
		when(mockModuleRepository.selectAll()).thenReturn(Arrays.asList(newModule(1L), newModule(2L), newModule(3L) ));
		when(mockModuleRepository.selectById(1L)).thenReturn(newModule(1L));		
		when(mockModuleRepository.selectById(2L)).thenReturn(newModule(2L));		
		when(mockModuleRepository.selectById(3L)).thenReturn(newModule(3L));		
		
		when(mockRoleRepository.selectAll()).thenReturn(Arrays.asList(newRole(1L), newRole(2L), newRole(3L) ));
		when(mockRoleRepository.selectById(1L)).thenReturn(newRole(1L));
		when(mockRoleRepository.selectById(2L)).thenReturn(newRole(2L));
		when(mockRoleRepository.selectById(3L)).thenReturn(newRole(3L));
				
		when(mockDictRepository.selectAll()).thenReturn(Arrays.asList(newDict(1L), newDict(2L), newDict(3L) ));
		when(mockDictRepository.selectById(1L)).thenReturn(newDict(1L));
		when(mockDictRepository.selectById(2L)).thenReturn(newDict(2L));
		when(mockDictRepository.selectById(3L)).thenReturn(newDict(3L));
				
		List<RoleModuleDict> dicts = userService.getDicts(1L, 1L, 1L, 1L);
		verify(mockUserRepository, times(1)).selectById(1L);		
		assertThat(dicts, hasSize(3));
		assertThat(dicts.stream().filter(RoleModuleDict::getIsViewGranted).collect(Collectors.toList()), hasSize(1));
		assertThat(dicts.stream().filter(RoleModuleDict::getIsEditGranted).collect(Collectors.toList()), hasSize(1));
	}

	
		
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failUserMayBeNotFindByName() {
		userService.findByName("Bla bla bla");		
	}
	
	

	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfUserIdIsNull() {		
		userService.findById(null);
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfUserIsNull() {		
		userService.create(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfUserIsNull() {		
		userService.update(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDeleteMethodIfUserIdIsNull() {
		userService.delete(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDisableMethodIfUserIdIsNull() {		
		userService.disable(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failEnableMethodIfUserIdIsNull() {		
		userService.enable(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failToArchiveMethodIfUserIdIsNull() {		
		userService.toArchive(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failFromArchiveMethodIfUserIdIsNull() {		
		userService.fromArchive(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failAddModulesMethodIfUserIdIsNull() {		
		userService.addModules(null, new HashSet<>());		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failAddModulesMethodIfModulesIsNull() {		
		userService.addModules(1L, null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failAddRolesMethodIfUserIdIsNull() {		
		userService.addRoles(null, 1L, new HashSet<>());		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failAddRolesMethodIfModuleIdIsNull() {		
		userService.addRoles(1L, null, new HashSet<>());		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failAddRolesMethodIfRolesIsNull() {		
		userService.addRoles(1L, 1L, null);		
	}

	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfUserHasNotNullId() {		
		userService.create(newUser(1L));		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfUserHasNullId() {		
		userService.update(newUser(null));		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfUserIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failUpdateMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.update(newUser(1L));
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void failDeleteMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.delete(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failDisableMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.disable(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failEnableMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.enable(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failToArchiveMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.toArchive(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFromArchiveMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.fromArchive(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failAddModulesMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.addModules(1L, new HashSet<>());		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failAddRolesMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.addRoles(1L, 1L, new HashSet<>());		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failAddDictsMethodIfUserdIdIsNotExist() {		
		when(mockUserRepository.selectById(anyLong())).thenReturn(null);		
		userService.addDicts(1L, 1L, 1L, 1L, new HashSet<>());		
	}
}
