package kz.ecc.isbp.admin.common.repository;

public interface QueryBuilder {
	 QueryBuilder setParameter(String field, Object value);
	 Query build();
}
