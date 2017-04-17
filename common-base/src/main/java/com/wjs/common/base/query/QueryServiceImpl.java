package com.wjs.common.base.query;

import com.wjs.common.base.base.BaseDTO;
import com.wjs.common.base.base.BasePageResults;
import com.wjs.common.base.base.BaseQueryBuilder;
import com.wjs.common.base.spring.SpringBeanContext;
import com.wjs.common.base.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by panqingqing on 16/6/23.
 */
public class QueryServiceImpl implements QueryService {

    private Map<String, String> queryBuilderMap = new HashMap<String, String>();//通过DTO找到对应的builder实例

    @Override
    public <DTO extends BaseDTO> BasePageResults<DTO> queryDTO(DTO dto) {
        try {
            BaseQueryBuilder baseQueryBuilder = generateQueryBuilder(dto);
            ClassUtil.useGetCallSet(baseQueryBuilder, dto);
            BasePageResults basePageResults = new BasePageResults();
            basePageResults.setPage(baseQueryBuilder);
            basePageResults.setResults(baseQueryBuilder.getResults(basePageResults, dto));
            return basePageResults;
        } catch (Throwable throwable) {
            return new BasePageResults(throwable);
        }
    }

    private <DTO extends BaseDTO> BaseQueryBuilder generateQueryBuilder(DTO dto) {
        String key = dto.getClass().getName();
        String queryBuilder = queryBuilderMap.get(key);
        return (BaseQueryBuilder) SpringBeanContext.getBean(queryBuilder);
    }

    public void setQueryBuilderMap(Map<String, String> queryBuilderMap) {
        this.queryBuilderMap = queryBuilderMap;
    }
}
