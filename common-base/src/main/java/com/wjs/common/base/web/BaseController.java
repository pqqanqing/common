package com.wjs.common.base.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by panqingqing on 16/1/11.
 */
public interface BaseController<Entity, PK extends Serializable> {

    /**
     * @param request
     * @param response
     * @return
     * @Title: toList
     * @Description: 查询列表
     */
    String toList(HttpServletRequest request, HttpServletResponse response);

    /**
     * @param request
     * @param response
     * @return
     * @Title: toList
     * @Description: 查询分页列表
     */
    String toPageList(HttpServletRequest request, HttpServletResponse response);

    /**
     * @param request
     * @param response
     * @return
     * @Title: toAdd
     * @Description: 跳转到增加页面
     */
    String toAdd(HttpServletRequest request, HttpServletResponse response);

    /**
     * @param request
     * @param response
     * @param entity
     * @return
     * @Title: doAdd
     * @Description: 执行增加功能
     */
    String doAdd(HttpServletRequest request, HttpServletResponse response, Entity entity);

    /**
     * @param request
     * @param response
     * @param id
     * @return
     * @Title: toUpdate
     * @Description: 跳转到更新页面
     */
    String toUpdate(HttpServletRequest request, HttpServletResponse response, PK id);

    /**
     * @param request
     * @param response
     * @param entity
     * @return
     * @Title: doUpdate
     * @Description: 执行更新操作
     */
    String doUpdate(HttpServletRequest request, HttpServletResponse response, Entity entity);

    /**
     * @param request
     * @param response
     * @param id
     * @return
     * @Title: doDelete
     * @Description: 执行删除操作
     */
    String doDelete(HttpServletRequest request, HttpServletResponse response, PK id);

    /**
     * @param request
     * @param response
     * @param id
     * @return
     * @Title: doView
     * @Description: 执行查看详细内容
     */
    String doView(HttpServletRequest request, HttpServletResponse response, PK id);
}
