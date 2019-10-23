package com.cloud.auto.simple;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class AutoBeanXML {

	private String tablename = "upp_work_id";

    private String logicTableName = "epcc_ctrl_nb";

    private String classNamePrefix = "EpccCtrlNb";// 类名前缀

    private String[] colnames; // 列名数组

    private String[] colTypes; // 列名类型数组

    private int[] colSizes; // 列名大小数组

    private String[] colCommets;// 列注释

    private int[] colScales;// 列小数点右边的个数

    private String pkgPath = "test";

    private String path = "";

     //oracle
     private String driver = "oracle.jdbc.driver.OracleDriver";
     private String url = "jdbc:oracle:thin:@//172.168.65.190:1521/orcl";
     private String user = "upp2";
     private String pwd = "upp2";
    // db2
    // private String driver = "com.ibm.db2.jcc.DB2Driver";
    // private String url = "jdbc:db2://172.168.65.17:50000/ccs";
    // private String user = "ccs";
    // private String pwd = "ccs";
    // mysql
    /*private String driver = "com.mysql.jdbc.Driver";

    private String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF8";

    private String user = "root";

    private String pwd = "root";*/

    /**
     * 
     * 方法描述 : 获取连接
     * 
     * @return
     * @return Connection
     */
    public Connection getConnection() {

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void createBeanMethod() {

        Connection conn = getConnection(); // 得到数据库连接
        // myDB为数据库名
        String strsql = "select * from " + tablename;
        PreparedStatement pstmt = null;
        ResultSetMetaData rsmd = null;
        try {
            pstmt = conn.prepareStatement(strsql);
            rsmd = pstmt.executeQuery().getMetaData();

            int size = rsmd.getColumnCount(); // 共有多少列
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            colCommets = new String[size];
            colScales = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                //colSizes[i] = rsmd.getColumnDisplaySize(i+1);
                colSizes[i] = rsmd.getPrecision(i+1);//获取指定列的小数位数。
                colScales[i] = rsmd.getScale(i + 1);// 获取指定列的小数点右边的位数。
                // 查询列注释
                String strsql2 = null;
                if (driver.contains("mysql")) {
                    strsql2 = "select COLUMN_COMMENT from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME= ? and  column_name= ? ";
                } else if (driver.contains("db2")) {
                    strsql2 = "select t.Remarks from syscat.COLUMNS t where tabname=upper(?) and COLNAME = upper(?)";
                } else {
                    strsql2 = "select COMMENTS from user_col_comments where TABLE_NAME= UPPER(?) and  column_name= UPPER(?) ";
                }
                PreparedStatement pstmt2 = conn.prepareStatement(strsql2);
                pstmt2.setString(1, tablename);
                pstmt2.setString(2, colnames[i]);
                ResultSet resultSet = pstmt2.executeQuery();
                if (resultSet.next() && resultSet.getString(1) != null)
                    colCommets[i] = resultSet.getString(1).replaceAll("[\\t\\n\\r]", "    ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析处理(生成实体类主体代码)
     */
    private void parse() {

        // 生成DO

        // 生成DO类
        createFile(builderDOString(), classNamePrefix + "DO.java");

        // 生成mapper
        createFile(builderMaperStr(), classNamePrefix + "Mapper.xml");

        // 生成repository
        createFile(builderRepositoryString(), classNamePrefix + "Repository.java");
    }

    public String createSQLStr() {

        StringBuilder sb = new StringBuilder();
        // 拼接所有字段
        sb.append("<sql id=\"baseColumnList\"> \r\n");
        sb.append("\t");
        for (String colname : colnames) {
            sb.append(colname + ", ");
        }
        sb.append(" ");
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("\r\n</sql>\r\n");

        // 拼接插入语句
        sb.append("<insert id=\"insert\" parameterType=\"");
        sb.append(getEntityOrRepositoryPkgPath(true));
        sb.append("\">\r\n");
        sb.append("\t");
        sb.append("insert into \r\n");
        sb.append("\t");
        sb.append(logicTableName);
        sb.append("\t");
        sb.append("(\r\n<include refid=\"baseColumnList\" />)\r\n");
        sb.append("\t");
        sb.append("values (");
        for (String colname : colnames) {
            sb.append("#{" + colName2FieldName(colname) + "}, ");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append(")\r\n");
        sb.append("</insert>\r\n");

        // 拼接update语句
        sb.append("<update id=\"update\" parameterType=\"");
        sb.append(getEntityOrRepositoryPkgPath(true));
        sb.append("\">\r\n");
        sb.append("\t");
        sb.append("update ");
        sb.append(logicTableName);
        sb.append("\t");
        sb.append(" set \r\n");
        sb.append("\t");
        sb.append("where \r\n");
        sb.append("</update>\r\n");

        // 删除语句
        sb.append("<delete id=\"delete\" parameterType=\"");
        sb.append(getEntityOrRepositoryPkgPath(true));
        sb.append("\">\r\n");
        sb.append("\t");
        sb.append("delete from ");
        sb.append(logicTableName);
        sb.append("\r\n");
        sb.append("</delete>\r\n");

        // 单条查询语句
        sb.append("<select id=\"select\" parameterType=\"Map\" resultMap=\"result\">\r\n");
        sb.append("\t");
        sb.append("select \r\n");
        sb.append("\t");
        sb.append("<include refid=\"baseColumnList\" />\r\n");
        sb.append("\t");
        sb.append("from ").append(logicTableName).append("\r\n");
        sb.append("</select>");

        // 查询所有
        sb.append("<select id=\"selectAll\" parameterType=\"Map\" resultMap=\"result\">\r\n");
        sb.append("\t");
        sb.append("select \r\n");
        sb.append("\t");
        sb.append("<include refid=\"baseColumnList\" />\r\n");
        sb.append("\t");
        sb.append("from ").append(logicTableName).append("\r\n");
        sb.append("</select>\r\n");

        sb.append("</mapper>");

        return sb.toString();

    }

    public String builderMaperStr() {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
        sb.append("<mapper namespace=\"").append(getEntityOrRepositoryPkgPath(false)).append("\">\r\n");
        sb.append("<resultMap id=\"");
        sb.append("result\"");
        sb.append(" type=\"");
        sb.append(getEntityOrRepositoryPkgPath(true));
        sb.append("\">\r\n");
        for (String colname : colnames) {
            sb.append("\t<result property=\"");
            sb.append(colName2FieldName(colname));
            sb.append("\" column=\"");
            sb.append(colname);
            sb.append("\" />\r\n");
        }
        sb.append("</resultMap>\r\n");
        sb.append(createSQLStr());
        return sb.toString();
    }

    /**
     * 解析输出属性
     * 
     * @return
     */
    private String builderDOString() {

        StringBuffer sb = new StringBuffer();
        
        sb.append("package " + this.getEntityPkgPath()+ ";\r\n\r\n\r\n");
        
        sb.append("@Setter\r\n@Getter\r\npublic class " + classNamePrefix + "DO" + " {\r\n");
        for (int i = 0; i < colnames.length; i++) {
            if (null != colCommets[i]) {
                sb.append("\t/**\r\n");
                sb.append("\t * " + colCommets[i] + "\r\n");
                sb.append("\t */\r\n");
            }
            // else{
            // sb.append("\t//\r");
            // }
            // sb.append("\t@Column(name=\""+colnames[i]+"\",length="+colSizes[i]+")\r");
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i], colSizes[i], colScales[i]) + " "
                    + colName2FieldName(colnames[i]) + ";\r\n\r\n");

        }
        sb.append("}\r\n");
        return sb.toString();
    }

    private String builderRepositoryString() {

        StringBuffer sb = new StringBuffer();
        
        sb.append("package " + this.getRepositoryPkgPath()+ ";\r\n\r\n\r\n");
        
        sb.append("public interface " + classNamePrefix + "Repository" + " {\r\n\r\n");
        sb.append("\tvoid insert(").append(classNamePrefix).append("DO model);\r\n\r\n");
        sb.append("\tint update(").append(classNamePrefix).append("DO model);\r\n\r\n");
        sb.append("\tint delete(").append(classNamePrefix).append("DO model);\r\n\r\n");
        sb.append("\t").append(classNamePrefix).append("DO select(Map<String, Object> map);\r\n\r\n");
        sb.append("\t").append("List<").append(classNamePrefix).append("DO> selectAll(Map<String, Object> map);\r\n\r\n");
        sb.append("}\r\n");
        return sb.toString();
    }

    private String colName2FieldName(String colName) {

        String[] strs = colName.split("_");
        String fieldName = "";
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            if (i == 0) {
                fieldName += s.toLowerCase();
            } else {
                fieldName += (s.toLowerCase().charAt(0) + "").toUpperCase() + s.toLowerCase().substring(1);
            }
        }
        return fieldName;
    }

    private String sqlType2JavaType(String sqlType , int colSize , int colScale) {

        if (sqlType.equalsIgnoreCase("bit") || sqlType.equalsIgnoreCase("boolean")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")
                || (sqlType.equalsIgnoreCase("decimal") && colSize < 11 && 0 >= colScale)
                || (sqlType.equalsIgnoreCase("number") && colSize < 11 && 0 >= colScale)) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint") || (sqlType.equalsIgnoreCase("number") && 0 >= colScale)
                || (sqlType.equalsIgnoreCase("decimal") && 0 >= colScale)) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("number")) {
            return "BigDecimal";
        } else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("varchar2")
                || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")
                || sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("time")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        }
        return null;
    }

    private void createDir() {

        String[] paths = pkgPath.split("\\.");
        StringBuilder sb = new StringBuilder("./");
        if (paths.length != 0) {
            for (int i = 0; i < paths.length; i++) {
                sb.append(paths[i]).append("/");
            }
        }
        sb.append(classNamePrefix.toLowerCase());
        sb.append("/");
        path = sb.toString();
        File file = new File(path + "repository");
        boolean isDirExists = file.exists() && file.isDirectory();
        if (!isDirExists) {
            file.mkdirs();
        }
        file = new File(path + "entity");
        isDirExists = file.exists() && file.isDirectory();
        if (!isDirExists) {
            file.mkdirs();
        }
        System.out.println("生成文件目录" + path);
    }

    /**
     * 
     * 功能:
     * @param content
     * @param fileName 
     * void
     * 2017年4月1日 下午2:42:37
     */
    public void createFile(String content , String fileName) {

        FileWriter fw = null;
        try {
            fw = new FileWriter(path + (fileName.endsWith("Repository.java") ? "/repository/" : "/entity/") + fileName);
            fw.write(content);
        } catch (Exception e) {
            System.err.println("生成" + fileName + "出错");
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 
     * 功能:
     * @param flag
     * @return String
     * 2017年4月1日 下午2:56:37
     */
    public String getEntityOrRepositoryPkgPath(boolean flag) {

        if (flag) {
            return new StringBuilder().append(getEntityPkgPath()).append(".").append(classNamePrefix).append("DO").toString();
        }
        return new StringBuilder().append(getRepositoryPkgPath()).append(".").append(classNamePrefix).append("Repository").toString();
    }
    
    /**
     * 
     * 功能:
     * @param flag
     * @return String
     * 2017年4月1日 下午2:56:37
     */
    public String getEntityPkgPath() {

        return new StringBuilder().append(pkgPath).append(".").append(this.classNamePrefix.toLowerCase())
                    .append(".entity").toString();
    }
    
    /**
     * 
     * 功能:
     * @param flag
     * @return String
     * 2017年4月1日 下午2:56:37
     */
    public String getRepositoryPkgPath() {

    	return new StringBuilder().append(pkgPath).append(".").append(this.classNamePrefix.toLowerCase())
                .append(".repository").toString();
    }

    public static void main(String[] args) {

        AutoBeanXML createBean = new AutoBeanXML();
        createBean.createDir();
        System.out.println("创建目录完成");
        createBean.createBeanMethod();
        createBean.parse();
        System.out.println("创建文件完成");
    }
}
