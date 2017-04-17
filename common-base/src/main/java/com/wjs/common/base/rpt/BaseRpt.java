package com.wjs.common.base.rpt;

import com.wjs.common.base.db.PageResults;
import com.wjs.common.base.db.RowMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by panqingqing on 16/1/11.
 */
public interface BaseRpt<Entity, PK extends Serializable> {

    void lock(Entity entity);

    void save(Entity entity);

    void saveList(Collection<Entity> entities);

    void saveOrUpdate(Entity entity);

    void saveOrUpdateList(Collection<Entity> entities);

    void persist(Entity entity);

    void delete(Entity entity);

    boolean deleteById(PK id);

    void deleteAll(Collection<Entity> entities);

    void update(Entity entity);

    Entity loadById(PK id);

    Entity getById(PK id);

    Entity getAndLockById(PK id);

    <T> T getById(Class<T> cls, PK id);

    boolean contains(Entity entity);

    void queryHqlExecuteUpdate(String hqlString, Object... values);

    void querySqlExecuteUpdate(String sqlString, Object... values);

    Entity getByHQL(String hqlString, Object... values);

    Entity getBySQL(String sqlString, Object... values);

    List<Entity> getListByIds(Long[] ids);

    List<Entity> getListByHQL(String hqlString, Object... values);

    List<Entity> getListBySQL(String sqlString, Object... values);

    <Entity> List<Entity> queryList(Object... values);

    List<?> findListBySql(final String sql, final RowMapper map, final Object... values);

    Long countByHql(String hql, Object... values);

    PageResults<Entity> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize, Object... values);

    void evict(Entity entity);

    void refresh(Entity entity);

    void flush();

    void clear();
}
