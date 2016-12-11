package kz.ecc.isbp.admin.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import java.util.*;
import static kz.ecc.isbp.admin.helper.EntitiesHelper.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.nsi.entity.FinYear;
import kz.ecc.isbp.admin.nsi.repository.FinYearRepository;
import kz.ecc.isbp.admin.nsi.service.FinYearService;
import kz.ecc.isbp.admin.nsi.service.impl.FinYearServiceImpl;


public class FinYearServiceTest {
	FinYearRepository mockFinYearRepository;
	FinYearService finYearService;
	
	
	@Before
	public void setUp() throws Exception {
		mockFinYearRepository = mock(FinYearRepository.class);
		finYearService = new FinYearServiceImpl(mockFinYearRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListFinYearsMayBeFound() {
		when(mockFinYearRepository.selectAll()).thenReturn(Arrays.asList(newFinYear(1L), newFinYear(2L), newFinYear(3L) ));
		
		List<FinYear> finYears = finYearService.findAll();		
		verify(mockFinYearRepository, times(1)).selectAll();
		assertThat(finYears, is(not(nullValue())));
		assertThat(finYears, is(not(empty())));
		assertThat(finYears, hasSize(3));
		assertThat(finYears.get(0).getId(), is(equalTo(1L)));
		assertThat(finYears.get(1).getId(), is(equalTo(2L)));
		assertThat(finYears.get(2).getId(), is(equalTo(3L)));
		assertFinYear(finYears.get(0));		
	}
	
	
	@Test
	public void existingFinYearMayBeFoundById() {
		when(mockFinYearRepository.selectById(1L)).thenReturn(newFinYear(1L));		
		FinYear finYear = finYearService.findById(1L);		
		verify(mockFinYearRepository, times(1)).selectById(1L);
		assertThat(finYear, is(not(nullValue())));
		assertFinYear(finYear);
	}
	


	//Unsupported operations
	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryCreateNewFinYear() {
		finYearService.create(newFinYear(null));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryUpdateFinYear() {
		finYearService.update(newFinYear(1L));		
	}

	
	@Test(expected=UnsupportedOperationException.class)
	public void failTryDeleteFinYear() {
		finYearService.delete(1L);		
	}
	
	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIffinYearIdIsNull() {		
		finYearService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIffinYearNameIsNull() {
		finYearService.findByName(null);		
	}


	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIffinYearIdIsNotExist() {		
		when(mockFinYearRepository.selectById(anyLong())).thenReturn(null);		
		finYearService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIffinYearNameIsNotExist() {		
		when(mockFinYearRepository.selectByName(anyString())).thenReturn(null);		
		finYearService.findByName("Bla bla bla");
	}
}
