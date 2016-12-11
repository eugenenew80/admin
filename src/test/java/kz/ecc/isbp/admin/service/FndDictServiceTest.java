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
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.repository.DictRepository;
import kz.ecc.isbp.admin.fnd.service.DictService;
import kz.ecc.isbp.admin.fnd.service.impl.DictServiceImpl;


public class FndDictServiceTest {
	DictRepository mockDictRepository;
	DictService dictService;
	
	
	@Before
	public void setUp() throws Exception {
		mockDictRepository = mock(DictRepository.class);
		dictService = new DictServiceImpl(mockDictRepository);
	}

	
	@After
	public void tearDown() throws Exception {}
	
	
	//Success cases
	
	@Test
	public void theListdictsMayBeFound() {
		when(mockDictRepository.selectAll()).thenReturn(Arrays.asList(newDict(1L), newDict(2L), newDict(3L) ));
		
		List<Dict> dicts = dictService.findAll();		
		verify(mockDictRepository, times(1)).selectAll();
		assertThat(dicts, is(not(nullValue())));
		assertThat(dicts, is(not(empty())));
		assertThat(dicts, hasSize(3));
		assertThat(dicts.get(0).getId(), is(equalTo(1L)));
		assertThat(dicts.get(1).getId(), is(equalTo(2L)));
		assertThat(dicts.get(2).getId(), is(equalTo(3L)));
		assertDict(dicts.get(0));		
	}
	
	
	@Test
	public void existingdictMayBeFoundById() {
		when(mockDictRepository.selectById(1L)).thenReturn(newDict(1L));		
		Dict dict = dictService.findById(1L);		
		verify(mockDictRepository, times(1)).selectById(1L);
		assertThat(dict, is(not(nullValue())));
		assertDict(dict);
	}
	

	@Test
	public void existingdictMayBeFoundByName() {
		when(mockDictRepository.selectByName(DICT_NAME_RU)).thenReturn(newDict(1L));		
		Dict dict = dictService.findByName(DICT_NAME_RU);		
		verify(mockDictRepository, times(1)).selectByName(DICT_NAME_RU);
		assertThat(dict, is(not(nullValue())));
		assertDict(dict);
	}
	
	
	@Test
	public void newDictMayBeCreated() {
		Dict origdict = newDict(null);
		when(mockDictRepository.insert(origdict)).thenReturn(newDict(1L));
		
		Dict dict = dictService.create(origdict);		
		verify(mockDictRepository, times(1)).insert(origdict);		
		assertThat(dict.getId(), is(not(nullValue())));
		assertDict(dict);
	}

	
	@Test
	public void existingdictMayBeUpdated() {
		Dict origdict = newDict(1L);		
		when(mockDictRepository.selectById(1L)).thenReturn(origdict);
		when(mockDictRepository.update(anyObject())).then(returnsFirstArg());
		
		Dict dict = dictService.update(origdict);		
		verify(mockDictRepository, times(1)).selectById(1L);
		verify(mockDictRepository, times(1)).update(origdict);
		assertThat(dict, is(not(nullValue())));
		assertDict(dict);
	}

	
	@Test
	public void existingdictMayBeDeleted() {
		when(mockDictRepository.selectById(1L)).thenReturn(newDict(1L));
		when(mockDictRepository.delete(anyLong())).thenReturn(true);		
		
		boolean result = dictService.delete(1L);		
		verify(mockDictRepository, times(1)).delete(1L);
		assertThat(result, is(equalTo(true)));
	}

	
	
	//Call with invalid arguments
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByIdMethodIfdictIdIsNull() {		
		dictService.findById(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failFindByNameMethodIfdictNameIsNull() {
		dictService.findByName(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfdictIsNull() {		
		dictService.create(null);		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfdictIsNull() {		
		dictService.update(null);		
	}

	
	@Test(expected=InvalidArgumentException.class)
	public void failDeleteMethodIfdictIdIsNull() {
		dictService.delete(null);		
	}


	@Test(expected=InvalidArgumentException.class)
	public void failCreateMethodIfdictHasNotNullId() {		
		dictService.create(newDict(1L));		
	}
	
	
	@Test(expected=InvalidArgumentException.class)
	public void failUpdateMethodIfdictHasNullId() {		
		dictService.update(newDict(null));		
	}

	
		
	//Entity not found
	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByIdMethodIfdictIdIsNotExist() {		
		when(mockDictRepository.selectById(anyLong())).thenReturn(null);		
		dictService.findById(1L);
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failUpdateMethodIfdictdIdIsNotExist() {		
		when(mockDictRepository.selectById(anyLong())).thenReturn(null);		
		dictService.update(newDict(1L));
	}
	
	
	@Test(expected = EntityNotFoundException.class)
	public void failDeleteMethodIfdictdIdIsNotExist() {		
		when(mockDictRepository.selectById(anyLong())).thenReturn(null);		
		dictService.delete(1L);		
	}

	
	@Test(expected = EntityNotFoundException.class)
	public void failFindByNameMethodIfdictNameIsNotExist() {		
		when(mockDictRepository.selectByName(anyString())).thenReturn(null);		
		dictService.findByName("Бла бла бла");
	}
}
