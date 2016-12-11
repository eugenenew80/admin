package kz.ecc.isbp.admin.common.repository;

import kz.ecc.isbp.admin.common.entity.ability.HasId;

public abstract class AbstractReadOnlyRepository<T extends HasId> extends AbstractRepository<T> {
	public T insert(T entity) { throw new UnsupportedOperationException(); }	
	public T update(T entity) { throw new UnsupportedOperationException(); }	
	public boolean delete(Long entityId) { throw new UnsupportedOperationException(); }
}
