package kz.ecc.isbp.admin.common.service;

import java.util.List;
import kz.ecc.isbp.admin.common.entity.ability.HasId;
import kz.ecc.isbp.admin.common.repository.Query;

public interface EntityService<T extends HasId> {
	List<T> findAll();
	
	List<T> find(Query query);
	
	T findById(Long entityId);
    
	T findByName(String entityName);
	
	T create(T entity);

	T update(T entity);

    boolean delete(Long entityId);
}
