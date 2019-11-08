package com.cloud.base;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.context.WebContextUtil;
import com.cloud.mybatis.interceptor.PageBounds;
import com.cloud.mybatis.page.Pager;
import com.cloud.utils.tools.BeanCopyUtil;
import com.cloud.utils.tools.CollectionUtil;
import com.cloud.utils.tools.NumberUtil;


/**
 * 类描述：Handler基础类
 */
public abstract class BaseHandler {

	public <T> void convertDo2Vo(Pager pager, List<T> listVo) {

		if (CollectionUtil.isNEmpty(pager.getResult())) {
			for (int i = 0; i < pager.getResult().size(); i++) {
				BeanCopyUtil.copy(pager.getResult().get(i), listVo.get(i));
			}
		}
		pager.setResult(listVo);
	}

	public <T> List<T> convertDo2Vo(List<?> theDOList, Class<T> t) {

		List<T> list = new LinkedList<T>();

		for (int i = 0; i < theDOList.size(); i++) {
			T dest = null;
			try {
				dest = t.newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException("创建对象失败", e);
			}
			BeanCopyUtil.copy(theDOList.get(i), dest);
			list.add(dest);
		}
		return list;

	}

	public <T> PagerView<T> getPagerView(int total, List<?> theDOList, Class<T> t) {
		List<T> list = this.convertDo2Vo(theDOList, t);
		PagerView<T> pagerView = new PagerView<T>();
		pagerView.setTotal(total);
		pagerView.setRows(list);
		return pagerView;
	}

	public PageBounds getPageBounds(Pager pager) {
		PageBounds pageBounds = new PageBounds(pager.getRowStart(), pager.getPageSize());
		return pageBounds;
	}

	/**
	 * 方法描述：获取当前登陆用户用户账号
	 * 
	 * @return String
	 */
	public String getUserAcct() {
		String userAcct = WebContextUtil.getUserContext().getUserAcct();
		if (StringUtils.isEmpty(userAcct)) {
			userAcct = "";
		}
		return userAcct;
	}

	public Pager getPager(Map<String, Object> params) {
		Pager pager = new Pager();
		pager.setPageIndex(NumberUtil.parseInt(params.get("pageIndex"), 1));
		pager.setPageSize(NumberUtil.parseInt(params.get("pageSize"), 10));
		return pager;
	}

	public PageBounds getPageBounds(Map<String, Object> params) {
		Pager pager = getPager(params);
		return new PageBounds(pager.getRowStart(), pager.getPageSize());
	}

}
