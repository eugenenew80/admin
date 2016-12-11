package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.repository.OrgStructRepository;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;
import kz.ecc.isbp.admin.nsi.service.impl.OrgStructServiceImpl;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;


public class OrgStructServiceTest {
	OrgStructRepository mockOrgStructRepository;
	OrgStructService orgStructService;
	
	
	@Before
	public void setUp() throws Exception {
		mockOrgStructRepository = mock(OrgStructRepository.class);
		orgStructService = new OrgStructServiceImpl(mockOrgStructRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListorgStructsMayBeFound() {
		when(mockOrgStructRepository.selectAll()).thenReturn(Arrays.asList(newOrgStruct(1L), newOrgStruct(2L), newOrgStruct(3L) ));
		
		List<OrgStruct> orgStructs = orgStructService.findAll();		
		verify(mockOrgStructRepository, times(1)).selectAll();
		assertThat(orgStructs, is(not(nullValue())));
		assertThat(orgStructs, is(not(empty())));
		assertThat(orgStructs, hasSize(3));
		assertThat(orgStructs.get(0).getId(), is(equalTo(1L)));
		assertThat(orgStructs.get(1).getId(), is(equalTo(2L)));
		assertThat(orgStructs.get(2).getId(), is(equalTo(3L)));
		assertOrgStruct(orgStructs.get(0));		
	}
	
	
	@Test
	public void existingorgStructMayBeFoundById() {
		when(mockOrgStructRepository.selectById(1L)).thenReturn(newOrgStruct(1L));		
		OrgStruct orgStruct = orgStructService.findById(1L);		
		verify(mockOrgStructRepository, times(1)).selectById(1L);
		assertThat(orgStruct, is(not(nullValue())));
		assertOrgStruct(orgStruct);
	}
	

	@Test
	public void existingorgStructMayBeFoundByName() {
		when(mockOrgStructRepository.selectByName(ORG_STRUCT_NAME_RU)).thenReturn(newOrgStruct(1L));		
		OrgStruct orgStruct = orgStructService.findByName(ORG_STRUCT_NAME_RU);		
		verify(mockOrgStructRepository, times(1)).selectByName(ORG_STRUCT_NAME_RU);
		assertThat(orgStruct, is(not(nullValue())));
		assertOrgStruct(orgStruct);
	}
	




	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIforgStructIdIsNull() {		
		orgStructService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIforgStructNameIsNull() {
		orgStructService.findByName(null);		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIforgStructIdIsNotExist() {		
		when(mockOrgStructRepository.selectById(anyLong())).thenReturn(null);		
		orgStructService.findById(1L);
	}



	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIforgStructNameIsNotExist() {		
		when(mockOrgStructRepository.selectByName(anyString())).thenReturn(null);		
		orgStructService.findByName("Бла бла бла");
	}
}
