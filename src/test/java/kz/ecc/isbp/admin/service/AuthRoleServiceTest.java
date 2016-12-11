package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.auth.service.RoleService;
import kz.ecc.isbp.admin.auth.service.impl.RoleServiceImpl;
import kz.ecc.isbp.admin.auth.repository.RoleRepository;
import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;


public class AuthRoleServiceTest {
	RoleRepository mockRoleRepository;
	RoleService roleService;
	
	
	@Before
	public void setUp() throws Exception {
		mockRoleRepository = mock(RoleRepository.class);
		roleService = new RoleServiceImpl(mockRoleRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListRolesMayBeFound() {
		when(mockRoleRepository.selectAll()).thenReturn(Arrays.asList(newRole(1L), newRole(2L), newRole(3L) ));
		
		List<Role> roles = roleService.findAll();		
		verify(mockRoleRepository, times(1)).selectAll();
		assertThat(roles, is(not(nullValue())));
		assertThat(roles, is(not(empty())));
		assertThat(roles, hasSize(3));
		assertThat(roles.get(0).getId(), is(equalTo(1L)));
		assertThat(roles.get(1).getId(), is(equalTo(2L)));
		assertThat(roles.get(2).getId(), is(equalTo(3L)));
		assertRole(roles.get(0));		
	}
	
	
	@Test
	public void existingRoleMayBeFoundById() {
		when(mockRoleRepository.selectById(1L)).thenReturn(newRole(1L));		
		Role role = roleService.findById(1L);		
		verify(mockRoleRepository, times(1)).selectById(1L);
		assertThat(role, is(not(nullValue())));
		assertRole(role);
	}
	

	@Test
	public void existingRoleMayBeFoundByName() {
		when(mockRoleRepository.selectByName(ROLE_NAME_RU)).thenReturn(newRole(1L));		
		Role role = roleService.findByName(ROLE_NAME_RU);		
		verify(mockRoleRepository, times(1)).selectByName(ROLE_NAME_RU);
		assertThat(role, is(not(nullValue())));
		assertRole(role);
	}
	
	
	@Test
	public void newRoleMayBeCreated() {
		Role origRole = newRole(null);
		when(mockRoleRepository.insert(origRole)).thenReturn(newRole(1L));
		
		Role role = roleService.create(origRole);		
		verify(mockRoleRepository, times(1)).insert(origRole);		
		assertThat(role.getId(), is(not(nullValue())));
		assertRole(role);
	}

	
	@Test
	public void existingRoleMayBeUpdated() {
		Role origRole = newRole(1L);		
		when(mockRoleRepository.selectById(1L)).thenReturn(origRole);
		when(mockRoleRepository.update(anyObject())).then(returnsFirstArg());
		
		Role role = roleService.update(origRole);		
		verify(mockRoleRepository, times(1)).selectById(1L);
		verify(mockRoleRepository, times(1)).update(origRole);
		assertThat(role, is(not(nullValue())));
		assertRole(role);
	}

	
	@Test
	public void existingRoleMayBeDeleted() {
		when(mockRoleRepository.selectById(1L)).thenReturn(newRole(1L));
		when(mockRoleRepository.delete(anyLong())).thenReturn(true);		
		
		boolean result = roleService.delete(1L);		
		verify(mockRoleRepository, times(1)).delete(1L);
		assertThat(result, is(equalTo(true)));
	}

	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfRoleIdIsNull() {		
		roleService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfRoleNameIsNull() {
		roleService.findByName(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfRoleIsNull() {		
		roleService.create(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfRoleIsNull() {		
		roleService.update(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDeleteMethodIfRoleIdIsNull() {
		roleService.delete(null);		
	}


	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfRoleHasNotNullId() {		
		roleService.create(newRole(1L));		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfRoleHasNullId() {		
		roleService.update(newRole(null));		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfRoleIdIsNotExist() {		
		when(mockRoleRepository.selectById(anyLong())).thenReturn(null);		
		roleService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failUpdateMethodIfRoledIdIsNotExist() {		
		when(mockRoleRepository.selectById(anyLong())).thenReturn(null);		
		roleService.update(newRole(1L));
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void failDeleteMethodIfRoledIdIsNotExist() {		
		when(mockRoleRepository.selectById(anyLong())).thenReturn(null);		
		roleService.delete(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfRoleNameIsNotExist() {		
		when(mockRoleRepository.selectByName(anyString())).thenReturn(null);		
		roleService.findByName("Bla bla bla");
	}
}
