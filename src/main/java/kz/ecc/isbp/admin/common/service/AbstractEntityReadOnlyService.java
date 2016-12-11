package kz.ecc.isbp.admin.common.service;

import kz.ecc.isbp.admin.common.entity.ability.*;
import kz.ecc.isbp.admin.common.repository.Repository;

public abstract class AbstractEntityReadOnlyService<T extends HasId> extends AbstractEntityService<T> {
	public AbstractEntityReadOnlyService(Repository<T> repository) {
		super(repository);
	}

	public T create(T entity) { throw new UnsupportedOperationException(); }
	public T update(T entity) { throw new UnsupportedOperationException(); }
	public boolean delete(Long entityId) { throw new UnsupportedOperationException(); }
}
