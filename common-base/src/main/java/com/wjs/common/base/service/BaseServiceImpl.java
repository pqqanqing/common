package com.wjs.common.base.service;

import com.wjs.common.base.db.PageResults;
import com.wjs.common.base.db.RowMapper;
import com.wjs.common.base.rpt.BaseRpt;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by panqingqing on 16/1/11.
 */
@Service
public abstract class BaseServiceImpl<Entity, PK extends Serializable> implements BaseService<Entity, PK> {

    protected abstract String getListHql();

    protected abstract String getHql(Long id);

    protected abstract BaseRpt<Entity, Serializable> getBaseRpt();

    @Override
    public void lock(Entity entity) {
        getBaseRpt().lock(entity);
    }

    @Override
    public void save(Entity entity) {
        getBaseRpt().save(entity);
    }

    @Override
    public void saveList(Collection<Entity> entities) {
        getBaseRpt().saveList(entities);
    }

    @Override
    public void saveOrUpdate(Entity entity) {
        getBaseRpt().saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateList(Collection<Entity> entities) {
        getBaseRpt().saveOrUpdateList(entities);
    }

    @Override
    public void persist(Entity entity) {
        getBaseRpt().persist(entity);
    }

    @Override
    public void delete(Entity entity) {
        getBaseRpt().delete(entity);
    }

    @Override
    public boolean deleteById(PK id) {
        return getBaseRpt().deleteById(id);
    }

    @Override
    public void deleteAll(Collection<Entity> entities) {
        getBaseRpt().deleteAll(entities);
    }

    @Override
    public void update(Entity entity) {
        getBaseRpt().update(entity);
    }

    @Override
    public Entity loadById(PK id) {
        return getBaseRpt().loadById(id);
    }

    @Override
    public Entity getById(PK id) {
        return getBaseRpt().getById(id);
    }

    @Override
    public <T> T getById(Class<T> cls, PK id) {
        return getBaseRpt().getById(cls, id);
    }

    @Override
    public boolean contains(Entity entity) {
        return getBaseRpt().contains(entity);
    }

    @Override
    public void queryHqlExecuteUpdate(String hqlString, Object... values) {
        getBaseRpt().queryHqlExecuteUpdate(hqlString, values);
    }

    @Override
    public void querySqlExecuteUpdate(String sqlString, Object... values) {
        getBaseRpt().querySqlExecuteUpdate(sqlString, values);
    }

    @Override
    public Entity getByHQL(String hqlString, Object... values) {
        return getBaseRpt().getByHQL(hqlString, values);
    }

    @Override
    public Entity getBySQL(String sqlString, Object... values) {
        return getBaseRpt().getBySQL(sqlString, values);
    }

    @Override
    public List<Entity> getListByHQL(String hqlString, Object... values) {
        return getBaseRpt().getListByHQL(hqlString, values);
    }

    @Override
    public List<Entity> getListByHQL(Object... values) {
        return getBaseRpt().getListByHQL(getListHql(), values);
    }

    @Override
    public List<Entity> getListBySQL(String sqlString, Object... values) {
        return getBaseRpt().getListBySQL(sqlString, values);
    }

    @Override
    public <Entity> List<Entity> queryList(Object... values) {
        return getBaseRpt().queryList(values);
    }

    @Override
    public List<?> findListBySql(String sql, RowMapper map, Object... values) {
        return getBaseRpt().findListBySql(sql, map, values);
    }

    @Override
    public Long countByHql(String hql, Object... values) {
        return getBaseRpt().countByHql(hql, values);
    }

    @Override
    public PageResults<Entity> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize, Object... values) {
        return getBaseRpt().findPageByFetchedHql(hql, countHql, pageNo, pageSize, values);
    }

    @Override
    public void evict(Entity entity) {
        getBaseRpt().evict(entity);
    }

    @Override
    public void refresh(Entity entity) {
        getBaseRpt().refresh(entity);
    }

    @Override
    public void flush() {
        getBaseRpt().flush();
    }

    @Override
    public void clear() {
        getBaseRpt().clear();
    }
}
