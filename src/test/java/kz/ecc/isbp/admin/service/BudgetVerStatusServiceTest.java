package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.nsi.entity.BudgetVerStatus;
import kz.ecc.isbp.admin.nsi.repository.BudgetVerStatusRepository;
import kz.ecc.isbp.admin.nsi.service.BudgetVerStatusService;
import kz.ecc.isbp.admin.nsi.service.impl.BudgetVerStatusServiceImpl;


public class BudgetVerStatusServiceTest {
	BudgetVerStatusRepository mockBudgetVerStatusRepository;
	BudgetVerStatusService budgetVerStatusService;
	
	
	@Before
	public void setUp() throws Exception {
		mockBudgetVerStatusRepository = mock(BudgetVerStatusRepository.class);
		budgetVerStatusService = new BudgetVerStatusServiceImpl(mockBudgetVerStatusRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListBudgetVerStatussMayBeFound() {
		when(mockBudgetVerStatusRepository.selectAll()).thenReturn(Arrays.asList(newBudgetVerStatus(1L), newBudgetVerStatus(2L), newBudgetVerStatus(3L) ));
		
		List<BudgetVerStatus> budgetVerStatuss = budgetVerStatusService.findAll();		
		verify(mockBudgetVerStatusRepository, times(1)).selectAll();
		assertThat(budgetVerStatuss, is(not(nullValue())));
		assertThat(budgetVerStatuss, is(not(empty())));
		assertThat(budgetVerStatuss, hasSize(3));
		assertThat(budgetVerStatuss.get(0).getId(), is(equalTo(1L)));
		assertThat(budgetVerStatuss.get(1).getId(), is(equalTo(2L)));
		assertThat(budgetVerStatuss.get(2).getId(), is(equalTo(3L)));
		assertBudgetVerStatus(budgetVerStatuss.get(0));		
	}
	
	
	@Test
	public void existingBudgetVerStatusMayBeFoundById() {
		when(mockBudgetVerStatusRepository.selectById(1L)).thenReturn(newBudgetVerStatus(1L));		
		BudgetVerStatus budgetVerStatus = budgetVerStatusService.findById(1L);		
		verify(mockBudgetVerStatusRepository, times(1)).selectById(1L);
		assertThat(budgetVerStatus, is(not(nullValue())));
		assertBudgetVerStatus(budgetVerStatus);
	}
	

	@Test
	public void existingBudgetVerStatusMayBeFoundByName() {
		when(mockBudgetVerStatusRepository.selectByName(BUDGET_VER_STATUS_NAME_RU)).thenReturn(newBudgetVerStatus(1L));		
		BudgetVerStatus budgetVerStatus = budgetVerStatusService.findByName(BUDGET_VER_STATUS_NAME_RU);		
		verify(mockBudgetVerStatusRepository, times(1)).selectByName(BUDGET_VER_STATUS_NAME_RU);
		assertThat(budgetVerStatus, is(not(nullValue())));
		assertBudgetVerStatus(budgetVerStatus);
	}
	


	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryCreateNewBudgetVerStatus() {
		budgetVerStatusService.create(newBudgetVerStatus(null));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetVerStatus() {
		budgetVerStatusService.update(newBudgetVerStatus(1L));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetVerStatus() {
		budgetVerStatusService.delete(1L);		
	}
	
	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfbudgetVerStatusIdIsNull() {		
		budgetVerStatusService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfbudgetVerStatusNameIsNull() {
		budgetVerStatusService.findByName(null);		
	}


	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfbudgetVerStatusIdIsNotExist() {		
		when(mockBudgetVerStatusRepository.selectById(anyLong())).thenReturn(null);		
		budgetVerStatusService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfbudgetVerStatusNameIsNotExist() {		
		when(mockBudgetVerStatusRepository.selectByName(anyString())).thenReturn(null);		
		budgetVerStatusService.findByName("Bla bla bla");
	}
}
