package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.auth.service.PermissionService;
import kz.ecc.isbp.admin.auth.service.impl.PermissionServiceImpl;
import kz.ecc.isbp.admin.auth.repository.PermissionRepository;
import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;


public class AuthPermissionServiceTest {
	PermissionRepository mockPermissionRepository;
	PermissionService permissionService;
	
	
	@Before
	public void setUp() throws Exception {
		mockPermissionRepository = mock(PermissionRepository.class);
		permissionService = new PermissionServiceImpl(mockPermissionRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListPermissionsMayBeFound() {
		when(mockPermissionRepository.selectAll()).thenReturn(Arrays.asList(newPermission(1L), newPermission(2L), newPermission(3L) ));
		
		List<Permission> permissions = permissionService.findAll();		
		verify(mockPermissionRepository, times(1)).selectAll();
		assertThat(permissions, is(not(nullValue())));
		assertThat(permissions, is(not(empty())));
		assertThat(permissions, hasSize(3));
		assertThat(permissions.get(0).getId(), is(equalTo(1L)));
		assertThat(permissions.get(1).getId(), is(equalTo(2L)));
		assertThat(permissions.get(2).getId(), is(equalTo(3L)));
		assertPermission(permissions.get(0));		
	}
	
	
	@Test
	public void existingPermissionMayBeFoundById() {
		when(mockPermissionRepository.selectById(1L)).thenReturn(newPermission(1L));		
		Permission permission = permissionService.findById(1L);		
		verify(mockPermissionRepository, times(1)).selectById(1L);
		assertThat(permission, is(not(nullValue())));
		assertPermission(permission);
	}
	

	@Test
	public void existingPermissionMayBeFoundByName() {
		when(mockPermissionRepository.selectByName(PERMISSION_NAME_RU)).thenReturn(newPermission(1L));		
		Permission permission = permissionService.findByName(PERMISSION_NAME_RU);		
		verify(mockPermissionRepository, times(1)).selectByName(PERMISSION_NAME_RU);
		assertThat(permission, is(not(nullValue())));
		assertPermission(permission);
	}
	
	
	@Test
	public void newPermissionMayBeCreated() {
		Permission origPermission = newPermission(null);
		when(mockPermissionRepository.insert(origPermission)).thenReturn(newPermission(1L));
		
		Permission permission = permissionService.create(origPermission);		
		verify(mockPermissionRepository, times(1)).insert(origPermission);		
		assertThat(permission.getId(), is(not(nullValue())));
		assertPermission(permission);
	}

	
	@Test
	public void existingPermissionMayBeUpdated() {
		Permission origPermission = newPermission(1L);		
		when(mockPermissionRepository.selectById(1L)).thenReturn(origPermission);
		when(mockPermissionRepository.update(anyObject())).then(returnsFirstArg());
		
		Permission permission = permissionService.update(origPermission);		
		verify(mockPermissionRepository, times(1)).selectById(1L);
		verify(mockPermissionRepository, times(1)).update(origPermission);
		assertThat(permission, is(not(nullValue())));
		assertPermission(permission);
	}

	
	@Test
	public void existingPermissionMayBeDeleted() {
		when(mockPermissionRepository.selectById(1L)).thenReturn(newPermission(1L));
		when(mockPermissionRepository.delete(anyLong())).thenReturn(true);		
		
		boolean result = permissionService.delete(1L);		
		verify(mockPermissionRepository, times(1)).delete(1L);
		assertThat(result, is(equalTo(true)));
	}

	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfPermissionIdIsNull() {		
		permissionService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfPermissionNameIsNull() {
		permissionService.findByName(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfPermissionIsNull() {		
		permissionService.create(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfPermissionIsNull() {		
		permissionService.update(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDeleteMethodIfPermissionIdIsNull() {
		permissionService.delete(null);		
	}


	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfPermissionHasNotNullId() {		
		permissionService.create(newPermission(1L));		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfPermissionHasNullId() {		
		permissionService.update(newPermission(null));		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfPermissionIdIsNotExist() {		
		when(mockPermissionRepository.selectById(anyLong())).thenReturn(null);		
		permissionService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failUpdateMethodIfPermissiondIdIsNotExist() {		
		when(mockPermissionRepository.selectById(anyLong())).thenReturn(null);		
		permissionService.update(newPermission(1L));
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void failDeleteMethodIfPermissiondIdIsNotExist() {		
		when(mockPermissionRepository.selectById(anyLong())).thenReturn(null);		
		permissionService.delete(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfPermissionNameIsNotExist() {		
		when(mockPermissionRepository.selectByName(anyString())).thenReturn(null);		
		permissionService.findByName("Bla bla bla");
	}
}
