package com.wjs.common.base.query;

import com.wjs.common.base.base.BaseDTO;
import com.wjs.common.base.base.BasePageResults;

/**
 * @author panqingqing
 * @title 统一查询接口
 * @title Created by panqingqing on 16/6/23.
 */
public interface QueryService {

    <DTO extends BaseDTO> BasePageResults<DTO> queryDTO(DTO dto);
}
