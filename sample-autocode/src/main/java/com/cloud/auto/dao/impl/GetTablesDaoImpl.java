package com.cloud.auto.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cloud.auto.bean.ColumnStruct;
import com.cloud.auto.bean.TableStruct;
import com.cloud.auto.dao.GetTablesDao;
import com.cloud.auto.util.ConfigUtil;
import com.cloud.auto.util.DataSourceUtil;


/**
 * 项目名称：autoGenerate
 * 类描述：获取数据表及其结构的dao层实现类
 * 创建者：longhuaiyu
 * 创建时间：2018年6月12日下午2:33:21
 * 版本号：V1.0
 */
@SuppressWarnings("all")
public class GetTablesDaoImpl extends DataSourceUtil implements GetTablesDao {

	// 获得数据库的所有表名
	@Override
	public String[] getTablesName() {
		String tablesStr = ConfigUtil.tables;
		String[] tables = null;
		
		if(null != tablesStr && !"".equals(tablesStr)) {
			tables = tablesStr.split(",");
		}else {
			if(ConfigUtil.driver.contains("mysql")) {
				String sql = "show tables";
				ResultSet rs = this.query(sql);
				try {
					int i=0;
					while (rs.next()) {
						// 将获得的所有表名装进List
						i++;
						tables[i] = rs.getString(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return tables;
	}

	// 获得数据表中的字段名称、字段类型
	@Override
	public List getTablesStruct() {
		
		Connection conn = getConnection(); // 得到数据库连接
        
        PreparedStatement pstmt = null;
        ResultSetMetaData rsmd = null;
        List tablesStruct = new ArrayList();
		
        try {
			// 获得装有所有表名的List
			String tables[] = this.getTablesName();
			String sqls = null;
			
			// 装所有的表结构（表名+字段名称+字段类型）
			for (int i = 0; i < tables.length; i++) {
				// myDB为数据库名
				sqls = "select * from " + tables[i];
				pstmt = conn.prepareStatement(sqls);
		        rsmd = pstmt.executeQuery().getMetaData();
				// 装所有的列结构(字段名称+字段类型)
				List columnList = new ArrayList();
				
				for (int j = 0; j < rsmd.getColumnCount(); j++) {
					//columnList.add(rsmd.getColumnName(i + 1));//将列名保存到list
	                // 查询列注释
	                String strsql2 = null;
	                if (ConfigUtil.driver.contains("mysql")) {
	                    strsql2 = "select COLUMN_COMMENT from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME= ? and  column_name= ? ";
	                } else if (ConfigUtil.driver.contains("db2")) {
	                    strsql2 = "select t.Remarks from syscat.COLUMNS t where tabname=upper(?) and COLNAME = upper(?)";
	                } else {
	                    strsql2 = "select COMMENTS from user_col_comments where TABLE_NAME= UPPER(?) and  column_name= UPPER(?) ";
	                }
	                PreparedStatement pstmt2 = conn.prepareStatement(strsql2);
	                pstmt2.setString(1, tables[i]);
	                pstmt2.setString(2, rsmd.getColumnName(j + 1));
	                ResultSet resultSet = pstmt2.executeQuery();
	                ColumnStruct cs = null;
	                if (resultSet.next() && resultSet.getString(1) != null) {
	                	cs = new ColumnStruct(rsmd.getColumnName(j + 1).toLowerCase(), rsmd.getColumnTypeName(j + 1),resultSet.getString(1).replaceAll("[\\t\\n\\r]", "    "),rsmd.getScale(j + 1),rsmd.getPrecision(j + 1));
	                	//cs = new ColumnStruct(rsmd.getColumnName(j + 1).toLowerCase(), rsmd.getColumnTypeName(j + 1),resultSet.getString(1).replaceAll("[\\t\\n\\r]", "    "),rsmd.getScale(i + 1),rsmd.getColumnDisplaySize(i + 1));
	                	columnList.add(cs);
	                }else {
	                	cs = new ColumnStruct(rsmd.getColumnName(j + 1).toLowerCase(), rsmd.getColumnTypeName(j + 1),"",rsmd.getScale(j + 1),rsmd.getPrecision(j + 1));
	                	//cs = new ColumnStruct(rsmd.getColumnName(j + 1).toLowerCase(), rsmd.getColumnTypeName(j + 1),"",rsmd.getScale(i + 1),rsmd.getColumnDisplaySize(i + 1));
	                	columnList.add(cs);
	                }
	                
	            }
				
				//查询主键
                String sql3 = "";
                if (ConfigUtil.driver.contains("mysql")) {
                    sql3 = "SELECT TABLE_NAME,COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME=?";
                } else if (ConfigUtil.driver.contains("db2")) {
                	//
                } else {
                	sql3 = "select   column_name  from   user_cons_columns   where   constraint_name   =   (select   constraint_name   from   user_constraints where   table_name   =  UPPER(?)   and   constraint_type   ='P')"  ; 
 	               
                }
               // 装所有主键列结构(字段名称+字段类型)
				List pKeyColumnList = new ArrayList();
				PreparedStatement pstmt3 = conn.prepareStatement(sql3);
                pstmt3.setString(1, tables[i]);
                ResultSet resultSet1 = pstmt3.executeQuery();
                while (resultSet1.next()){
                	for(Object o:columnList){
                		ColumnStruct theColumnStruct = (ColumnStruct)o;
                		String keyColumnName = resultSet1.getString(1).toLowerCase();
                		if(theColumnStruct.getColumnName().equals(keyColumnName)){
                			pKeyColumnList.add(theColumnStruct);
                			break;
                		}
                	}
                }
				// 遍历完一张表，封装成对象
				TableStruct ts = new TableStruct(tables[i], columnList);
				ts.setpKeyColums(pKeyColumnList);
				// 将对象（一张表）装进集合
				tablesStruct.add(ts);
			}
        } catch (Exception e) {
        	e.printStackTrace();
		}
		return tablesStruct;
	}
}