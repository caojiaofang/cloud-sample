package com.cloud.base;

import java.util.Map;

import com.cloud.mybatis.page.Pager;


public class BaseController {

    @SuppressWarnings("rawtypes")
    public Pager getPager(Map map) {

        Pager pager = new Pager();
        pager.setPageIndex((int) map.get("pageIndex"));
        pager.setPageSize( (int) map.get("pageSize"));
        return pager;
    }
}
