package com.wjs.common.base.web;

import com.wjs.common.base.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

/**
 * Created by panqingqing on 16/1/11.
 */
@Controller
public abstract class BaseControllerImpl<Entity, PK extends Serializable> implements BaseController<Entity, PK> {

    protected final String basePathList = getBasePath() + "list";
    protected final String basePathPageList = getBasePath() + "page_list";
    protected final String basePathadd = getBasePath() + "add";
    protected final String basePathUpdate = getBasePath() + "update";
    protected final String basePathView = getBasePath() + "view";
    protected final String baseRedirectPath = getRedirectPath();

    protected abstract String getBasePath();

    protected abstract String getRedirectPath();

    protected abstract BaseService<Entity, Serializable> getBaseService();

    @Override
    @RequestMapping(params = "action=toList")
    public String toList(HttpServletRequest request, HttpServletResponse response) {
        List<Entity> list = getBaseService().queryList();
        request.setAttribute("list", list);
        return basePathList;
    }

    @Override
    @RequestMapping(params = "action=toPageList")
    public String toPageList(HttpServletRequest request, HttpServletResponse response) {
        return basePathPageList;
    }

    @Override
    @RequestMapping(params = "action=toAdd")
    public String toAdd(HttpServletRequest request, HttpServletResponse response) {
        return basePathadd;
    }

    @Override
    @RequestMapping(params = "action=doAdd")
    public String doAdd(HttpServletRequest request, HttpServletResponse response, Entity entity) {
        getBaseService().save(entity);
        return baseRedirectPath;
    }

    @Override
    @RequestMapping(params = "action=toUpdate")
    public String toUpdate(HttpServletRequest request, HttpServletResponse response, PK id) {
        Entity entity = getBaseService().getById(id);
        request.setAttribute("entity", entity);
        return basePathUpdate;
    }

    @Override
    @RequestMapping(params = "action=doUpdate")
    public String doUpdate(HttpServletRequest request, HttpServletResponse response, Entity entity) {
        getBaseService().update(entity);
        return baseRedirectPath;
    }

    @Override
    @RequestMapping(params = "action=doDelete")
    public String doDelete(HttpServletRequest request, HttpServletResponse response, PK id) {
        getBaseService().deleteById(id);
        return baseRedirectPath;
    }

    @Override
    @RequestMapping(params = "action=doView")
    public String doView(HttpServletRequest request, HttpServletResponse response, PK id) {
        Entity entity = getBaseService().getById(id);
        request.setAttribute("entity", entity);
        return basePathView;
    }
}
