package com.wjs.common.base.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BasePageResults<DTO extends BaseDTO> extends BaseObject {

    private int pageNo;//下一页
    private int pageSize = 10;//每页个个数
    private int count;//总数
    private int currentPage;//当前页数
    private int pageCount;//总页数
    private List<DTO> results;//记录

    public BasePageResults() {
    }

    public BasePageResults(Throwable throwable) {
        super(throwable);
    }

    public void setPage(BaseQueryBuilder baseQueryBuilder) {
        BasePageResults basePageResults = baseQueryBuilder.getBasePageResults();
        this.count = baseQueryBuilder.getCount();
        if (basePageResults == null) return;
        this.pageNo = basePageResults.getPageNo() == 0 ? 1 : basePageResults.getPageNo();
        this.pageSize = basePageResults.getPageSize();
        this.currentPage = (this.pageNo == 1) ? 0 : (pageNo - 1) * pageSize;
        this.pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }

    public void setResults(List<DTO> results) {
        this.results = results;
    }
}
