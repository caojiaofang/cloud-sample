package com.cloud.mybatis.interceptor;

import org.apache.ibatis.session.RowBounds;
/**
 * @Description: 
 * @author  lizhi 
 * @date  2019年4月6日 下午2:10:32
 */
public class PageBounds extends RowBounds {

	/**
	 * 总记录数
	 */
	private Integer countRows;

	public PageBounds() {
		super();
	}

	public PageBounds(int offset, int limit) {
		super(offset, limit);
	}

	public Integer getCountRows() {
		return countRows;
	}

	public void setCountRows(Integer countRows) {
		this.countRows = countRows;
	}

}
