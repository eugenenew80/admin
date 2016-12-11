package kz.ecc.isbp.admin.common.service;

import java.util.Date;
import java.util.List;
import kz.ecc.isbp.admin.common.entity.ability.*;
import kz.ecc.isbp.admin.common.exception.EntityNotFoundException;
import kz.ecc.isbp.admin.common.exception.RepositoryNotFoundException;
import kz.ecc.isbp.admin.common.exception.InvalidArgumentException;
import kz.ecc.isbp.admin.common.repository.Query;
import kz.ecc.isbp.admin.common.repository.Repository;

public abstract class AbstractEntityService<T extends HasId> implements EntityService<T> {

    public AbstractEntityService(Repository<T> repository) {
        this.repository = repository;
    }


    public List<T> findAll() {
		if (repository==null)
			throw new RepositoryNotFoundException();
		
		return repository.selectAll();
	}

	
	public List<T> find(Query query) {
		return repository.select(query);
	}


	public T findById(Long entityId) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entityId==null)
			throw new InvalidArgumentException();
		
		T entity = repository.selectById(entityId);
		if (entity==null)
			throw new EntityNotFoundException(entityId);
		
		return entity;
	}
	

	public T findByName(String entityName) {
		if (repository==null)
			throw new RepositoryNotFoundException();
		
		if (entityName==null)
			throw new InvalidArgumentException();
		
		T entity = repository.selectByName(entityName);
		if (entity==null)
			throw new EntityNotFoundException(entityName);

		return entity;
	}
	
	
	public T create(T entity) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entity==null) 
			throw new InvalidArgumentException();
		
		if (entity.getId()!=null)
			throw new InvalidArgumentException(entity);
			
		if (entity instanceof HasDates) {
			((HasDates) entity).setCreateDate(new Date());
			((HasDates) entity).setUpdateDate(new Date());
		}
		
		return repository.insert(entity);
	}

	
	public T update(T entity) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entity==null) 
			throw new InvalidArgumentException();
		
		if (entity.getId()==null) 
			throw new InvalidArgumentException(entity);

		T currentEntity = findById(entity.getId());
		if (currentEntity==null)
			throw new EntityNotFoundException(entity.getId());

		if (entity instanceof HasDates) {
			((HasDates) entity).setCreateDate( ((HasDates)currentEntity).getCreateDate() );
			((HasDates) entity).setUpdateDate(new Date());
		}
		
		return repository.update(entity);
	}

	
	public boolean delete(Long entityId) {
		if (repository==null)
			throw new RepositoryNotFoundException();

		if (entityId==null) 
			throw new InvalidArgumentException();		
		
		T currentEntity = findById(entityId);
		if (currentEntity==null)
			throw new EntityNotFoundException(entityId);
		
		return repository.delete(entityId); 
	}	
	

	private Repository<T> repository;
}
