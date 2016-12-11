package kz.ecc.isbp.admin.common.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import kz.ecc.isbp.admin.common.entity.ability.HasId;

public abstract class AbstractRepository<T extends HasId> implements Repository<T> {

	public List<T> selectAll() {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() +  ".findAll", clazz);
		return query.getResultList();
	}


	public List<T> select(Query query) {
    	TypedQuery<T> typedQuery = getEntityManager().createQuery("select t from " + clazz.getSimpleName() + " t" + " where " + query.where(), clazz);
    	query.params().keySet().stream()
    			.forEach(it -> typedQuery.setParameter(it, query.params().get(it)) );
    	
    	return typedQuery.getResultList();
	}

	
	public T selectById(Long entityId) {
		return em.find(clazz, entityId);
	}

	
	public T selectByName(String entityName) {
		TypedQuery<T> query = em.createNamedQuery(clazz.getSimpleName() + ".findByName", clazz);
		query.setParameter("name", entityName);
		
		return query.getResultList().stream()
				.findFirst()
				.orElse(null);
	}
	
	
	public T insert(T entity) {
		em.persist(entity);
		return entity;
	}

	
	public T update(T entity) {
		em.merge(entity);		
		return entity;
	}
	

	public boolean delete(Long entityId) {
		T entity = em.find(clazz, entityId);
		if (entity!=null) {
			em.remove(entity);
			return true;
		}
		return false;
	}
	
	
	
	public EntityManager getEntityManager() {
		return em;
	}

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}	
		
	protected void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}	

	
	@PersistenceContext(unitName = "isbp")
	private EntityManager em;
	private Class<T> clazz;
}
