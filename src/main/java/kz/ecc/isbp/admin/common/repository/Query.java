package kz.ecc.isbp.admin.common.repository;

import java.util.Map;

public interface Query {
	String where();
	Map<String, Object> params();
}
