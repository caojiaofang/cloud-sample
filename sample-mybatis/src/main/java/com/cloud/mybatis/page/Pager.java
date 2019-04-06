package com.cloud.mybatis.page;

import java.util.List;

/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public class Pager {

	private int pageSize;  // 每页行数
	private int pageIndex; // 当前页数
	private int pageTotal; // 总页数
	private int rowStart;  // 起始行
	private int rowTotal;  // 总行数
	private List<?> result; // 查询结果

	/**
	 * 初始化
	 */
	public Pager() {
		pageSize = 10; // 每页行数
		pageIndex = 1; // 当前页数
		rowStart = 0;  // 起始行
	}

	/**
	 * 计算总页数、记录数
	 * 
	 * @param countRows-总记录数
	 */
	public void calRowTotal(int countRows) {
		rowTotal = countRows;
		pageTotal = countRows / pageSize;
		int mod = countRows % pageSize;
		if (mod > 0) {
			pageTotal++;
		}
	}

	/**
	 * 首页
	 */
	public void first() {
		pageIndex = 1;
		rowStart = 0;
	}

	/**
	 * 前一页
	 */
	public void previous() {
		if (1 == pageIndex) {
			return;
		}
		pageIndex--;
	}

	/**
	 * 后一页
	 */
	public void next() {
		if (pageIndex < pageTotal) {
			pageIndex++;
		}
	}

	/**
	 * 最后一页
	 */
	public void last() {
		pageIndex = pageTotal;
	}

	/**
	 * 计算开始记录数
	 */
	private void calRowStart() {
		this.rowStart = (pageIndex - 1) * pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getRowStart() {
		calRowStart();
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getResult() {
		return (List<T>) result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

}
