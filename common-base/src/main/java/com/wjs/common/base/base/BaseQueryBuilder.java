package com.wjs.common.base.base;

import com.wjs.common.base.util.BeanPropertiesUtil;
import com.wjs.common.base.util.ScanClassUtil;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Restrictions.*;

/**
 * Created by panqingqing on 16/6/23.
 */
public class BaseQueryBuilder<Entity extends BaseEntity> extends BaseDTO2 {

    protected Class<Entity> entityClass;
    @Autowired
    protected SessionFactory sessionFactory;//拿到session工厂
    protected DetachedCriteria detachedCriteria;//离线查询

    public BaseQueryBuilder() {
        super();
    }

    public BaseQueryBuilder(Throwable throwable) {
        super(throwable);
    }

    protected Class getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    //spring加载上下文会先调用该初始化方法.参考http://blog.csdn.net/topwqp/article/details/8681497
    @PostConstruct
    public void init() {
        //初始化
        detachedCriteria = DetachedCriteria.forClass(getEntityClass());
    }

    public void add(Criterion criterion) {
        detachedCriteria.add(criterion);
    }

    public void orderByProperty(boolean flag, String property) {
        addOrder(TRUE.equals(flag) ? asc(property) : desc(property));
    }

    public void addOrder(Order order) {
        detachedCriteria.addOrder(order);
    }

    public void setFetchMode(String associationPath, FetchMode mode) {
        detachedCriteria.setFetchMode(associationPath, mode);
    }

    public void setProjection(Projection projection) {
        detachedCriteria.setProjection(projection);
    }

    public void setResultTransformer(ResultTransformer resultTransformer) {
        detachedCriteria.setResultTransformer(resultTransformer);
    }

    public void createAlias(String associationPath, String alias) {
        detachedCriteria.createAlias(associationPath, alias);
    }

    public void createAlias(String associationPath, String alias, JoinType joinType) {
        detachedCriteria.createAlias(associationPath, alias, joinType);
    }

    public void createAlias(String associationPath, String alias, JoinType joinType, Criterion withClause) {
        detachedCriteria.createAlias(associationPath, alias, joinType, withClause);
    }

    public void setComment(String comment) {
        detachedCriteria.setComment(comment);
    }

    public void setLockMode(LockMode lockMode) {
        detachedCriteria.setLockMode(lockMode);
    }

    public void setLockMode(String alias, LockMode lockMode) {
        detachedCriteria.setLockMode(alias, lockMode);
    }


    public int getCount() {
        Session session = null;
        int count = 0;
        try {
            session = sessionFactory.openSession();
            List<Entity> list = detachedCriteria.getExecutableCriteria(session).list();
            if (list != null) count = list.size();
        } catch (HibernateException e) {
            throw new HibernateException("Hibernate数据查询操作异常:" + e.getMessage(), e);
        } finally {
            if (session != null) session.close();
        }
        return count;
    }

    public <DTO extends BaseDTO> List<DTO> getResults(BasePageResults basePageResults, DTO dto) {
        Session session = null;
        List<DTO> dtos = new ArrayList<DTO>();
        try {
            session = sessionFactory.openSession();
            Criteria executableCriteria = detachedCriteria.getExecutableCriteria(session);
            if (basePageResults.getPageNo() != 0) {
                executableCriteria.setFirstResult(basePageResults.getCurrentPage());
                executableCriteria.setMaxResults(basePageResults.getPageSize());
            } else {
                //防止加载太多,默认999条吧
                executableCriteria.setFirstResult(0);
                executableCriteria.setMaxResults(999);
            }
            List<Entity> list = executableCriteria.list();
            for (Entity entity : list) {
                dtos.add(makeDTO(entity, dto));
            }
        } catch (HibernateException e) {
            throw new HibernateException("Hibernate数据查询操作异常:" + e.getMessage(), e);
        } finally {
            if (session != null) session.close();
        }
        return dtos;
    }

    private <DTO extends BaseDTO> DTO makeDTO(Entity entity, DTO dto) {
        return (DTO) BeanPropertiesUtil.copyProperties(entity, dto.getClass());
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        add(eq("id", id));
    }

    @Override
    public void setCreateTime(Date createTime) {
        super.setCreateTime(createTime);
        add(eq("createTime", createTime));
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        super.setUpdateTime(updateTime);
        add(eq("updateTime", updateTime));
    }

    @Override
    public void setVersion(Long version) {
        super.setVersion(version);
        add(eq("version", version));
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        add(eq("name", name));
    }

    @Override
    public void setType(Integer type) {
        super.setType(type);
        add(eq("type", type));
    }

    @Override
    public void setPriority(Integer priority) {
        super.setPriority(priority);
        add(eq("priority", priority));
    }

    @Override
    public void setDescribe(String describe) {
        super.setDescribe(describe);
        add(eq("describe", describe));
    }

    @Override
    public void setMemo(String memo) {
        super.setMemo(memo);
        add(eq("memo", memo));
    }

    @Override
    public void setUrl(String url) {
        super.setUrl(url);
        add(eq("url", url));
    }

    @Override
    public void setMethod(String method) {
        super.setMethod(method);
        add(eq("method", method));
    }

    @Override
    public void setLogicDelete(Boolean logicDelete) {
        super.setLogicDelete(logicDelete);
        add(eq("logicDelete", logicDelete));
    }

    @Override
    public void setAmount(BigDecimal amount) {
        super.setAmount(amount);
        add(eq("amount", amount));
    }

    @Override
    public void setStatusVal(String statusVal) {
        super.setStatusVal(statusVal);
        add(eq("status", ScanClassUtil.getStatusMap().get(getEntityClass() + statusVal)));
    }

    @Override
    public void setIdArr(Long[] idArr) {
        super.setIdArr(idArr);
        add(in("id", idArr));
    }

    @Override
    public void setCodeArr(String[] codeArr) {
        super.setCodeArr(codeArr);
        add(in("code", codeArr));
    }

    @Override
    public void setErrorCodeArr(String[] errorCodeArr) {
        super.setErrorCodeArr(errorCodeArr);
        add(in("errorCode", errorCodeArr));
    }

    @Override
    public void setStatusArr(String[] statusArr) {
        super.setStatusArr(statusArr);
        List statuses = new ArrayList();
        for (String status : statusArr) {
            statuses.add(ScanClassUtil.getStatusMap().get(getEntityClass() + statusVal));
        }
        add(in("status", statuses));
    }

    @Override
    public void setExcludeStatusArr(Integer[] excludeStatusArr) {
        super.setExcludeStatusArr(excludeStatusArr);
    }

    @Override
    public void setBeginCreateTime(Date beginCreateTime) {
        super.setBeginCreateTime(beginCreateTime);
        add(ge("createTime", beginCreateTime));
    }

    @Override
    public void setEndCreateTime(Date endCreateTime) {
        super.setEndCreateTime(endCreateTime);
        add(le("createTime", endCreateTime));
    }

    @Override
    public void setBeginUpdateTime(Date beginUpdateTime) {
        super.setBeginUpdateTime(beginUpdateTime);
        add(ge("updateTime", beginUpdateTime));
    }

    @Override
    public void setEndUpdateTime(Date endUpdateTime) {
        super.setEndUpdateTime(endUpdateTime);
        add(le("updateTime", endUpdateTime));
    }

    @Override
    public void setOrderByIdAsc(Boolean orderByIdAsc) {
        super.setOrderByIdAsc(orderByIdAsc);
        addOrder(asc("id"));
    }

    @Override
    public void setOrderByIdDesc(Boolean orderByIdDesc) {
        super.setOrderByIdDesc(orderByIdDesc);
        addOrder(desc("id"));
    }

    @Override
    public void setOrderByAmountAsc(Boolean orderByAmountAsc) {
        super.setOrderByAmountAsc(orderByAmountAsc);
        addOrder(asc("amount"));
    }

    @Override
    public void setOrderByAmountDesc(Boolean orderByAmountDesc) {
        super.setOrderByAmountDesc(orderByAmountDesc);
        addOrder(desc("amount"));
    }

    @Override
    public void setOrderByMemberIdAsc(Boolean orderByMemberIdAsc) {
        super.setOrderByMemberIdAsc(orderByMemberIdAsc);
        addOrder(asc("memberId"));
    }

    @Override
    public void setOrderByMemberIdDesc(Boolean orderByMemberIdDesc) {
        super.setOrderByMemberIdDesc(orderByMemberIdDesc);
        addOrder(desc("memberId"));
    }

    @Override
    public void setOrderByNameAsc(Boolean orderByNameAsc) {
        super.setOrderByNameAsc(orderByNameAsc);
        addOrder(asc("name"));
    }

    @Override
    public void setOrderByNameDesc(Boolean orderByNameDesc) {
        super.setOrderByNameDesc(orderByNameDesc);
        addOrder(desc("name"));
    }

    @Override
    public void setOrderByMemberNameAsc(Boolean orderByMemberNameAsc) {
        super.setOrderByMemberNameAsc(orderByMemberNameAsc);
        addOrder(asc("memberName"));
    }

    @Override
    public void setOrderByMemberNameDesc(Boolean orderByMemberNameDesc) {
        super.setOrderByMemberNameDesc(orderByMemberNameDesc);
        addOrder(desc("memberName"));
    }

    @Override
    public void setOrderByCodeAsc(Boolean orderByCodeAsc) {
        super.setOrderByCodeAsc(orderByCodeAsc);
        addOrder(asc("code"));
    }

    @Override
    public void setOrderByCodeDesc(Boolean orderByCodeDesc) {
        super.setOrderByCodeDesc(orderByCodeDesc);
        addOrder(desc("code"));
    }

    @Override
    public void setOrderByErrorCodeAsc(Boolean orderByErrorCodeAsc) {
        super.setOrderByErrorCodeAsc(orderByErrorCodeAsc);
        addOrder(asc("errorCode"));
    }

    @Override
    public void setOrderByErrorCodeDesc(Boolean orderByErrorCodeDesc) {
        super.setOrderByErrorCodeDesc(orderByErrorCodeDesc);
        addOrder(desc("errorCode"));
    }

    @Override
    public void setOrderByErrorMsgAsc(Boolean orderByErrorMsgAsc) {
        super.setOrderByErrorMsgAsc(orderByErrorMsgAsc);
        addOrder(asc("errorMsg"));
    }

    @Override
    public void setOrderByErrorMsgDesc(Boolean orderByErrorMsgDesc) {
        super.setOrderByErrorMsgDesc(orderByErrorMsgDesc);
        addOrder(desc("errorMsg"));
    }

    @Override
    public void setOrderByPriorityAsc(Boolean orderByPriorityAsc) {
        super.setOrderByPriorityAsc(orderByPriorityAsc);
        addOrder(asc("priority"));
    }

    @Override
    public void setOrderByPriorityDesc(Boolean orderByPriorityDesc) {
        super.setOrderByPriorityDesc(orderByPriorityDesc);
        addOrder(desc("priority"));
    }

    @Override
    public void setOrderByTypeAsc(Boolean orderByTypeAsc) {
        super.setOrderByTypeAsc(orderByTypeAsc);
        addOrder(asc("type"));
    }

    @Override
    public void setOrderByTypeDesc(Boolean orderByTypeDesc) {
        super.setOrderByTypeDesc(orderByTypeDesc);
        addOrder(desc("type"));
    }

    @Override
    public void setOrderByStatusAsc(Boolean orderByStatusAsc) {
        super.setOrderByStatusAsc(orderByStatusAsc);
        addOrder(asc("status"));
    }

    @Override
    public void setOrderByStatusDesc(Boolean orderByStatusDesc) {
        super.setOrderByStatusDesc(orderByStatusDesc);
        addOrder(desc("status"));
    }

    @Override
    public void setOrderByCreateTimeAsc(Boolean orderByCreateTimeAsc) {
        super.setOrderByCreateTimeAsc(orderByCreateTimeAsc);
        addOrder(asc("createTime"));
    }

    @Override
    public void setOrderByCreateTimeDesc(Boolean orderByCreateTimeDesc) {
        super.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
        addOrder(desc("createTime"));
    }

    @Override
    public void setOrderByUpdateTimeAsc(Boolean orderByUpdateTimeAsc) {
        super.setOrderByUpdateTimeAsc(orderByUpdateTimeAsc);
        addOrder(asc("updateTime"));
    }

    @Override
    public void setOrderByUpdateTimeDesc(Boolean orderByUpdateTimeDesc) {
        super.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
        addOrder(desc("updateTime"));
    }

    //为了不是所有的公共类都有这个json转出
    public BasePageResults getBasePageResults() {
        return basePageResults;
    }
}
