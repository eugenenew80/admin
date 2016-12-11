package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.emf.entity.BudgetType;
import kz.ecc.isbp.admin.emf.repository.BudgetTypeRepository;
import kz.ecc.isbp.admin.emf.service.BudgetTypeService;
import kz.ecc.isbp.admin.emf.service.impl.BudgetTypeServiceImpl;

public class EmfBudgetTypeServiceTest {
	BudgetTypeRepository mockBudgetTypeRepository;
	BudgetTypeService budgetTypeService;
	
	
	@Before
	public void setUp() throws Exception {
		mockBudgetTypeRepository = mock(BudgetTypeRepository.class);
		budgetTypeService = new BudgetTypeServiceImpl(mockBudgetTypeRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListBudgetTypesMayBeFound() {
		when(mockBudgetTypeRepository.selectAll()).thenReturn(Arrays.asList(newBudgetType(1L), newBudgetType(2L), newBudgetType(3L) ));
		
		List<BudgetType> budgetTypes = budgetTypeService.findAll();		
		verify(mockBudgetTypeRepository, times(1)).selectAll();
		assertThat(budgetTypes, is(not(nullValue())));
		assertThat(budgetTypes, is(not(empty())));
		assertThat(budgetTypes, hasSize(3));
		assertThat(budgetTypes.get(0).getId(), is(equalTo(1L)));
		assertThat(budgetTypes.get(1).getId(), is(equalTo(2L)));
		assertThat(budgetTypes.get(2).getId(), is(equalTo(3L)));
		assertBudgetType(budgetTypes.get(0));		
	}
	
	
	@Test
	public void existingBudgetTypeMayBeFoundById() {
		when(mockBudgetTypeRepository.selectById(1L)).thenReturn(newBudgetType(1L));		
		BudgetType budgetType = budgetTypeService.findById(1L);		
		verify(mockBudgetTypeRepository, times(1)).selectById(1L);
		assertThat(budgetType, is(not(nullValue())));
		assertBudgetType(budgetType);
	}
	

	@Test
	public void existingBudgetTypeMayBeFoundByName() {
		when(mockBudgetTypeRepository.selectByName(BUDGET_TYPE_NAME_RU)).thenReturn(newBudgetType(1L));		
		BudgetType budgetType = budgetTypeService.findByName(BUDGET_TYPE_NAME_RU);		
		verify(mockBudgetTypeRepository, times(1)).selectByName(BUDGET_TYPE_NAME_RU);
		assertThat(budgetType, is(not(nullValue())));
		assertBudgetType(budgetType);
	}
	
	
	
	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryCreateNewBudgetType() {
		budgetTypeService.create(newBudgetType(null));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetType() {
		budgetTypeService.update(newBudgetType(1L));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetType() {
		budgetTypeService.delete(1L);		
	}

	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfbudgetTypeIdIsNull() {		
		budgetTypeService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfbudgetTypeNameIsNull() {
		budgetTypeService.findByName(null);		
	}


	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfbudgetTypeIdIsNotExist() {		
		when(mockBudgetTypeRepository.selectById(anyLong())).thenReturn(null);		
		budgetTypeService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfbudgetTypeNameIsNotExist() {		
		when(mockBudgetTypeRepository.selectByName(anyString())).thenReturn(null);		
		budgetTypeService.findByName("Бла бла бла");
	}
}
