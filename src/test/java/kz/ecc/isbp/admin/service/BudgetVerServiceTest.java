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
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.repository.BudgetVerRepository;
import kz.ecc.isbp.admin.nsi.service.BudgetVerService;
import kz.ecc.isbp.admin.nsi.service.impl.BudgetVerServiceImpl;


public class BudgetVerServiceTest {
	BudgetVerRepository mockBudgetVerRepository;
	BudgetVerService budgetVerService;
	
	
	@Before
	public void setUp() throws Exception {
		mockBudgetVerRepository = mock(BudgetVerRepository.class);
		budgetVerService = new BudgetVerServiceImpl(mockBudgetVerRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListBudgetVersMayBeFound() {
		when(mockBudgetVerRepository.selectAll()).thenReturn(Arrays.asList(newBudgetVer(1L), newBudgetVer(2L), newBudgetVer(3L) ));
		
		List<BudgetVer> budgetVers = budgetVerService.findAll();		
		verify(mockBudgetVerRepository, times(1)).selectAll();
		assertThat(budgetVers, is(not(nullValue())));
		assertThat(budgetVers, is(not(empty())));
		assertThat(budgetVers, hasSize(3));
		assertThat(budgetVers.get(0).getId(), is(equalTo(1L)));
		assertThat(budgetVers.get(1).getId(), is(equalTo(2L)));
		assertThat(budgetVers.get(2).getId(), is(equalTo(3L)));
		assertBudgetVer(budgetVers.get(0));		
	}
	
	
	@Test
	public void existingBudgetVerMayBeFoundById() {
		when(mockBudgetVerRepository.selectById(1L)).thenReturn(newBudgetVer(1L));		
		BudgetVer budgetVer = budgetVerService.findById(1L);		
		verify(mockBudgetVerRepository, times(1)).selectById(1L);
		assertThat(budgetVer, is(not(nullValue())));
		assertBudgetVer(budgetVer);
	}
	

	@Test
	public void existingBudgetVerMayBeFoundByName() {
		when(mockBudgetVerRepository.selectByName(MODULE_NAME_RU)).thenReturn(newBudgetVer(1L));		
		BudgetVer budgetVer = budgetVerService.findByName(MODULE_NAME_RU);		
		verify(mockBudgetVerRepository, times(1)).selectByName(MODULE_NAME_RU);
		assertThat(budgetVer, is(not(nullValue())));
		assertBudgetVer(budgetVer);
	}
	
	
	@Test
	public void newBudgetVerMayBeCreated() {
		BudgetVer origBudgetVer = newBudgetVer(null);
		when(mockBudgetVerRepository.insert(origBudgetVer)).thenReturn(newBudgetVer(1L));
		
		BudgetVer budgetVer = budgetVerService.create(origBudgetVer);		
		verify(mockBudgetVerRepository, times(1)).insert(origBudgetVer);		
		assertThat(budgetVer.getId(), is(not(nullValue())));
		assertBudgetVer(budgetVer);
	}

	
	@Test
	public void existingBudgetVerMayBeUpdated() {
		BudgetVer origBudgetVer = newBudgetVer(1L);		
		when(mockBudgetVerRepository.selectById(1L)).thenReturn(origBudgetVer);
		when(mockBudgetVerRepository.update(anyObject())).then(returnsFirstArg());
		
		BudgetVer budgetVer = budgetVerService.update(origBudgetVer);		
		verify(mockBudgetVerRepository, times(1)).selectById(1L);
		verify(mockBudgetVerRepository, times(1)).update(origBudgetVer);
		assertThat(budgetVer, is(not(nullValue())));
		assertBudgetVer(budgetVer);
	}

	
	@Test
	public void existingBudgetVerMayBeDeleted() {
		when(mockBudgetVerRepository.selectById(1L)).thenReturn(newBudgetVer(1L));
		when(mockBudgetVerRepository.delete(anyLong())).thenReturn(true);		
		
		boolean result = budgetVerService.delete(1L);		
		verify(mockBudgetVerRepository, times(1)).delete(1L);
		assertThat(result, is(equalTo(true)));
	}

	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfBudgetVerIdIsNull() {		
		budgetVerService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfBudgetVerNameIsNull() {
		budgetVerService.findByName(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfBudgetVerIsNull() {		
		budgetVerService.create(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfBudgetVerIsNull() {		
		budgetVerService.update(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDeleteMethodIfBudgetVerIdIsNull() {
		budgetVerService.delete(null);		
	}


	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfBudgetVerHasNotNullId() {		
		budgetVerService.create(newBudgetVer(1L));		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfBudgetVerHasNullId() {		
		budgetVerService.update(newBudgetVer(null));		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfBudgetVerIdIsNotExist() {		
		when(mockBudgetVerRepository.selectById(anyLong())).thenReturn(null);		
		budgetVerService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failUpdateMethodIfBudgetVerdIdIsNotExist() {		
		when(mockBudgetVerRepository.selectById(anyLong())).thenReturn(null);		
		budgetVerService.update(newBudgetVer(1L));
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void failDeleteMethodIfBudgetVerdIdIsNotExist() {		
		when(mockBudgetVerRepository.selectById(anyLong())).thenReturn(null);		
		budgetVerService.delete(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfBudgetVerNameIsNotExist() {		
		when(mockBudgetVerRepository.selectByName(anyString())).thenReturn(null);		
		budgetVerService.findByName("Бла бла бла");
	}
}
