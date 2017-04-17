package com.wjs.common.base.rpt;

import com.wjs.common.base.db.PageResults;
import com.wjs.common.base.db.RowMapper;
import com.wjs.common.base.util.CloseableUtil;
import org.hibernate.*;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by panqingqing on 16/1/11.
 */
@Repository
public abstract class BaseRptImpl<Entity, PK extends Serializable> implements BaseRpt<Entity, PK> {

    protected Class<Entity> entityClass;
    @Autowired
    private SessionFactory sessionFactory;

    protected Class getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return
     * @Title: getSession
     * @Description: getCurrentSession 会自动关闭session,使用的是当前的session事务
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * @return
     * @Title: getNewSession
     * @Description: openSession 需要手动关闭session,是打开了一个新的session
     */
    public Session getNewSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void lock(Entity entity) {
        LockMode currentLockMode = getSession().getCurrentLockMode(entity);
    }

    @Override
    public void save(Entity entity) {
        getSession().save(entity);
    }

    @Override
    public void saveList(Collection<Entity> entities) {
        if (entities != null && entities.size() != 0) {
            for (Entity entity : entities) {
                save(entity);
            }
        }
    }

    @Override
    public void saveOrUpdate(Entity entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void saveOrUpdateList(Collection<Entity> entities) {
        if (entities != null && entities.size() != 0) {
            for (Entity entity : entities) {
                saveOrUpdate(entity);
            }
        }
    }

    @Override
    public void persist(Entity entity) {
        getSession().persist(entity);
    }

    @Override
    public void delete(Entity entity) {
        getSession().delete(entity);
    }

    @Override
    public boolean deleteById(PK id) {
        Entity entity = getById(id);
        if (null == entity) {
            return false;
        }
        delete(entity);
        return true;
    }

    @Override
    public void deleteAll(Collection<Entity> entities) {
        for (Object entity : entities) {
            getSession().delete(entity);
        }
    }

    @Override
    public void update(Entity entity) {
        getSession().update(entity);
    }

    @Override
    public Entity loadById(PK id) {
        return (Entity) getSession().load(getEntityClass(), id);
    }

    @Override
    public Entity getById(PK id) {
        return (Entity) getSession().get(getEntityClass(), id);
    }

    @Override
    public Entity getAndLockById(PK id) {
        return (Entity) getSession().get(getEntityClass(), id, LockOptions.UPGRADE);
    }

    @Override
    public <T> T getById(Class<T> cls, PK id) {
        return (T) getSession().get(cls, id);
    }

    @Override
    public boolean contains(Entity entity) {
        return getSession().contains(entity);
    }

    @Override
    public void queryHqlExecuteUpdate(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    @Override
    public void querySqlExecuteUpdate(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    @Override
    public Entity getByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Entity) query.uniqueResult();
    }

    @Override
    public Entity getBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Entity) query.uniqueResult();
    }

    @Override
    public List<Entity> getListByIds(Long[] ids) {
        List<Entity> entities = new ArrayList<Entity>();
        for (Long id : ids) {
            entities.add(getById((PK) id));
        }
        return entities;
    }

    @Override
    public List<Entity> getListByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    @Override
    public List<Entity> getListBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    @Override
    public <Entity> List<Entity> queryList(Object... values) {
        Query query = this.getSession().createQuery("from " + getEntityClass().getName());
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    @Override
    public List<?> findListBySql(final String sql, final RowMapper map, final Object... values) {
        final List list = new ArrayList();
        Work jdbcWork = new Work() {
            public void execute(Connection connection) throws SQLException {
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    ps = connection.prepareStatement(sql);
                    for (int i = 0; i < values.length; i++) {
                        setParameter(ps, i, values[i]);
                    }
                    rs = ps.executeQuery();
                    int index = 0;
                    while (rs.next()) {
                        Object obj = map.mapRow(rs, index++);
                        list.add(obj);
                    }
                } finally {
                    CloseableUtil.close(rs, ps);
                }
            }
        };
        getSession().doWork(jdbcWork);
        return list;
    }

    @Override
    public Long countByHql(String hql, Object... values) {
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Long) query.uniqueResult();
    }

    @Override
    public PageResults<Entity> findPageByFetchedHql(String hql, String countHql, int pageNo, int pageSize, Object... values) {
        PageResults<Entity> retValue = new PageResults<Entity>();
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        int currentPage = pageNo > 1 ? pageNo : 1;
        retValue.setCurrentPage(currentPage);
        retValue.setPageSize(pageSize);
        if (countHql == null) {
            ScrollableResults results = query.scroll();
            results.last();
            retValue.setTotalCount(results.getRowNumber() + 1);// 设置总记录数
        } else {
            Long count = countByHql(countHql, values);
            retValue.setTotalCount(count.intValue());
        }
        retValue.resetPageNo();
        List<Entity> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
        if (itemList == null) {
            itemList = new ArrayList<Entity>();
        }
        retValue.setResults(itemList);
        return retValue;
    }

    @Override
    public void evict(Entity entity) {
        getSession().evict(entity);
    }

    @Override
    public void refresh(Entity entity) {
        getSession().refresh(entity);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public void clear() {
        getSession().clear();
    }


    private void setParameter(PreparedStatement ps, int pos, Object data) throws SQLException {
        if (data == null) {
            ps.setNull(pos + 1, Types.VARCHAR);
            return;
        }
        Class dataCls = data.getClass();
        if (String.class.equals(dataCls)) {
            ps.setString(pos + 1, (String) data);
        } else if (boolean.class.equals(dataCls)) {
            ps.setBoolean(pos + 1, ((Boolean) data));
        } else if (int.class.equals(dataCls)) {
            ps.setInt(pos + 1, (Integer) data);
        } else if (double.class.equals(dataCls)) {
            ps.setDouble(pos + 1, (Double) data);
        } else if (java.util.Date.class.equals(dataCls)) {
            java.util.Date val = (java.util.Date) data;
            ps.setTimestamp(pos + 1, new Timestamp(val.getTime()));
        } else if (BigDecimal.class.equals(dataCls)) {
            ps.setBigDecimal(pos + 1, (BigDecimal) data);
        } else {
            ps.setObject(pos + 1, data);
        }
    }
}
