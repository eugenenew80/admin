package kz.ecc.isbp.admin.common.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class DefaultQueryImpl implements Query {
	private final String where;
	private final Map<String, Object> params;
	
	public String where() {
		return where;
	}
	
	public Map<String, Object> params() {
		return Collections.unmodifiableMap(params);
	}
	
	
	private DefaultQueryImpl(Map<String, Object> fields) {
		where = fields.keySet().stream()
			.map( key -> "t." + key  + (fields.get(key) instanceof String ? " like " : " = ")  + ":" + key.replace(".", "_"))
			.collect(Collectors.joining(" and "))
			.toString();
				
		params = fields.keySet().stream().collect(Collectors.toMap(key -> key.replace(".", "_"), fields::get));
	}
	
	
	public static QueryBuilder builder() {
		return new QueryBuliderImpl();
	}
	

	public static class QueryBuliderImpl implements QueryBuilder {
		public QueryBuliderImpl setParameter(String field, Object value) {
			if (value!=null)
				params.put(field, value); 
			return this;
		}
		
		public Query build() {
			return new DefaultQueryImpl(params);
		}
		
		private final Map<String, Object> params = new HashMap<>();
	}	
}
