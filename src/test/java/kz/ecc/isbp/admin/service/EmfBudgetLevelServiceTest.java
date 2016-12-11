package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.emf.entity.BudgetLevel;
import kz.ecc.isbp.admin.emf.repository.BudgetLevelRepository;
import kz.ecc.isbp.admin.emf.service.BudgetLevelService;
import kz.ecc.isbp.admin.emf.service.impl.BudgetLevelServiceImpl;

public class EmfBudgetLevelServiceTest {
	BudgetLevelRepository mockBudgetLevelRepository;
	BudgetLevelService budgetLevelService;
	
	
	@Before
	public void setUp() throws Exception {
		mockBudgetLevelRepository = mock(BudgetLevelRepository.class);
		budgetLevelService = new BudgetLevelServiceImpl(mockBudgetLevelRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListBudgetLevelsMayBeFound() {
		when(mockBudgetLevelRepository.selectAll()).thenReturn(Arrays.asList(newBudgetLevel(1L), newBudgetLevel(2L), newBudgetLevel(3L) ));
		
		List<BudgetLevel> budgetLevels = budgetLevelService.findAll();		
		verify(mockBudgetLevelRepository, times(1)).selectAll();
		assertThat(budgetLevels, is(not(nullValue())));
		assertThat(budgetLevels, is(not(empty())));
		assertThat(budgetLevels, hasSize(3));
		assertThat(budgetLevels.get(0).getId(), is(equalTo(1L)));
		assertThat(budgetLevels.get(1).getId(), is(equalTo(2L)));
		assertThat(budgetLevels.get(2).getId(), is(equalTo(3L)));
		assertBudgetLevel(budgetLevels.get(0));		
	}
	
	
	@Test
	public void existingBudgetLevelMayBeFoundById() {
		when(mockBudgetLevelRepository.selectById(1L)).thenReturn(newBudgetLevel(1L));		
		BudgetLevel budgetLevel = budgetLevelService.findById(1L);		
		verify(mockBudgetLevelRepository, times(1)).selectById(1L);
		assertThat(budgetLevel, is(not(nullValue())));
		assertBudgetLevel(budgetLevel);
	}
	

	@Test
	public void existingBudgetLevelMayBeFoundByName() {
		when(mockBudgetLevelRepository.selectByName(BUDGET_LEVEL_NAME_RU)).thenReturn(newBudgetLevel(1L));		
		BudgetLevel budgetLevel = budgetLevelService.findByName(BUDGET_LEVEL_NAME_RU);		
		verify(mockBudgetLevelRepository, times(1)).selectByName(BUDGET_LEVEL_NAME_RU);
		assertThat(budgetLevel, is(not(nullValue())));
		assertBudgetLevel(budgetLevel);
	}
	


	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryCreateNewBudgetLevel() {
		budgetLevelService.create(newBudgetLevel(null));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateBudgetLevel() {
		budgetLevelService.update(newBudgetLevel(1L));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteBudgetLevel() {
		budgetLevelService.delete(1L);		
	}
	
	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfbudgetLevelIdIsNull() {		
		budgetLevelService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfbudgetLevelNameIsNull() {
		budgetLevelService.findByName(null);		
	}


	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfbudgetLevelIdIsNotExist() {		
		when(mockBudgetLevelRepository.selectById(anyLong())).thenReturn(null);		
		budgetLevelService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfbudgetLevelNameIsNotExist() {		
		when(mockBudgetLevelRepository.selectByName(anyString())).thenReturn(null);		
		budgetLevelService.findByName("Bla bla bla");
	}
}
