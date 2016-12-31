package kz.ecc.isbp.admin.helper;

import java.sql.Connection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import kz.ecc.isbp.admin.auth.entity.Permission;
import kz.ecc.isbp.admin.auth.entity.Role;
import kz.ecc.isbp.admin.auth.entity.RoleModule;
import kz.ecc.isbp.admin.auth.entity.RoleModuleDict;
import kz.ecc.isbp.admin.auth.entity.User;
import kz.ecc.isbp.admin.emf.entity.BudgetLevel;
import kz.ecc.isbp.admin.emf.entity.BudgetType;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.entity.Module;
import kz.ecc.isbp.admin.nsi.entity.BudgetRequestType;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.entity.BudgetVerStatus;
import kz.ecc.isbp.admin.nsi.entity.FinYear;

public class DBUnitHelper {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager em;
	private Connection connection;

	public DBUnitHelper() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("isbp_test"); 
		em = entityManagerFactory.createEntityManager();
		Session session = em.unwrap(Session.class);
		connection = ((SessionImpl) session).connection();
		//connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/isbp_test?user=postgres&password=1");
	}
	
	public EntityManager getEntityManager() {
		return em;
	}


	public void closeDatabase() throws Exception {
		if( connection != null) {
			connection.close();
			connection = null;
		}
		
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}
	
	public void beginTransaction() {
		em.getTransaction().begin();
	}
	
	
	public void commitTransaction() {
		em.getTransaction().commit();
	}
	
	
	public void commitTransaction(boolean clearContext) {
		commitTransaction();
		if (clearContext ) { 
			em.clear();
			clearCache();
		}
	}


	public void insert(List<DataSetLoader> loaders) throws Exception {
		for (DataSetLoader loader : loaders)
			loader.cleanAndInsert(connection);
	
		clearCache();
	}

	public void delete(List<DataSetLoader> loaders) throws Exception {
		for (DataSetLoader loader : loaders)
			loader.deleteAll(connection);
	
		clearCache();
	}

	
	private void clearCache() {
		em.getEntityManagerFactory().getCache().evict(User.class);
		em.getEntityManagerFactory().getCache().evict(Role.class);
		em.getEntityManagerFactory().getCache().evict(Permission.class);
		em.getEntityManagerFactory().getCache().evict(Module.class);
		em.getEntityManagerFactory().getCache().evict(Dict.class);
		em.getEntityManagerFactory().getCache().evict(RoleModule.class);
		em.getEntityManagerFactory().getCache().evict(RoleModuleDict.class);
		em.getEntityManagerFactory().getCache().evict(BudgetType.class);
		em.getEntityManagerFactory().getCache().evict(BudgetLevel.class);
		em.getEntityManagerFactory().getCache().evict(FinYear.class);
		em.getEntityManagerFactory().getCache().evict(BudgetVerStatus.class);
		em.getEntityManagerFactory().getCache().evict(BudgetRequestType.class);
		em.getEntityManagerFactory().getCache().evict(BudgetVer.class);
	}
}
