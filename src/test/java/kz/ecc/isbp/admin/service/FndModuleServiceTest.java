package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.fnd.repository.ModuleRepository;
import kz.ecc.isbp.admin.fnd.service.ModuleService;
import kz.ecc.isbp.admin.fnd.service.impl.ModuleServiceImpl;


public class FndModuleServiceTest {
	ModuleRepository mockModuleRepository;
	ModuleService moduleService;
	
	
	@Before
	public void setUp() throws Exception {
		mockModuleRepository = mock(ModuleRepository.class);
		moduleService = new ModuleServiceImpl(mockModuleRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListModulesMayBeFound() {
		when(mockModuleRepository.selectAll()).thenReturn(Arrays.asList(newModule(1L), newModule(2L), newModule(3L) ));
		
		List<Module> modules = moduleService.findAll();		
		verify(mockModuleRepository, times(1)).selectAll();
		assertThat(modules, is(not(nullValue())));
		assertThat(modules, is(not(empty())));
		assertThat(modules, hasSize(3));
		assertThat(modules.get(0).getId(), is(equalTo(1L)));
		assertThat(modules.get(1).getId(), is(equalTo(2L)));
		assertThat(modules.get(2).getId(), is(equalTo(3L)));
		assertModule(modules.get(0));		
	}
	
	
	@Test
	public void existingModuleMayBeFoundById() {
		when(mockModuleRepository.selectById(1L)).thenReturn(newModule(1L));		
		Module module = moduleService.findById(1L);		
		verify(mockModuleRepository, times(1)).selectById(1L);
		assertThat(module, is(not(nullValue())));
		assertModule(module);
	}
	

	@Test
	public void existingModuleMayBeFoundByName() {
		when(mockModuleRepository.selectByName(MODULE_NAME_RU)).thenReturn(newModule(1L));		
		Module module = moduleService.findByName(MODULE_NAME_RU);		
		verify(mockModuleRepository, times(1)).selectByName(MODULE_NAME_RU);
		assertThat(module, is(not(nullValue())));
		assertModule(module);
	}
	
	
	@Test
	public void newModuleMayBeCreated() {
		Module origModule = newModule(null);
		when(mockModuleRepository.insert(origModule)).thenReturn(newModule(1L));
		
		Module module = moduleService.create(origModule);		
		verify(mockModuleRepository, times(1)).insert(origModule);		
		assertThat(module.getId(), is(not(nullValue())));
		assertModule(module);
	}

	
	@Test
	public void existingModuleMayBeUpdated() {
		Module origModule = newModule(1L);		
		when(mockModuleRepository.selectById(1L)).thenReturn(origModule);
		when(mockModuleRepository.update(anyObject())).then(returnsFirstArg());
		
		Module module = moduleService.update(origModule);		
		verify(mockModuleRepository, times(1)).selectById(1L);
		verify(mockModuleRepository, times(1)).update(origModule);
		assertThat(module, is(not(nullValue())));
		assertModule(module);
	}

	
	@Test
	public void existingModuleMayBeDeleted() {
		when(mockModuleRepository.selectById(1L)).thenReturn(newModule(1L));
		when(mockModuleRepository.delete(anyLong())).thenReturn(true);		
		
		boolean result = moduleService.delete(1L);		
		verify(mockModuleRepository, times(1)).delete(1L);
		assertThat(result, is(equalTo(true)));
	}

	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfModuleIdIsNull() {		
		moduleService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfModuleNameIsNull() {
		moduleService.findByName(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfModuleIsNull() {		
		moduleService.create(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfModuleIsNull() {		
		moduleService.update(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDeleteMethodIfModuleIdIsNull() {
		moduleService.delete(null);		
	}


	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfModuleHasNotNullId() {		
		moduleService.create(newModule(1L));		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfModuleHasNullId() {		
		moduleService.update(newModule(null));		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfModuleIdIsNotExist() {		
		when(mockModuleRepository.selectById(anyLong())).thenReturn(null);		
		moduleService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failUpdateMethodIfModuledIdIsNotExist() {		
		when(mockModuleRepository.selectById(anyLong())).thenReturn(null);		
		moduleService.update(newModule(1L));
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void failDeleteMethodIfModuledIdIsNotExist() {		
		when(mockModuleRepository.selectById(anyLong())).thenReturn(null);		
		moduleService.delete(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfModuleNameIsNotExist() {		
		when(mockModuleRepository.selectByName(anyString())).thenReturn(null);		
		moduleService.findByName("Бла бла бла");
	}

}
