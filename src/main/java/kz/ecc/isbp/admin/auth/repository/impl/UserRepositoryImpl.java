package kz.ecc.isbp.admin.auth.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.auth.repository.UserRepository;
import kz.ecc.isbp.admin.common.repository.AbstractRepository;

@Stateless
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {
	public UserRepositoryImpl() { setClazz(User.class); }

	public UserRepositoryImpl(EntityManager entityManager) {
		this();
		setEntityManager(entityManager);
	}

	public User selectByName(String entityName) {
		throw new UnsupportedOperationException();
	}
}
