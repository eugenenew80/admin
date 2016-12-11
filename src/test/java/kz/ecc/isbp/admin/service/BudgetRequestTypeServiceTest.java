package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.nsi.entity.BudgetRequestType;
import kz.ecc.isbp.admin.nsi.repository.BudgetRequestTypeRepository;
import kz.ecc.isbp.admin.nsi.service.BudgetRequestTypeService;
import kz.ecc.isbp.admin.nsi.service.impl.BudgetRequestTypeServiceImpl;


public class BudgetRequestTypeServiceTest {
	BudgetRequestTypeRepository mockBudgetRequestTypeRepository;
	BudgetRequestTypeService budgetRequestTypeService;
	
	
	@Before
	public void setUp() throws Exception {
		mockBudgetRequestTypeRepository = mock(BudgetRequestTypeRepository.class);
		budgetRequestTypeService = new BudgetRequestTypeServiceImpl(mockBudgetRequestTypeRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListBudgetRequestTypesMayBeFound() {
		when(mockBudgetRequestTypeRepository.selectAll()).thenReturn(Arrays.asList(newBudgetRequestType(1L), newBudgetRequestType(2L), newBudgetRequestType(3L) ));
		
		List<BudgetRequestType> budgetRequestTypes = budgetRequestTypeService.findAll();		
		verify(mockBudgetRequestTypeRepository, times(1)).selectAll();
		assertThat(budgetRequestTypes, is(not(nullValue())));
		assertThat(budgetRequestTypes, is(not(empty())));
		assertThat(budgetRequestTypes, hasSize(3));
		assertThat(budgetRequestTypes.get(0).getId(), is(equalTo(1L)));
		assertThat(budgetRequestTypes.get(1).getId(), is(equalTo(2L)));
		assertThat(budgetRequestTypes.get(2).getId(), is(equalTo(3L)));
		assertBudgetRequestType(budgetRequestTypes.get(0));		
	}
	
	
	@Test
	public void existingBudgetRequestTypeMayBeFoundById() {
		when(mockBudgetRequestTypeRepository.selectById(1L)).thenReturn(newBudgetRequestType(1L));		
		BudgetRequestType budgetRequestType = budgetRequestTypeService.findById(1L);		
		verify(mockBudgetRequestTypeRepository, times(1)).selectById(1L);
		assertThat(budgetRequestType, is(not(nullValue())));
		assertBudgetRequestType(budgetRequestType);
	}
	

	@Test
	public void existingBudgetRequestTypeMayBeFoundByName() {
		when(mockBudgetRequestTypeRepository.selectByName(BUDGET_REQUEST_TYPE_NAME_RU)).thenReturn(newBudgetRequestType(1L));		
		BudgetRequestType budgetRequestType = budgetRequestTypeService.findByName(BUDGET_REQUEST_TYPE_NAME_RU);		
		verify(mockBudgetRequestTypeRepository, times(1)).selectByName(BUDGET_REQUEST_TYPE_NAME_RU);
		assertThat(budgetRequestType, is(not(nullValue())));
		assertBudgetRequestType(budgetRequestType);
	}
	


	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryCreateNewBudgetRequestType() {
		budgetRequestTypeService.create(newBudgetRequestType(null));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetRequestType() {
		budgetRequestTypeService.update(newBudgetRequestType(1L));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetRequestType() {
		budgetRequestTypeService.delete(1L);		
	}
	
	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfbudgetRequestTypeIdIsNull() {		
		budgetRequestTypeService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfbudgetRequestTypeNameIsNull() {
		budgetRequestTypeService.findByName(null);		
	}


	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfbudgetRequestTypeIdIsNotExist() {		
		when(mockBudgetRequestTypeRepository.selectById(anyLong())).thenReturn(null);		
		budgetRequestTypeService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfbudgetRequestTypeNameIsNotExist() {		
		when(mockBudgetRequestTypeRepository.selectByName(anyString())).thenReturn(null);		
		budgetRequestTypeService.findByName("Bla bla bla");
	}
}
