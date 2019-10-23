package com.cloud.auto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 项目名称：autoGenerate
 * 类描述：获得数据源工具类
 * 创建者：longhuaiyu
 * 创建时间：2018年6月12日下午2:30:53
 * 版本号：V1.0
 */
public class DataSourceUtil {

	// 操作数据库的对象
	private Connection con;
	private Statement sta;
	private ResultSet rs;


	/**
	 * 获得链接
	 */
	public Connection getConnection() {
		try {
			// 加载驱动
			Class.forName(ConfigUtil.driver);
			con = DriverManager.getConnection(ConfigUtil.url, ConfigUtil.userName, ConfigUtil.userPs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 创建一个状态通道
	 */
	private void createStatement() {
		// 获得链接的方法
		this.getConnection();
		try {
			sta = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 基于状态通道的 查询方法
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet query(String sql) {
		this.createStatement();
		try {
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 关闭资源方法
	 */
	public void closeRes() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (sta != null) {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}