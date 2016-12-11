package kz.ecc.isbp.admin.common.repository;

import java.util.List;

import kz.ecc.isbp.admin.common.entity.ability.HasId;

public interface Repository <T extends HasId> {
    List<T> selectAll();

    List<T> select(Query query);
    
    T selectById(Long entityId);
    
    T selectByName(String entityName);
    
    T insert(T entity);

    T update(T entity);

    boolean delete(Long entityId);
}
